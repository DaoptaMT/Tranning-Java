package com.mt.pharmacy_be.dto.medicineDTO;

import com.mt.pharmacy_be.dto.kindOfMedicineDTO.KindOfMedicineResponseDTO;
import com.mt.pharmacy_be.dto.unitDetailDTO.UnitDetailResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DTO for returning medicine details.
 * Author: Thanh Truc
 * Date: 21/07/2015
 * Description: This class represents the data transfer object for medicine responses,
 */
@Getter
@Setter
public class MedicineResponseDTO {
    Long id;
    String code;
    String name;
    Double price;
    Integer quantity;
    Float vat;
    String note;
    String maker;
    String origin;
    Float retailProfit;
    String activeElement;
    List<String> images;
    KindOfMedicineResponseDTO kindOfMedicine;
    List<UnitDetailResponseDTO> unitDetails;
}
