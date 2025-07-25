package com.mt.pharmacy_be.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.mt.pharmacy_be.dto.ApiResponse;
import com.mt.pharmacy_be.enums.ErrorCode;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
@Order(1)
public class GlobalExceptionHandler {

    /**
     * Function: Handles custom application exceptions to type ApiException.
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
        ErrorCode errorCode = ErrorCode.INVALID_DATA;
        error.setCode(errorCode.getCode());
        error.setMessage(errorCode.getMessage());

        Map<String, String> fieldErrors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String errorName = fieldError.getDefaultMessage();
            String errorMessage;
            try {
                errorMessage = ErrorCode.valueOf(errorName).getMessage();
            } catch (IllegalArgumentException e) {
                errorMessage = errorName;
            }
            fieldErrors.put(fieldError.getField(), errorMessage);
        });

        error.additionalProperty("data", fieldErrors);
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

    /**
     * Function: Handles JSON parsing errors that occur when the request body cannot be read.
     * Author: Thanh Truc
     * Date: 23/07/2025
     * Description: Returns HTTP 400 Bad Request response with error details,
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleJsonParseError(HttpMessageNotReadableException ex) {
        var error = new ApiResponse<>();
        ErrorCode errorCode = ErrorCode.INVALID_DATA;
        error.setCode(errorCode.getCode());
        error.setMessage(errorCode.getMessage());

        Map<String, String> errors = new HashMap<>();
        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException ife) {
            String fieldName = ife.getPath().isEmpty() ? "unknown" : ife.getPath().getFirst().getFieldName();
            String errorMessage;
            try {
                String errorsCode = fieldName.toUpperCase() + "_FORMAT_INVALID";
                errorMessage = ErrorCode.valueOf(errorsCode).getMessage();
            } catch (IllegalArgumentException e) {
                errorMessage = "Invalid format for field " + fieldName;
            }
            errors.put(fieldName, errorMessage);
        } else {
            errors.put("error", errorCode.getMessage());
        }

        error.additionalProperty("data", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Function: Handles bad credentials exceptions for authentication failures.
     * Author: Thanh Truc
     * Date: 22/07/2025
     * Description: Returns HTTP 401 Unauthorized response with error details indicating invalid credentials.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {
        ErrorCode errorCode = ErrorCode.INVALID_CREDENTIALS;
        ApiResponse<?> response = ApiResponse.builder()
                .message(errorCode.getMessage())
                .code(errorCode.getCode())
                .build();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Function: Handles authorization denial exceptions
     * Author: Thanh Truc
     * Date: 24/07/2025
     * Description: Returns HTTP 403 Forbidden response when authorization is denied
     */
    @ExceptionHandler(value = {AuthorizationDeniedException.class})
    public ResponseEntity<?> handleAuthorizationDeniedException(AuthorizationDeniedException exception) {
        ErrorCode errorCode = ErrorCode.FORBIDDEN;
        ApiResponse<?> response = ApiResponse.builder()
                .message(errorCode.getMessage())
                .code(errorCode.getCode())
                .build();

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}