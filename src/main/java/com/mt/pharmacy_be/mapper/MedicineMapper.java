package com.mt.pharmacy_be.mapper;

import com.mt.pharmacy_be.dto.medicineDTO.MedicineCsvDTO;
import com.mt.pharmacy_be.dto.medicineDTO.MedicineRequestDTO;
import com.mt.pharmacy_be.dto.medicineDTO.MedicineResponseDTO;
import com.mt.pharmacy_be.entity.Medicine;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicineMapper {

    /**
     * Convert Entity to Response
     * Author: Thanh Truc
     * Date: 21/07/2015
     * Description: This method converts a Medicine entity to a MedicineResponseDTO.
     */
    MedicineResponseDTO toMedicineResponseDTO(Medicine medicine);

    /**
     * Convert Request to Entity
     * Author: Thanh Truc
     * Date: 22/07/2015
     * Description: This method converts a MedicineRequestDTO to a Medicine entity.
     */
    Medicine toMedicineEntity(MedicineRequestDTO requestDTO);

    /**
     * Convert CSV DTO to Entity
     * Author: Thanh Truc
     * Date: 28/07/2024
     * Description: This method converts a MedicineCsvDTO to a Medicine entity.
     */
    Medicine toMedicineEntity(MedicineCsvDTO requestDTO);
}
