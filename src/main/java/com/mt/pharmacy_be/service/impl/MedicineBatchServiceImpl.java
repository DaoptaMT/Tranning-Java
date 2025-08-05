package com.mt.pharmacy_be.service.impl;

import com.mt.pharmacy_be.enums.ErrorCode;
import com.mt.pharmacy_be.exception.ApiException;
import com.mt.pharmacy_be.service.MedicineBatchService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MedicineBatchServiceImpl implements MedicineBatchService {

    JobLauncher jobLauncher;
    ApplicationContext applicationContext;

    /**
     * Imports medicine data from a CSV file.
     * Author: Thanh Truc
     * Date: 28/07/2024
     * Description: This method handles the import of medicine data from a CSV file.
     */
    @Override
    public void importMedicineFromCsv(MultipartFile file) {
        try {
            if (file.isEmpty() || !Objects.requireNonNull(file.getOriginalFilename()).endsWith(".csv")) {
                throw new ApiException(ErrorCode.CSV_INVALID);
            }

            // Save the uploaded file to a temporary location
            String timestamp = String.valueOf(new Date().getTime());
            Path tempDir = Files.createTempDirectory("medicine_import_");
            Path tempFile = Paths.get(tempDir.toString(), "medicine_" + timestamp + ".csv");
            log.info("Saving uploaded file to: {}", tempFile);
            Files.copy(file.getInputStream(), tempFile);

            Job job = applicationContext.getBean("importMedicineJob", Job.class);

            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .addString("filePath", tempFile.toAbsolutePath().toString())
                    .toJobParameters();

            // Run the job
            JobExecution jobExecution = jobLauncher.run(job, jobParameters);

            log.info("Job execution status: {}", jobExecution.getStatus());

            Files.deleteIfExists(tempFile);
            Files.deleteIfExists(tempDir);

        } catch (JobInstanceAlreadyCompleteException e) {
            log.error("This job has already been completed", e);
            throw new ApiException(ErrorCode.CSV_ALREADY_PROCESSED);
        } catch (IOException e) {
            log.error("Error reading file", e);
            throw new RuntimeException("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error during import", e);
            throw new RuntimeException("Error during import: " + e.getMessage());
        }
    }
}
