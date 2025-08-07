package com.mt.pharmacy_be.service;

import com.mt.pharmacy_be.dto.exportDTO.ExportProgressDTO;
import com.mt.pharmacy_be.dto.medicineDTO.MedicineSearchRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.FileNotFoundException;

@Service
public interface MedicineExportService {

    void exportMedicineToCsv();

    void exportMedicineToCsvPaginated(int page, int pageSize);

    void exportMedicineWithFilters(MedicineSearchRequestDTO searchRequest);

    Flux<ExportProgressDTO> streamProgressAsFlux();

    ResponseEntity<?> downloadExportFile(String fileName) throws FileNotFoundException;
}
