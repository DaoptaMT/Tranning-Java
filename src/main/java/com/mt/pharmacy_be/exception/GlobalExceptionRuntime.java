package com.mt.pharmacy_be.exception;

import com.mt.pharmacy_be.dto.ApiResponse;
import com.mt.pharmacy_be.enums.ErrorCode;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Order(2)
public class GlobalExceptionRuntime {

    /**
     * Function: Handles uncaught RuntimeExceptions
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: Provides a fallback handler for any uncaught runtime exceptions,
     * returning HTTP 500 Internal Server Error with generic error details.
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException exception) {
        var error = new ApiResponse<>();
        ErrorCode errorCode = ErrorCode.INVALID_DATA;

        error.setCode(errorCode.getCode());
        error.setMessage(errorCode.getMessage());

        Map<String, String> details = new HashMap<>();
        details.put("error", exception.getClass().getSimpleName());
        details.put("message", exception.getMessage());

        error.additionalProperty("data", details);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
