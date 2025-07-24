package com.mt.pharmacy_be.service.impl;

import com.mt.pharmacy_be.enums.TokenType;
import com.mt.pharmacy_be.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.refresh-key}")
    private String refreshKey;

    /**
     * Generate a JWT token for the given user details.
     * Author: Thanh Truc
     * Date: 15/07/2025
     * Description: This method generates a JWT token with default claims and the username from the user details.
     */
    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Extracts the username from the JWT token.
     * Author: Thanh Truc
     * Date: 15/07/2025
     * Description: This method retrieves the username from the JWT token using the provided token type.
     */
    @Override
    public String extractUsername(String token, TokenType tokenType) {
        return extractClaims(token, Claims::getSubject, tokenType);
    }

    /**
     * Checks if the JWT token is valid for the given user details and token type.
     * Author: Thanh Truc
     * Date: 15/07/2025
     * Description: This method verifies if the token's username matches the user details and if the token is not expired.
     */
    @Override
    public Boolean isTokenValid(String token, UserDetails userDetails, TokenType tokenType) {
        final String username = extractUsername(token, tokenType);
        return (userDetails.getUsername().equals(username) && !isTokenExpired(token, tokenType));
    }

    /**
     * Generates a refresh token for the given user details.
     * Author: Thanh Truc
     * Date: 15/07/2025
     * Description: This method creates a refresh token with a default expiration time of 30 days.
     */
    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        long refreshExpiration = 604800000L; // 7 days by default
        return buildToken(new HashMap<>(), userDetails, refreshExpiration, TokenType.REFRESH_TOKEN);
    }

    /**
     * Generates a JWT token with the specified claims and user details.
     * Author: Thanh Truc
     * Date: 15/07/2025
     * Description: This method creates a JWT token with the provided claims, user details, and a default expiration time of 15 minutes.
     */
    private String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        long accessExpiration = 900000L; // 15 minutes by default
        return buildToken(claims, userDetails, accessExpiration, TokenType.ACCESS_TOKEN);
    }

    /**
     * Builds a JWT token with the specified claims, user details, expiration time, and token type.
     * Author: Thanh Truc
     * Date: 15/07/2025
     * Description: This method constructs a JWT token using the provided claims, user details, expiration time, and token type.
     */
    private String buildToken(Map<String, Object> claims, UserDetails userDetails, long expiration, TokenType tokenType) {
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey(tokenType), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Retrieves the signing key based on the token type.
     * Author: Thanh Truc
     * Date: 15/07/2025
     * Description: This method returns the appropriate signing key for the given token type,
     * either access or refresh token.
     */
    private Key getKey(TokenType tokenType) {
        byte[] keyBytes = new byte[0];
        if (tokenType.equals(TokenType.ACCESS_TOKEN)) {
            keyBytes = Decoders.BASE64.decode(secretKey);
        } else if (tokenType.equals(TokenType.REFRESH_TOKEN)) {
            keyBytes = Decoders.BASE64.decode(refreshKey);
        }
        return Keys.hmacShaKeyFor(keyBytes);

    }

    /**
     * Extracts claims from the JWT token using the provided token type.
     * Author: Thanh Truc
     * Date: 15/07/2025
     * Description: Returns claims containing the token's payload information.
     */
    private Claims extractClaims(String token, TokenType tokenType) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey(tokenType))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks if the JWT token is expired.
     * Author: Thanh Truc
     * Date: 15/07/2025
     * Description: This method checks if the token's expiration date is before the current date.
     */
    private boolean isTokenExpired(String token, TokenType tokenType) {
        return extractExpiration(token, tokenType).before(new Date());
    }

    /**
     * Extracts claims from the JWT token using the provided token type.
     * Author: Thanh Truc
     * Date: 15/07/2025
     * Description: This method retrieves the claims from the JWT token using the specified token type.
     */
    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver, TokenType tokenType) {
        final Claims claims = extractClaims(token, tokenType);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts the expiration date from the JWT token.
     * Author: Thanh Truc
     * Date: 15/07/2025
     * Description: This method retrieves the expiration date from the JWT token using the provided token type.
     */
    private Date extractExpiration(String token, TokenType tokenType) {
        return extractClaims(token, Claims::getExpiration, tokenType);
    }

}
