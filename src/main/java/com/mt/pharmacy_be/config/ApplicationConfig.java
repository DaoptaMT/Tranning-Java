package com.mt.pharmacy_be.config;

import com.mt.pharmacy_be.enums.ErrorCode;
import com.mt.pharmacy_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationConfig {

    UserRepository userRepository;

    /**
     * Provides UserDetailsService to load user details by username.
     * Author: Thanh Truc
     * Date: 15/07/2025
     * Description: Queries UserEntity from UserRepository, throws exception if not found.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorCode.USER_NOT_FOUND.getMessage()));
    }

    /**
     * Provides AuthenticationManager for user authentication.
     * Author: Thanh Truc
     * Date: 15/07/2025
     * Description: Uses AuthenticationConfiguration to manage authentication process.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Provides PasswordEncoder to encrypt user passwords.
     * Author: Thanh Truc
     * Date: 15/07/2025
     * Description: Uses BCryptPasswordEncoder for secure password hashing and verification.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
