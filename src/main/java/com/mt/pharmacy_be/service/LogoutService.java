package com.mt.pharmacy_be.service;

import com.mt.pharmacy_be.entity.UserEntity;
import com.mt.pharmacy_be.enums.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LogoutService implements LogoutHandler {

    JwtService jwtService;
    UserDetailsService userDetailsService;
    RedisTokenService redisTokenService;

    /**
     * Handles the logout process by revoking all tokens associated with the user.
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: Revokes all tokens associated with the user's ID in Redis.
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authorizationHeader = request.getHeader(AUTHORIZATION);

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            return;
        }

        final String jwt = authorizationHeader.substring(7);
        String username = jwtService.extractUsername(jwt, TokenType.ACCESS_TOKEN);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UUID userId = ((UserEntity) userDetails).getId();

        redisTokenService.revokeAllUserTokens(userId);
    }
}
