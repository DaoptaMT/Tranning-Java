package com.mt.pharmacy_be.dto.kindofmedicineDTO;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KindOfMedicineResponseDTO {
//    private Long id;
    private String code;
    private String name;
    private boolean flag_deleted;

}
