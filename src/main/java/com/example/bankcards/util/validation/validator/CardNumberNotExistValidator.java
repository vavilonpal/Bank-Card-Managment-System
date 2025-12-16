package com.example.bankcards.util.validation.validator;

import com.example.bankcards.dto.bankcard.request.BankCardAdditionRequest;
import com.example.bankcards.service.BankCardsService;
import com.example.bankcards.util.validation.annotation.CardNumberNotExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CardNumberNotExistValidator implements ConstraintValidator<CardNumberNotExist, String> {
   private final BankCardsService cardsService;
    @Override
    public boolean isValid(String cardNumber, ConstraintValidatorContext context) {
        return !cardsService.isExistsByCardNumber(cardNumber);
    }
}
