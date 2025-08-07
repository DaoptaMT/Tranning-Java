package com.mt.pharmacy_be.dto.kindOfMedicineDTO;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * DTO representing a type of medicine.
 * Author: Thanh Truc
 * Date: 21/07/2025
 * Description: Contains basic information about a medicine type, including id, code, and name,
 *              used for returning response data to the client.
 */
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KindOfMedicineResponseDTO {
    Long id;
    String code;
    String name;
}
