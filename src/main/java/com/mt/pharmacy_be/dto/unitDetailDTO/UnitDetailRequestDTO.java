package com.mt.pharmacy_be.dto.unitDetailDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    Long unitId;

    @NotNull(message = "CONVERSION_UNIT_NOT_NULL")
    @Min(value = 1, message = "CONVERSION_UNIT_MIN_1")
    Long conversionUnit;
}
