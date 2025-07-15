package com.mt.pharmacy_be.dto.authenticationDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mt.pharmacy_be.enums.TokenType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponseDTO {
    @Enumerated(EnumType.STRING)
    TokenType tokenType;
    UUID id;
    String email;
    String roles;
    String message;
    @JsonProperty("accessToken")
    String accessToken;
    @JsonProperty("refreshToken")
    String refreshToken;
}
