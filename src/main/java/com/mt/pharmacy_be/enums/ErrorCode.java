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
    VALUE_MUST_BE_NUMERIC(1022, "Value must be numeric", HttpStatus.BAD_REQUEST),
    INVALID_CREDENTIALS(1023, "Incorrect username or password", HttpStatus.UNAUTHORIZED),
    FORBIDDEN(1024, "You do not have permission to access this resource", HttpStatus.FORBIDDEN),
    FILES_NOT_EMPTY(1025, "File list must not be null or empty", HttpStatus.BAD_REQUEST),

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

    // Medicine 3***
    MEDICINE_NOT_FOUND(3000, "Medicine not found", HttpStatus.BAD_REQUEST),
    CODE_NOT_BLANK(3001, "Code must be not blank", HttpStatus.BAD_REQUEST),
    NAME_NOT_BLANK(3002, "Name must be not blank", HttpStatus.BAD_REQUEST),
    PRICE_NOT_NULL(3003, "Price must be not null", HttpStatus.BAD_REQUEST),
    PRICE_GREATER_THAN_0(3004, "Price must be greater than 0", HttpStatus.BAD_REQUEST),
    QUANTITY_NOT_NULL(3005, "Quantity must be not null", HttpStatus.BAD_REQUEST),
    QUANTITY_MIN_1(3006, "Quantity must be greater than or equal to 1", HttpStatus.BAD_REQUEST),
    VAT_NOT_NULL(3007, "VAT must be not null", HttpStatus.BAD_REQUEST),
    VAT_MIN_INVALID(3008, "VAT min invalid", HttpStatus.BAD_REQUEST),
    NOTE_NOT_BLANK(3009, "Note must be not blank", HttpStatus.BAD_REQUEST),
    MAKER_NOT_BLANK(3010, "Maker must be not blank", HttpStatus.BAD_REQUEST),
    ORIGIN_NOT_BLANK(3011, "Origin must be not blank", HttpStatus.BAD_REQUEST),
    RETAIL_PROFIT_NOT_NULL(3012, "Retail profits must be not null", HttpStatus.BAD_REQUEST),
    ACTIVE_ELEMENT_NOT_BLANK(3013, "Active element must be not blank", HttpStatus.BAD_REQUEST),
    IMAGES_NOT_EMPTY(3014, "Images must be not empty", HttpStatus.BAD_REQUEST),
    KIND_OF_MEDICINE_ID_NOT_NULL(3015, "Kind of medicine ID must be not null", HttpStatus.BAD_REQUEST),
    UNIT_DETAILS_NOT_EMPTY(3016, "Unit details must be not empty", HttpStatus.BAD_REQUEST),
    FAILED_TO_UPLOAD_IMAGE(3017, "Failed to upload image", HttpStatus.INTERNAL_SERVER_ERROR),
    NUMERIC_NUMBER(3018, "Not blank, must be numeric, and greater than 0", HttpStatus.BAD_REQUEST),
    RETAIL_PROFIT_MIN_INVALID(3019, "Retail profit must be greater than or equal to 0", HttpStatus.BAD_REQUEST),
    KIND_OF_MEDICINE_ID_INVALID(3020, "Kind of medicine ID must be greater than 0", HttpStatus.BAD_REQUEST),
    IMAGE_URLS_NOT_EMPTY(3021, "Image URLs must be not empty", HttpStatus.BAD_REQUEST),

    // Unit Detail 4***
    UNIT_DETAIL_NOT_FOUND(4000, "Unit detail not found", HttpStatus.BAD_REQUEST),
    CONVERSION_UNIT_MIN_1(4001, "Conversion unit must be greater than or equal to 1", HttpStatus.BAD_REQUEST),
    CONVERSION_UNIT_NOT_NULL(4002, "Conversion unit must be not null", HttpStatus.BAD_REQUEST),

    // Kind of Medicine 5***
    KIND_OF_MEDICINE_NOT_FOUND(5000, "Kind of medicine not found", HttpStatus.BAD_REQUEST),
    KIND_NOT_FOUND(5001, "Kind not found", HttpStatus.BAD_REQUEST),
    CODE_INVALID(5002, "Code invalid ", HttpStatus.BAD_REQUEST),
    NAME_INVALID(5003, "Name invalid ", HttpStatus.BAD_REQUEST),

    // Unit 6***
    UNIT_NOT_FOUND(6000, "Unit not found", HttpStatus.BAD_REQUEST),
    UNIT_ID_NOT_NULL(6001, "Unit ID must be not null", HttpStatus.BAD_REQUEST),
    UNIT_ID_MIN_1(6002, "Unit ID must be greater than or equal to 1", HttpStatus.BAD_REQUEST),

    // Image Medicine 7***
    IMAGE_NOT_FOUND(7000, "Image not found", HttpStatus.BAD_REQUEST),

    // Batch Import 8***
    CSV_INVALID(8000, "Please upload a CSV file", HttpStatus.BAD_REQUEST),
    CSV_ALREADY_PROCESSED(8001, "This file has already been processed", HttpStatus.BAD_REQUEST),
    CSV_DOWNLOAD_INVALID(8002, "CSV file is invalid or corrupted", HttpStatus.BAD_REQUEST),

    ;

    Integer code;
    String message;
    HttpStatusCode statusCode;
}
