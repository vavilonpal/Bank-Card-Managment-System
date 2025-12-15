package com.example.bankcards.util.validation.annotation;

import com.example.bankcards.util.validation.validator.TransferCardsNotMatchIdsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TransferCardsNotMatchIdsValidator.class)
@Documented
public @interface TransferCardsIdsNotMatch {
    String message() default "From card id and to card must be different";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
