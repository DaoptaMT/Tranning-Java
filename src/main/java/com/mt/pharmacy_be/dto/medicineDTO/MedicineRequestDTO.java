package com.mt.pharmacy_be.dto.medicineDTO;

import com.mt.pharmacy_be.dto.unitDetailDTO.UnitDetailRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
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

    //@JsonDeserialize(using = LocalDateDeserializer.class)
    @NotNull(message = "PRICE_NOT_NULL")
    @DecimalMin(value = "0.0", inclusive = false, message = "PRICE_GREATER_THAN_0")
    BigDecimal price;

    @NotNull(message = "QUANTITY_NOT_NULL")
    @Min(value = 1, message = "QUANTITY_MIN_1")
    Integer quantity;

    @NotNull(message = "VAT_NOT_NULL")
    @DecimalMin(value = "0.0", message = "VAT_MIN_INVALID")
    Float vat;

    @NotBlank(message = "NOTE_NOT_BLANK")
    String note;

    @NotBlank(message = "MAKER_NOT_BLANK")
    String maker;

    @NotBlank(message = "ORIGIN_NOT_BLANK")
    String origin;

    @NotNull(message = "RETAIL_PROFIT_NOT_NULL")
    @DecimalMin(value = "0.0", message = "RETAIL_PROFIT_MIN_INVALID")
    Float retailProfit;

    @NotBlank(message = "ACTIVE_ELEMENT_NOT_BLANK")
    String activeElement;

    @NotNull(message = "KIND_OF_MEDICINE_ID_NOT_NULL")
    @Positive(message = "KIND_OF_MEDICINE_ID_INVALID")
    Long kindOfMedicineId;

    @NotEmpty(message = "UNIT_DETAILS_NOT_EMPTY")
    @Valid
    List<UnitDetailRequestDTO> unitDetails;

    @NotEmpty(message = "IMAGE_URLS_NOT_EMPTY")
    List<String> imageUrls;
}
