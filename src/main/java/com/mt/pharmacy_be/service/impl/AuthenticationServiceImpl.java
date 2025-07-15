package com.mt.pharmacy_be.service.impl;

import com.mt.pharmacy_be.dto.authenticationDTO.AuthenticationRequestDTO;
import com.mt.pharmacy_be.dto.authenticationDTO.AuthenticationResponseDTO;
import com.mt.pharmacy_be.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public AuthenticationResponseDTO login(AuthenticationRequestDTO authenticationRequestDTO) {
        return null;
    }
}
