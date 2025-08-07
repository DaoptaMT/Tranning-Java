package com.mt.pharmacy_be.dto.unitDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UnitRequestDTO {
    @NotBlank(message = "UNIT_NAME_INVALID")
    private String name;
}
