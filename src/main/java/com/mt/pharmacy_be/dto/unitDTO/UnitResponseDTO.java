package com.mt.pharmacy_be.dto.unitDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UnitResponseDTO {
    private Long id;
    private String name;
}
