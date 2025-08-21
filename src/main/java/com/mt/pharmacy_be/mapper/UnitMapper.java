package com.mt.pharmacy_be.mapper;

import com.mt.pharmacy_be.dto.unitDTO.UnitRequestDTO;
import com.mt.pharmacy_be.dto.unitDTO.UnitResponseDTO;
import com.mt.pharmacy_be.entity.UnitEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UnitMapper {

    /** Converts a UnitRequestDTO to a Unit entity.
     * Author: Thanh Truc
     * Date: 06/08/2025
     * Description: This method maps a UnitRequestDTO to a Unit entity.
     */
    UnitResponseDTO toUnitResponseDTO(UnitEntity unit);

    /** Converts a Unit entity to a UnitRequestDTO.
     * Author: Thanh Truc
     * Date: 06/08/2025
     * Description: This method maps a Unit entity to a UnitRequestDTO.
     */
    UnitEntity toUnit(UnitRequestDTO unitRequestDTO);

}
