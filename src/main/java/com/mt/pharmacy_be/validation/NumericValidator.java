package com.mt.pharmacy_be.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator for the Numeric annotation.
 * Author: Thanh Truc
 * Date: 22/07/2025
 * Description: This class implements the logic to validate that a string value is numeric and greater than zero.
 */
public class NumericValidator implements ConstraintValidator<Numeric, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        if (!value.matches("\\d+(\\.\\d+)?")) {
            return false;
        }
        try {
            return Double.parseDouble(value) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}