package com.mt.pharmacy_be.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to validate that a string value is numeric and greater than zero.
 * Author: Thanh Truc
 * Date: 22/07/2025
 * Description: This annotation can be applied to fields or parameters to ensure they contain a valid numeric value.
 */
@Constraint(validatedBy = NumericValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Numeric {
    String message() default "VALUE_MUST_BE_NUMERIC";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}