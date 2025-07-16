package com.mt.pharmacy_be.aditing;

import com.mt.pharmacy_be.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAudiAware implements AuditorAware<String> {

    /**
     * Function: Retrieves the current authenticated user's username for auditing purposes.
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: Returns the username from SecurityContext if the user is authenticated,
     *              otherwise returns an empty Optional.
     */
    @Override
    public @NonNull Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        UserEntity userPrincipal = (UserEntity) authentication.getPrincipal();
        return Optional.ofNullable(userPrincipal.getUsername());
    }
}
