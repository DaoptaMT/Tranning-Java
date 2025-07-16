package com.mt.pharmacy_be.config;

import com.mt.pharmacy_be.entity.UserEntity;
import com.mt.pharmacy_be.enums.TokenType;
import com.mt.pharmacy_be.service.JwtService;
import com.mt.pharmacy_be.service.RedisTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    JwtService jwtService;
    UserDetailsService userDetailsService;
    RedisTokenService redisTokenService;

    /**
     * Filters each HTTP request to check for a valid JWT token.
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: Extracts token from "Authorization" header, validates it against Redis and JWT service,
     *              sets the authentication in the security context if valid, and handles expired or invalid tokens.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwtToken = authorizationHeader.substring(7);

        try{
            final String userName = jwtService.extractUsername(jwtToken, TokenType.ACCESS_TOKEN);
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                UUID userId = ((UserEntity) userDetails).getId();

                boolean isValidInRedis = redisTokenService.isTokenValid(userId, jwtToken);

                if (jwtService.isTokenValid(jwtToken, userDetails, TokenType.ACCESS_TOKEN) && isValidInRedis){
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null,
                                    userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            // Token expired - respond with Unauthorized (401)
            log.warn("JWT Token has expired: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token has expired. Please login again.\"}");
            response.getWriter().flush();

        } catch (Exception e) {
            // Any other authentication errors
            log.error("Authentication failed: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Authentication failed. Token invalid.\"}");
            response.getWriter().flush();
        }

    }
}
