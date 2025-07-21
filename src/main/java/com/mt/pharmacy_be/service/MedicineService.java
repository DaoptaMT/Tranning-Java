package com.mt.pharmacy_be.service;

import com.mt.pharmacy_be.dto.PageResponse;
import com.mt.pharmacy_be.dto.medicineDTO.MedicineResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface MedicineService {

    /**
     * Get all medicines with pagination.
     * Author: Thanh Truc
     * Date: 21/07/2025
     * Description: This method retrieves a paginated list of all medicines.
     */
    PageResponse<?> getAll(int page, int pageSize);

    /**
     * Get medicine by ID.
     * Author: Thanh Truc
     * Date: 21/07/2025
     * Description: This method retrieves a specific medicine by its ID.
     */
    MedicineResponseDTO getById(Long id);
}
