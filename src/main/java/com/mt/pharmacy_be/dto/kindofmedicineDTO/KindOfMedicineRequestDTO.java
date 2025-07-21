package com.mt.pharmacy_be.dto.kindOfMedicineDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KindOfMedicineRequestDTO {


    @NotBlank(message = "CODE_INVALID")
    private String code;

    @NotBlank(message = "NAME_INVALID")
    private String name;

    private boolean flag_deleted = false;
}
