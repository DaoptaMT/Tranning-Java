package com.mt.pharmacy_be.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mt.pharmacy_be.dto.export.ExportProgressDTO;
import com.mt.pharmacy_be.dto.medicineDTO.MedicineSearchRequestDTO;
import com.mt.pharmacy_be.entity.MedicineEntity;
import com.mt.pharmacy_be.enums.ErrorCode;
import com.mt.pharmacy_be.enums.ExportStatus;
import com.mt.pharmacy_be.exception.ApiException;
import com.mt.pharmacy_be.repository.MedicineRepository;
import com.mt.pharmacy_be.service.ExportEventService;
import com.mt.pharmacy_be.service.MedicineExportService;
import com.mt.pharmacy_be.util.MedicineSpecificationFactory;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class MedicineExportServiceImpl implements MedicineExportService {

    static final String EXPORT_DIRECTORY = "exports";
    static final int MAX_WAIT_TIME_SECONDS = 300;
    static final long CLEANUP_DELAY_MS = 5000;

    JobLauncher jobLauncher;
    Job medicineExportJob;
    ExportEventService exportEventService;
    MedicineRepository medicineRepository;
    ObjectMapper objectMapper;
    MedicineSpecificationFactory medicineSpecificationFactory;

    /**
     * Exports all medicine data to a CSV file asynchronously.
     * Author: Thanh Truc
     * Date: 05/08/2025
     * Description: This method exports all medicine records to a CSV file.
     */
    @Async("exportExecutor")
    @Override
    public void exportMedicineToCsv() {
        String fileName = generateFileName();
        String filePath = buildFilePath(fileName);

        if (prepareExportDirectory(fileName, filePath)) {
            return;
        }

        notifyExportStarted(fileName, filePath, "Export started");

        try {
            long recordCount = medicineRepository.count();
            log.info("Found {} medicine records to export", recordCount);

            if (recordCount == 0) {
                notifyNoDataAvailable(fileName, filePath, "No data available to export");
                return;
            }

            JobParameters jobParameters = buildJobParameters(fileName, filePath, null, null, "all");
            executeExportJob(fileName, filePath, jobParameters);
        } catch (Exception e) {
            handleExportException(fileName, filePath, e);
        }
    }

    /**
     * Exports medicine data to a CSV file asynchronously with pagination support.
     * Author: Thanh Truc
     * Date: 05/08/2025
     * Description: This method exports a paginated set of medicine records to a CSV file.
     */
    @Async("exportExecutor")
    @Override
    public void exportMedicineToCsvPaginated(int page, int pageSize) {
        String fileName = generateFileName();
        String filePath = buildFilePath(fileName);

        if (prepareExportDirectory(fileName, filePath)) {
            return;
        }

        notifyExportStarted(fileName, filePath,
                "Export started for page " + page + " with pageSize " + pageSize);

        try {
            long totalRecords = medicineRepository.count();
            int adjustedPage = Math.max(1, page) - 1;
            long possibleRecordsForPage = calculatePossibleRecordsForPage(totalRecords, adjustedPage, pageSize);

            log.info("Exporting {} medicine records for page {} with pageSize {}",
                    possibleRecordsForPage, page, pageSize);

            if (possibleRecordsForPage == 0) {
                notifyNoDataAvailable(fileName, filePath,
                        "No data available to export for the specified page");
                return;
            }

            JobParameters jobParameters = buildJobParameters(fileName, filePath, adjustedPage, pageSize, "paginated");
            executeExportJob(fileName, filePath, jobParameters);
        } catch (Exception e) {
            handleExportException(fileName, filePath, e);
        }
    }

    /**
     * Streams export progress updates as a Flux.
     * Author: Thanh Truc
     * Date: 05/08/2025
     * Description: This method provides a reactive stream of export progress updates,
     */
    @Override
    public Flux<ExportProgressDTO> streamProgressAsFlux() {
        return exportEventService.createProgressStream();
    }

    /**
     * Downloads the exported CSV file.
     * Author: Thanh Truc
     * Date: 05/08/2025
     * Description: This method allows downloading the exported CSV file by its filename.
     */
    @Override
    public ResponseEntity<?> downloadExportFile(String fileName) throws FileNotFoundException {
        File file = new File(EXPORT_DIRECTORY, fileName);

        if (!file.exists()) {
            throw new ApiException(ErrorCode.CSV_DOWNLOAD_INVALID);
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        ResponseEntity<Resource> response = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=medicines_export.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .contentLength(file.length())
                .body(resource);

        scheduleFileCleanup(file);

        return response;
    }

    /**
     * Exports medicine data to CSV with applied filters.
     * Author: Thanh Truc
     * Date: 05/08/2025
     * Description: This method exports medicine data filtered by search criteria.
     */
    @Async("exportExecutor")
    @Override
    public void exportMedicineWithFilters(MedicineSearchRequestDTO request) {
        String fileName = generateFileName();
        String filePath = buildFilePath(fileName);

        if (prepareExportDirectory(fileName, filePath)) {
            return;
        }

        String startMessage = "Export started with filters";
        notifyExportStarted(fileName, filePath, startMessage);

        try {
            Specification<MedicineEntity> specification = medicineSpecificationFactory.buildMedicineSpecification(request);
            List<MedicineEntity> medicineEntityPage = medicineRepository.findAll(specification);

            if (medicineEntityPage.isEmpty()) {
                notifyNoDataAvailable(fileName, filePath, "No data matches the specified filters");
                return;
            }

            JobParametersBuilder builder = new JobParametersBuilder()
                    .addString("fileName", fileName)
                    .addString("filePath", filePath)
                    .addString("exportType", "filtered")
                    .addString("searchCriteria", objectMapper.writeValueAsString(request))
                    .addLong("time", System.currentTimeMillis());

            JobParameters jobParameters = builder.toJobParameters();
            executeExportJob(fileName, filePath, jobParameters);
        } catch (Exception e) {
            handleExportException(fileName, filePath, e);
        }
    }

    /**
     * Generates a unique file name for export
     */
    private String generateFileName() {
        return "medicines_export_" + UUID.randomUUID() + ".csv";
    }

    /**
     * Builds a file path for the export file
     */
    private String buildFilePath(String fileName) {
        return EXPORT_DIRECTORY + "/" + fileName;
    }

    /**
     * Prepares the export directory, creating it if it doesn't exist
     */
    private boolean prepareExportDirectory(String fileName, String filePath) {
        try {
            Path exportDir = Paths.get(EXPORT_DIRECTORY);
            if (!Files.exists(exportDir)) {
                Files.createDirectories(exportDir);
            }
            return false;
        } catch (IOException e) {
            log.error("Failed to create export directory: {}", e.getMessage());
            sendExportProgress(fileName, filePath, ExportStatus.FAILED,
                    "Error creating export directory: " + e.getMessage());
            return true;
        }
    }

    /**
     * Notifies that the export has started
     */
    private void notifyExportStarted(String fileName, String filePath, String message) {
        sendExportProgress(fileName, filePath, ExportStatus.PROCESSING, message);
    }

    /**
     * Notifies that no data is available for export
     */
    private void notifyNoDataAvailable(String fileName, String filePath, String message) {
        sendExportProgress(fileName, filePath, ExportStatus.COMPLETED, message);
    }

    /**
     * Builds job parameters for the export job
     */
    private JobParameters buildJobParameters(String fileName, String filePath,
                                             Integer page, Integer pageSize, String exportType) {
        JobParametersBuilder builder = new JobParametersBuilder()
                .addString("fileName", fileName)
                .addString("filePath", filePath)
                .addLong("time", System.currentTimeMillis());

        if (page != null && pageSize != null) {
            builder.addLong("page", page.longValue())
                    .addLong("pageSize", pageSize.longValue())
                    .addString("exportType", exportType);
        }

        return builder.toJobParameters();
    }

    /**
     * Executes the export job with the given parameters
     */
    private void executeExportJob(String fileName, String filePath, JobParameters jobParameters)
            throws Exception {
        JobExecution jobExecution = jobLauncher.run(medicineExportJob, jobParameters);

        // Add timeout to prevent indefinite waiting
        int waitedSeconds = 0;

        while (jobExecution.isRunning() && waitedSeconds < MAX_WAIT_TIME_SECONDS) {
            Thread.sleep(500);
            waitedSeconds++;
        }

        if (waitedSeconds >= MAX_WAIT_TIME_SECONDS) {
            sendExportProgress(fileName, filePath, ExportStatus.FAILED, "Export timed out after 5 minutes");
            return;
        }

        boolean success = !jobExecution.getStatus().isUnsuccessful();
        ExportStatus status = success ? ExportStatus.COMPLETED : ExportStatus.FAILED;
        String message = success ? "Export completed successfully" :
                "Export failed: " + jobExecution.getExitStatus().getExitDescription();

        sendExportProgress(fileName, filePath, status, message);
    }

    private void handleExportException(String fileName, String filePath, Exception e) {
        log.error("Error during export: {}", e.getMessage(), e);
        sendExportProgress(fileName, filePath, ExportStatus.FAILED, "Error during export: " + e.getMessage());
    }

    /**
     * Helper method to calculate possible records for a page
     */
    private long calculatePossibleRecordsForPage(long totalRecords, int page, int pageSize) {
        return Math.min(pageSize, Math.max(0, totalRecords - (long) page * pageSize));
    }

    /**
     * Schedules the cleanup of the export file after download
     */
    private void scheduleFileCleanup(File file) {
        CompletableFuture.delayedExecutor(CLEANUP_DELAY_MS, TimeUnit.MILLISECONDS)
                .execute(() -> {
                    try {
                        Files.deleteIfExists(file.toPath());
                        Path parentDir = file.getParentFile().toPath();
                        if (Files.isDirectory(parentDir) && Files.list(parentDir).findAny().isEmpty()) {
                            Files.deleteIfExists(parentDir);
                        }
                    } catch (Exception e) {
                        log.error("Error during download cleanup", e);
                    }
                });
    }

    /**
     * Sends export progress update
     */
    private void sendExportProgress(String fileName, String filePath, ExportStatus status, String message) {
        ExportProgressDTO progress = ExportProgressDTO.builder()
                .fileName(fileName)
                .filePath(filePath)
                .status(status.name())
                .message(message)
                .when(LocalDateTime.now())
                .build();
        exportEventService.sendExportProgress(progress);
    }
}
