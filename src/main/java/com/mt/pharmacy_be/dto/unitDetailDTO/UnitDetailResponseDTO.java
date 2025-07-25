package com.mt.pharmacy_be.dto.unitDetailDTO;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * DTO representing the details of a unit.
 * Author: Thanh Truc
 * Date: 21/07/2025
 * Description: This class contains the details of a unit, including its ID, name, and conversion unit.
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UnitDetailResponseDTO {
    Long id;
    Long unitId;
    String unitName;
    Long conversionUnit;
}
