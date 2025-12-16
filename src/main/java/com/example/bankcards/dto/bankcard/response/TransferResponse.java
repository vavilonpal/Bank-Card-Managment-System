package com.example.bankcards.dto.bankcard.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TransferResponse {
    private BigDecimal transferAmount;
    private BigDecimal fromCardBalance;
    private BigDecimal toCardBalance;
    private final LocalDateTime transferTime = LocalDateTime.now();
}
