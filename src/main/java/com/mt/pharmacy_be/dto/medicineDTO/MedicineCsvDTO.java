package com.mt.pharmacy_be.dto.medicineDTO;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * DTO for importing medicine data from a CSV file.
 * Author: Thanh Truc
 * Date: 28/07/2024
 * Description: This class represents the structure of a medicine record in the CSV file,
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MedicineCsvDTO {
    String name;
    Double price;
    Long quantity;
    Float vat;
    String note;
    String maker;
    String origin;
    Float retailProfit;
    String kindOfMedicineId;
    String activeElement;
}
