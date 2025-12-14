package com.example.bankcards.util.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.example.bankcards.dto.user.request.RegisterUserRequest;
import com.example.bankcards.util.validation.annotation.PasswordMatch;


public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, RegisterUserRequest> {

    @Override
    public boolean isValid(RegisterUserRequest userRequest, ConstraintValidatorContext context) {
        String password = userRequest.getPassword();
        String passwordConfirm = userRequest.getPasswordConfirm();

        if (!password.equals(passwordConfirm)){
            // Изменяем дефолтной контекст, чтобы в GlobalErrors вместо error.getObjectName() возвращать "fieldName": "passwordConfirm"
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Passwords do not match")
                    .addPropertyNode("passwordConfirm")
                    .addConstraintViolation();
            return false;
        }
        userRequest.setPasswordsIsMatch(true);
        return true;
    }
}