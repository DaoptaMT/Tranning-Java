package com.mt.pharmacy_be.dto.medicineDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * * DTO for creating or updating a medicine.
 * Author: Thanh Truc
 * Date: 21/07/2015
 * Description: This class represents the data transfer object for medicine requests,
 */
@Getter
@Setter
public class MedicineRequestDTO {
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
    Long kindOfMedicineId;
    Long conversionUnit;
    String unitName;
    Long UnitId;

}
