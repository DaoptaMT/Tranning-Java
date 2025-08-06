package com.mt.pharmacy_be.service;

import com.mt.pharmacy_be.dto.PageResponse;
import com.mt.pharmacy_be.dto.kindOfMedicineDTO.KindOfMedicineRequestDTO;
import com.mt.pharmacy_be.dto.kindOfMedicineDTO.KindOfMedicineResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface KindOfMedicineService {
    KindOfMedicineResponseDTO createKindOfMedicine(KindOfMedicineRequestDTO kindOfMedicine);

    PageResponse<?> getKindOfMedicineByName(String name, int page, int size);

    KindOfMedicineResponseDTO getKindOfMedicineByCode(String name);

    PageResponse<?> getAllKindOfMedicines(int page, int size);
    
    void deleteKindOfMedicine(Long id);
    
    KindOfMedicineResponseDTO updateKindOfMedicine(Long id,KindOfMedicineRequestDTO kindOfMedicineRequestDTO);

    KindOfMedicineResponseDTO getById(Long id);
}

