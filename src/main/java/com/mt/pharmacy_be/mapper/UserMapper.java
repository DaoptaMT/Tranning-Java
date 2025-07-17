package com.mt.pharmacy_be.mapper;

import com.mt.pharmacy_be.dto.userDTO.UserRequestDTO;
import com.mt.pharmacy_be.dto.userDTO.UserResponseDTO;
import com.mt.pharmacy_be.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Converts UserRequestDTO to UserEntity.
     * Author: Thanh Truc
     * Date: 17/07/2025
     * Description: This method maps the fields from UserRequestDTO to UserEntity.
     */
    UserEntity toUserEntity(UserRequestDTO userRequestDTO);

    /**
     * Converts UserEntity to UserResponseDTO.
     * Author: Thanh Truc
     * Date: 17/07/2025
     * Description: This method maps the fields from UserEntity to UserResponseDTO.
     */
    UserResponseDTO toUserResponseDTO(UserEntity userEntity);
}
