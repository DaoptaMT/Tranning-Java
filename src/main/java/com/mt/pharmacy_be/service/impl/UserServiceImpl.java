package com.mt.pharmacy_be.service.impl;

import com.mt.pharmacy_be.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    @Override
    public Object getAllUser(int page, int pageSize) {
        return null;
    }
}
