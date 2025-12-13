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

        if (password.isBlank() || passwordConfirm.isBlank()){
            return false;
        }
        if (!password.equals(passwordConfirm)){
            return false;
        }
        userRequest.setPasswordsIsMatch(true);
        return true;
    }
}