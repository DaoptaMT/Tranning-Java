package com.mt.pharmacy_be.dto.authenticationDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequestDTO {

    @NotBlank(message = "USERNAME_INVALID")
    String username;

    @NotBlank(message = "PASSWORD_INVALID")
    String password;
}
