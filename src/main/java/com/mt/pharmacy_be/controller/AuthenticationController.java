package com.mt.pharmacy_be.controller;

import com.mt.pharmacy_be.dto.authenticationDTO.AuthenticationRequestDTO;
import com.mt.pharmacy_be.service.AuthenticationService;
import com.mt.pharmacy_be.util.JsonResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    AuthenticationService authenticationService;

    /**
     * Handles user login requests.
     * Author: Thanh Truc
     * Date: 15/07/2025
     * Description: This endpoint processes user login by validating credentials
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody @Valid AuthenticationRequestDTO request){
        return JsonResponse.ok(authenticationService.login(request));
    }

    /**
     * Handles user registration requests.
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: This endpoint processes user registration by creating a new user account
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        return JsonResponse.ok(authenticationService.refreshToken(request));
    }
}
