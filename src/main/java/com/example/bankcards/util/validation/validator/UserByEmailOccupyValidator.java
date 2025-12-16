package com.example.bankcards.util.validation.validator;

import com.example.bankcards.security.AuthenticationService;
import com.example.bankcards.service.UserService;
import com.example.bankcards.util.validation.annotation.EmailNotOccupy;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserByEmailOccupyValidator implements ConstraintValidator<EmailNotOccupy, String> {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    public boolean isValid(String email, ConstraintValidatorContext context) {
        UUID currentUserId = authenticationService.getCurrentUserIdOrNull();

        boolean exists;
        if (currentUserId == null) {
            exists = userService.existsByEmail(email);
        } else {
            exists = userService.existsByEmailAndNotUserId(email, currentUserId);
        }

        log.info("Email {} exists for another user: {}", email, exists);
        return !exists;
    }
}