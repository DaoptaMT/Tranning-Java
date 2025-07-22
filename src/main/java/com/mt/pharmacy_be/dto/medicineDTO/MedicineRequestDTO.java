package com.mt.pharmacy_be.dto.medicineDTO;

import com.mt.pharmacy_be.dto.unitDetailDTO.UnitDetailRequestDTO;
import com.mt.pharmacy_be.validation.Numeric;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * * DTO for creating or updating a medicine.
 * Author: Thanh Truc
 * Date: 21/07/2015
 * Description: This class represents the data transfer object for medicine requests,
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MedicineRequestDTO {
    @NotBlank(message = "NAME_NOT_BLANK")
    String name;

    @Numeric(message = "NUMERIC_NUMBER")
    String price;

    @Numeric(message = "NUMERIC_NUMBER")
    String quantity;

    @Numeric(message = "NUMERIC_NUMBER")
    String vat;

    @NotBlank(message = "NOTE_NOT_BLANK")
    String note;

    @NotBlank(message = "MAKER_NOT_BLANK")
    String maker;

    @NotBlank(message = "ORIGIN_NOT_BLANK")
    String origin;

    @Numeric(message = "NUMERIC_NUMBER")
    String retailProfit;

    @NotBlank(message = "ACTIVE_ELEMENT_NOT_BLANK")
    String activeElement;

    @Numeric(message = "NUMERIC_NUMBER")
    String kindOfMedicineId;

    @NotEmpty(message = "UNIT_DETAILS_NOT_EMPTY")
    @Valid
    List<UnitDetailRequestDTO> unitDetails;
}
