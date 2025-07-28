package com.mt.pharmacy_be.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface MedicineBatchService {
    void importMedicineFromCsv(MultipartFile file);
}
