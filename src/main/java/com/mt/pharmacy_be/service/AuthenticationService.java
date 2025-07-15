package com.mt.pharmacy_be.service;

import com.mt.pharmacy_be.dto.authenticationDTO.AuthenticationRequestDTO;
import com.mt.pharmacy_be.dto.authenticationDTO.AuthenticationResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    AuthenticationResponseDTO login(AuthenticationRequestDTO authenticationRequestDTO);
}
