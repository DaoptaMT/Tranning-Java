package com.mt.pharmacy_be.dto.userDTO;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

/**
 * DTO for user response.
 * Author: Thanh Truc
 * Date: 17/07/2025
 * Description: This class is used to encapsulate the data returned in user-related responses.
 */
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponseDTO {
    UUID id;
    String username;
    String roles;
}
