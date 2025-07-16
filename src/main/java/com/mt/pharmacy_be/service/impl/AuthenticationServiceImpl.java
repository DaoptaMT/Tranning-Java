package com.mt.pharmacy_be.service.impl;

import com.mt.pharmacy_be.dto.authenticationDTO.AuthenticationRequestDTO;
import com.mt.pharmacy_be.dto.authenticationDTO.AuthenticationResponseDTO;
import com.mt.pharmacy_be.entity.UserEntity;
import com.mt.pharmacy_be.enums.ErrorCode;
import com.mt.pharmacy_be.enums.TokenType;
import com.mt.pharmacy_be.exception.ApiException;
import com.mt.pharmacy_be.repository.UserRepository;
import com.mt.pharmacy_be.service.AuthenticationService;
import com.mt.pharmacy_be.service.JwtService;
import com.mt.pharmacy_be.service.RedisTokenService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements AuthenticationService {

    UserRepository userRepository;
    AuthenticationManager authenticationManager;
    JwtService jwtService;
    RedisTokenService redisTokenService;

    /**
     * Handles user login requests.
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: This method processes user login by validating credentials and generating JWT tokens.
     */
    @Override
    public AuthenticationResponseDTO login(AuthenticationRequestDTO authenticationRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequestDTO.getUsername(),
                        authenticationRequestDTO.getPassword()
                )
        );

        var user = userRepository.findByUsername(authenticationRequestDTO.getUsername())
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return buildAuthenticationResponse(user, accessToken, refreshToken, "Login success");
    }

    /**
     * Builds the authentication response containing user details and tokens.
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: This method constructs the response object with user information and JWT tokens,
     */
    private AuthenticationResponseDTO buildAuthenticationResponse(UserEntity user, String accessToken,
                                                                  String refreshToken, String message) {
        redisTokenService.revokeAllUserTokens(user.getId());
        redisTokenService.saveToken(user, refreshToken, 15); // 15 minutes
        redisTokenService.saveToken(user, refreshToken, 10080); // 7 days

        return AuthenticationResponseDTO.builder()
                .tokenType(TokenType.BEARER)
                .id(user.getId())
                .username(user.getUsername())
                .roles(user.getAuthorities().toString())
                .message(message)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
