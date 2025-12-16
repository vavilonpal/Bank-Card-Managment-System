package com.example.bankcards.util.validation.validator;

import com.example.bankcards.security.AuthenticationService;
import com.example.bankcards.service.UserService;
import com.example.bankcards.util.validation.annotation.PhoneNumberNotOccupy;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserByPhoneNumberOccupyValidator implements ConstraintValidator<PhoneNumberNotOccupy, String> {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        UUID currentUserId = authenticationService.getCurrentUserIdOrNull();

        boolean exists;
        if (currentUserId == null) {
            exists = userService.existsByPhoneNumber(phoneNumber);
        } else {
            exists = userService.existsByPhoneNumberAndNotUserId(phoneNumber, currentUserId);
        }

        log.info("Phone Number {} exists for another user: {}", phoneNumber, exists);
        return !exists;
    }

}
