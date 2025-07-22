package com.mt.pharmacy_be.controller;

import com.mt.pharmacy_be.dto.medicineDTO.MedicineRequestDTO;
import com.mt.pharmacy_be.service.MedicineService;
import com.mt.pharmacy_be.util.JsonResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/medicines")
public class MedicineController {

    MedicineService medicineService;

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
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(@RequestPart("data") @Valid MedicineRequestDTO request,
                                    @RequestPart("files") List<MultipartFile> files) {
        return JsonResponse.ok(medicineService.create(request, files));
    }

    /**
     * Handles requests to update an existing medicine.
     * Author: Thanh Truc
     * Date: 21/07/2025
     * Description: This endpoint updates an existing medicine in the system.
     */
    @PutMapping(value = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestPart("data") @Valid MedicineRequestDTO request,
                                    @RequestPart("files") List<MultipartFile> files) {
        return JsonResponse.ok(medicineService.update(id, request, files));
    }
}
