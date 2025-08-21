package com.mt.pharmacy_be.controller;

import com.mt.pharmacy_be.dto.exportDTO.ExportProgressDTO;
import com.mt.pharmacy_be.dto.medicineDTO.MedicineRequestDTO;
import com.mt.pharmacy_be.dto.medicineDTO.MedicineSearchRequestDTO;
import com.mt.pharmacy_be.service.MedicineBatchService;
import com.mt.pharmacy_be.service.MedicineExportService;
import com.mt.pharmacy_be.service.MedicineService;
import com.mt.pharmacy_be.util.JsonResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.FileNotFoundException;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/medicines")
public class MedicineController {

    MedicineService medicineService;
    MedicineBatchService medicineBatchService;
    MedicineExportService medicineExportService;

    /**
     * Handles requests to retrieve all medicines with pagination.
     * Author: Thanh Truc
     * Date: 21/07/2025
     * Description: This endpoint retrieves a paginated list of all medicines.
     */
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = false, defaultValue = "1") int page,
                                    @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return JsonResponse.ok(medicineService.getAll(page, pageSize));
    }

    /**
     * Handles requests to retrieve a medicine by its ID.
     * Author: Thanh Truc
     * Date: 21/07/2025
     * Description: This endpoint retrieves a specific medicine by its ID.
     */
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return JsonResponse.ok(medicineService.getById(id));
    }

    /**
     * Handles requests to create a new medicine.
     * Author: Thanh Truc
     * Date: 21/07/2025
     * Description: This endpoint creates a new medicine in the system.
     */
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid MedicineRequestDTO request) {
        return JsonResponse.ok(medicineService.create(request));
    }

    /**
     * Handles requests to update an existing medicine.
     * Author: Thanh Truc
     * Date: 21/07/2025
     * Description: This endpoint updates an existing medicine in the system.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody @Valid MedicineRequestDTO request) {
        return JsonResponse.ok(medicineService.update(id, request));
    }

    /**
     * Handles requests to delete a medicine by its ID.
     * Author: Thanh Truc
     * Date: 22/07/2025
     * Description: This endpoint deletes a specific medicine by its ID.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        medicineService.delete(id);
        return JsonResponse.deleted();
    }

    /**
     * Handles requests to search for medicines.
     * Author: Thanh Truc
     * Date: 22/07/2025
     * Description: This endpoint allows searching for medicines based on various criteria.
     */
    @PostMapping("/search")
    public ResponseEntity<?> searchMedicines(@RequestBody MedicineSearchRequestDTO search,
                                             @RequestParam(required = false, defaultValue = "1") int page,
                                             @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return JsonResponse.ok(medicineService.searchMedicines(search, page, pageSize));
    }

    /**
     * Handles requests to export medicines to a CSV file.
     * Author: Thanh Truc
     * Date: 28/07/2025
     * Description: This endpoint exports all medicines to a CSV file and returns it as a downloadable resource.
     */
    @PostMapping("/batch/import")
    public ResponseEntity<?> importMedicines(@RequestParam("file") MultipartFile file) {
        medicineBatchService.importMedicineFromCsv(file);
        return JsonResponse.ok("File import process started successfully.");
    }

    /**
     * Handles requests to export medicines to a CSV file asynchronously.
     * Author: Thanh Truc
     * Date: 05/08/2025
     * Description: This endpoint triggers an asynchronous export of all medicines to a CSV file.
     */
    @PostMapping("/export-csv")
    public ResponseEntity<?> exportToCsvAsync() {
        medicineExportService.exportMedicineToCsv();
        return JsonResponse.ok("Exporting...");
    }

    /**
     * Handles requests to export medicines to a CSV file asynchronously with pagination.
     * Author: Thanh Truc
     * Date: 05/08/2025
     * Description: This endpoint triggers an asynchronous export of medicines for a specific page to a CSV file.
     */
    @PostMapping("/export-csv/paginated")
    public ResponseEntity<?> exportToCsvPaginatedAsync(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        medicineExportService.exportMedicineToCsvPaginated(page, pageSize);
        return JsonResponse.ok("Exporting page " + page + " with page size " + pageSize + "...");
    }

    /**
     * Handles requests to download the exported CSV file.
     * Author: Thanh Truc
     * Date: 05/08/2025
     * Description: This endpoint allows downloading the exported CSV file by its filename.
     */
    @GetMapping("/download-csv/{fileName}")
    public ResponseEntity<?> downloadCsv(@PathVariable String fileName) throws FileNotFoundException {
        return medicineExportService.downloadExportFile(fileName);
    }

    /**
     * Streams the progress of the export operation as Server-Sent Events (SSE).
     * Author: Thanh Truc
     * Date: 05/08/2025
     * Description: This endpoint provides a real-time stream of export progress updates.
     */
    @GetMapping(value = "/export/progress", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<ExportProgressDTO>> streamExportProgress() {
        return medicineExportService.streamProgressAsFlux()
                .map(progress -> ServerSentEvent.<ExportProgressDTO>builder()
                        .id(progress.getFileName())
                        .event("EXPORT_PROGRESS")
                        .data(progress)
                        .build());
    }

    /**
     * Handles requests to export filtered medicines to a CSV file asynchronously.
     * Author: Thanh Truc
     * Date: 05/08/2025
     * Description: This endpoint triggers an asynchronous export of medicines based on search filters.
     */
    @PostMapping("/export-csv/filtered")
    public ResponseEntity<?> exportFilteredDataToCsvAsync(@RequestBody MedicineSearchRequestDTO searchRequest) {
        medicineExportService.exportMedicineWithFilters(searchRequest);
        return JsonResponse.ok("Exporting all filtered data...");
    }
}
