package com.mt.pharmacy_be.service;

import com.mt.pharmacy_be.dto.userDTO.UserRequestDTO;
import com.mt.pharmacy_be.dto.userDTO.UserResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Object getAllUser(int page, int pageSize);

    UserResponseDTO register(UserRequestDTO request);
}
