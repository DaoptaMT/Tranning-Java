package com.mt.pharmacy_be.service;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Object getAllUser(int page, int pageSize);
}
