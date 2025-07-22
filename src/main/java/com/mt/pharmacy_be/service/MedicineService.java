package com.mt.pharmacy_be.service;

import com.mt.pharmacy_be.dto.PageResponse;
import com.mt.pharmacy_be.dto.medicineDTO.MedicineRequestDTO;
import com.mt.pharmacy_be.dto.medicineDTO.MedicineResponseDTO;
import com.mt.pharmacy_be.dto.medicineDTO.MedicineSearchRequestDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    /**
     * Create a new medicine.
     * Author: Thanh Truc
     * Date: 21/07/2025
     * Description: This method creates a new medicine in the system.
     */
    MedicineResponseDTO create(MedicineRequestDTO request, List<MultipartFile> files);

    /**
     * Update an existing medicine.
     * Author: Thanh Truc
     * Date: 22/07/2025
     * Description: This method updates an existing medicine's details.
     */
    MedicineResponseDTO update(Long id, @Valid MedicineRequestDTO request, List<MultipartFile> files);

    /**
     * Delete a medicine by ID.
     * Author: Thanh Truc
     * Date: 22/07/2025
     * Description: This method deletes a medicine from the system by its ID.
     */
    void delete(Long id);

    /**
     * Search for medicines based on various criteria.
     * Author: Thanh Truc
     * Date: 22/07/2025
     * Description: This method searches for medicines using the provided search criteria.
     */
    PageResponse<?> searchMedicines(MedicineSearchRequestDTO search, int page, int pageSize);
}
