package com.example.bankcards.util.validation.validator;

import com.example.bankcards.dto.bankcard.request.TransferRequest;
import com.example.bankcards.util.validation.annotation.TransferCardsIdsNotMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class TransferCardsNotMatchIdsValidator implements ConstraintValidator<TransferCardsIdsNotMatch, TransferRequest> {
    @Override
    public boolean isValid(TransferRequest request, ConstraintValidatorContext context) {
        return !request.getFromCardId().equals(request.getToCardId());
    }
}
