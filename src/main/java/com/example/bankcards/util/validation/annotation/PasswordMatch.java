package com.example.bankcards.util.validation.annotation;

import com.example.bankcards.util.validation.validator.PasswordMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchValidator.class)
@Documented
public @interface PasswordMatch {
    String message() default "Passwords doesn't match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
