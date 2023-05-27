package com.example.demo.validation.service;

import com.example.demo.validation.annotation.NullableNotBlank;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NullableNotBlankValidator implements ConstraintValidator<NullableNotBlank, String> {

     public boolean isValid(String value, ConstraintValidatorContext context) {
         return value == null || !value.isBlank();
    }
}
