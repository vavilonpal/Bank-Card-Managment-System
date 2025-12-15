package com.example.bankcards.util.validation.annotation;


import com.example.bankcards.util.validation.validator.UserByEmailOccupyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserByEmailOccupyValidator.class)
@Documented
public @interface EmailNotOccupy {
    String message() default "Email already uses";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
