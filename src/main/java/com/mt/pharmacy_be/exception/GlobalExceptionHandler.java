package com.mt.pharmacy_be.exception;

import com.mt.pharmacy_be.dto.ApiResponse;
import com.mt.pharmacy_be.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Function: Handles custom application exceptions of type ApiException.
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: Returns a response with appropriate HTTP status and error details based on the ApiException's error code.
     */
    @ExceptionHandler(value = ApiException.class)
    ResponseEntity<?> handlingAppException(ApiException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    /**
     * Function: Handles validation exceptions thrown when method arguments fail validation.
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: Builds a response containing validation error codes and messages for each invalid field,
     * returning HTTP 400 Bad Request.
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<?> handlingValidException(MethodArgumentNotValidException exception) {
        var error = new ApiResponse<>();
        String errorName = ErrorCode
                .valueOf(exception.getBindingResult().getFieldErrors().getFirst().getDefaultMessage())
                .getMessage();
        error.setCode(ErrorCode.INVALID_DATA.getCode());
        error.setMessage(errorName);
        exception.getBindingResult().getFieldErrors().forEach(fieldError ->
                error.additionalProperty(fieldError.getField(), errorName)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Function: Handles access denied exceptions for unauthorized access attempts.
     * Author: Thanh Truc
     * Date: 16/07/2025
     * Description: Returns HTTP 401 Unauthorized response with error details.
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<?> handlingAccessDeniedException() {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return ResponseEntity.status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

}