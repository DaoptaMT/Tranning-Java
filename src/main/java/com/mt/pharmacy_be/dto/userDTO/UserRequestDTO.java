package com.mt.pharmacy_be.dto.userDTO;

import com.mt.pharmacy_be.enums.RoleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * DTO for user registration requests.
 * Author: Thanh Truc
 * Date: 17/07/2025
 * Description: This class is used to encapsulate the data required for user registration.
 */
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequestDTO {
    @NotBlank(message = "USERNAME_INVALID")
    String username;

    @NotBlank(message = "PASSWORD_INVALID")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "PASSWORD_NOT_FORMAT")
    String password;

    @NotEmpty(message = "ROLE_INVALID")
    List<RoleType> roles;
}
