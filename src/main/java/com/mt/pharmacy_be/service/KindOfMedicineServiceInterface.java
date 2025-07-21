package com.mt.pharmacy_be.service;

import com.mt.pharmacy_be.dto.kindofmedicineDTO.KindOfMedicineRequestDTO;
import com.mt.pharmacy_be.dto.kindofmedicineDTO.KindOfMedicineResponseDTO;
import com.mt.pharmacy_be.entity.KindOfMedicine;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface KindOfMedicineServiceInterface {
    KindOfMedicineResponseDTO createKindOfMedicine(KindOfMedicineRequestDTO kindOfMedicine);
    
    List<?> getKindOfMedicineByName(String name);

    KindOfMedicineResponseDTO getKindOfMedicineByCode(String name);

    Page<KindOfMedicineResponseDTO> getAllKindOfMedicines(int page, int size);
    
    String deleteKindOfMedicine(String code);
    
    KindOfMedicineResponseDTO updateKindOfMedicine(String code,KindOfMedicineRequestDTO kindOfMedicineRequestDTO);
}

