package com.example.bankcards.util.validation.validator;

import com.example.bankcards.service.UserService;
import com.example.bankcards.util.validation.annotation.EmailNotOccupy;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UserByEmailOccupyValidator implements ConstraintValidator<EmailNotOccupy, String> {
    private final UserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        boolean isOccupy = userService.isExistsByEmail(email);
        log.info("Email {} is occupy {}", email, isOccupy);
        return !isOccupy;
    }
}