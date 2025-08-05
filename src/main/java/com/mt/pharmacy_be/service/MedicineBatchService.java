package com.mt.pharmacy_be.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service interface for handling batch operations related to medicines.
 * This service provides methods to import medicine data from CSV files.
 * Author: Thanh Truc
 * Date: 05/08/2025
 * Description: This interface defines the contract for batch operations on medicines, including importing data from CSV files.
 */
@Service
public interface MedicineBatchService {
    void importMedicineFromCsv(MultipartFile file);
}
