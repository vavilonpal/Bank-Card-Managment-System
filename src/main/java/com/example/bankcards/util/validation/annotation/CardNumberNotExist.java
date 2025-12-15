package com.example.bankcards.util.validation.annotation;


import com.example.bankcards.util.validation.validator.CardNumberNotExistValidator;
import com.example.bankcards.util.validation.validator.UserByEmailOccupyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CardNumberNotExistValidator.class)
@Documented
public @interface CardNumberNotExist {
    String message() default "Card number already exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
