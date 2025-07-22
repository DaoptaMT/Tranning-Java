package com.mt.pharmacy_be.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    GET_SUCCESSFUL(1010, "Get successful", HttpStatus.OK),
    ADD_SUCCESSFUL(1011, "Add successful", HttpStatus.OK),
    DELETE_SUCCESSFUL(1012, "Delete successful", HttpStatus.OK),
    UPDATE_SUCCESSFUL(1013, "Update successful", HttpStatus.OK),
    INVALID_DATA(1014, "Invalid data", HttpStatus.BAD_REQUEST),
    REVIEW_NOT_FOUND(1020, "Review not found", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(1021, "Invalid or expired token", HttpStatus.BAD_REQUEST),

    // Auth and User 1***
    EMAIL_INVALID(1000, "Email invalid", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1001, "Password must be not blank", HttpStatus.BAD_REQUEST),
    FULL_NAME_INVALID(1003, "Full name must be not null", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1004, "User not found", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTS(1004, "Email exists", HttpStatus.BAD_REQUEST),
    USERNAME_EXISTS(1004, "Username exists", HttpStatus.BAD_REQUEST),
    NEW_PASSWORD_INVALID(1008, "new password invalid", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1009, "Username invalid", HttpStatus.BAD_REQUEST),
    TOKEN_NOT_BLANK(1010, "Token must be not blank", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_FORMAT(1011, "Password must contain at least 8 characters, including uppercase, " +
            "lowercase, number, and special character", HttpStatus.BAD_REQUEST),

    // Role 2***
    ROLE_INVALID(2000, "Role must be not blank", HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(2001, "Role not found", HttpStatus.BAD_REQUEST),
    ROLE_INVALID_TYPE(2002, "Role invalid type", HttpStatus.BAD_REQUEST),

    //Kind 3***
    KIND_NOT_FOUND(3000, "Kind not found", HttpStatus.BAD_REQUEST),
//    KIND_INVALID(3001, "Kind of medicine not found with id: ", HttpStatus.BAD_REQUEST)
    CODE_INVALID(3001, "Code invalid ", HttpStatus.BAD_REQUEST),
    NAME_INVALID(3002, "Name invalid ", HttpStatus.BAD_REQUEST),
    KIND_EXISTED(3003, "Code exists", HttpStatus.BAD_REQUEST),
    ;


    Integer code;
    String message;
    HttpStatusCode statusCode;
}
