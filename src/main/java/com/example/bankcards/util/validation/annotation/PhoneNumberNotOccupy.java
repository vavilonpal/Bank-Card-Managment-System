package com.example.bankcards.util.validation.annotation;

import com.example.bankcards.util.validation.validator.UserByEmailOccupyValidator;
import com.example.bankcards.util.validation.validator.UserByPhoneNumberOccupyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserByPhoneNumberOccupyValidator.class)
@Documented
public @interface PhoneNumberNotOccupy {
    String message() default "Email already uses";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
