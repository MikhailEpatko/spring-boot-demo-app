package com.example.demo.validation.annotation;

import com.example.demo.validation.service.NullableNotBlankValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = NullableNotBlankValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NullableNotBlank {

    String message() default "Не может быть пустым или состоять из пробельных символов";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
