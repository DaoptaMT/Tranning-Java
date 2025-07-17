package com.mt.pharmacy_be.service;

import com.mt.pharmacy_be.enums.TokenType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface JwtService {

    String generateToken(UserDetails userDetails);

    String extractUsername(String token, TokenType tokenType);

    Boolean isTokenValid(String token, UserDetails userDetails, TokenType tokenType);

    String generateRefreshToken(UserDetails userDetails);
}
