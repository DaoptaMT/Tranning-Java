package com.mt.pharmacy_be.controller;

import com.mt.pharmacy_be.service.MedicineService;
import com.mt.pharmacy_be.service.UserService;
import com.mt.pharmacy_be.util.JsonResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
