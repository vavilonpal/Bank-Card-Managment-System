package com.example.bankcards.dto.bankcard.request;


import com.example.bankcards.util.validation.annotation.TransferCardsIdsNotMatch;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@TransferCardsIdsNotMatch // Проверка на разность полученных id карт для перевода
public class TransferRequest {

    private UUID fromCardId;
    private UUID toCardId;

    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
}
