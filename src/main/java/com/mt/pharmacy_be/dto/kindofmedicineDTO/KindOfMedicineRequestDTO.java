package com.mt.pharmacy_be.dto.kindofmedicineDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class KindOfMedicineRequestDTO {


    @NotBlank(message = "CODE_INVALID")
    private String code;

    @NotBlank(message = "NAME_INVALID")
    private String name;

    private boolean flag_deleted = false;
}
