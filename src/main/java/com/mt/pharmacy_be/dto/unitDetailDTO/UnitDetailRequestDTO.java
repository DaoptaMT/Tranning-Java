package com.mt.pharmacy_be.dto.unitDetailDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * DTO for requesting unit detail information.
 * Author: Thanh Truc
 * Date: 22/07/2025
 * Description: This class is used to encapsulate the request data for unit details,
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UnitDetailRequestDTO {
    @NotNull(message = "UNIT_ID_NOT_NULL")
    @Positive(message = "UNIT_ID_MIN_1")
    Long unitId;

    @NotNull(message = "CONVERSION_UNIT_NOT_NULL")
    @Positive(message = "CONVERSION_UNIT_MIN_1")
    Long conversionUnit;
}
