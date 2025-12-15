package com.example.bankcards.util.validation.validator;

import com.example.bankcards.entity.User;
import com.example.bankcards.service.UserService;
import com.example.bankcards.util.validation.annotation.PhoneNumberNotOccupy;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UserByPhoneNumberOccupyValidator implements ConstraintValidator<PhoneNumberNotOccupy, String> {
    private final UserService userService;

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        boolean isOccupy = userService.isExistsByPhoneNumber(phoneNumber);
        log.info("Phone number {} is occupy {}", phoneNumber, isOccupy);
        return !isOccupy;
    }

}
