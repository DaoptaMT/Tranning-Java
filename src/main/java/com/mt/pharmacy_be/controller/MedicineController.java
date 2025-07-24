package com.mt.pharmacy_be.controller;

import com.mt.pharmacy_be.dto.medicineDTO.MedicineRequestDTO;
import com.mt.pharmacy_be.dto.medicineDTO.MedicineSearchRequestDTO;
import com.mt.pharmacy_be.service.MedicineService;
import com.mt.pharmacy_be.util.JsonResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("permitAll()")
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
    @PreAuthorize("hasAuthority('EMPLOYEE')")
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
    @PreAuthorize("hasAuthority('EMPLOYEE')")
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
    @PreAuthorize("hasAuthority('EMPLOYEE')")
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
    @PreAuthorize("permitAll()")
    @PostMapping("/search")
    public ResponseEntity<?> searchMedicines(@RequestBody MedicineSearchRequestDTO search,
                                             @RequestParam(required = false, defaultValue = "1") int page,
                                             @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return JsonResponse.ok(medicineService.searchMedicines(search, page, pageSize));
    }
}
