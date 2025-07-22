package com.mt.pharmacy_be.dto.medicineDTO;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * DTO for searching medicines with various criteria.
 * Author: Thanh Truc
 * Date: 22/07/2025
 * Description: This class encapsulates the search criteria for medicines,
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MedicineSearchRequestDTO {
    String code;
    String name;
    String minPrice;
    String maxPrice;
    String minQuantity;
    String maxQuantity;
    String maker;
    String origin;
    String activeElement;
    String kindOfMedicineId;
    String sortPrice;
}
