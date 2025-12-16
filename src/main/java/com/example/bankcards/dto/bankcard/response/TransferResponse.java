package com.example.bankcards.dto.bankcard.response;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponse {
    private BigDecimal transferAmount;
    private BigDecimal fromCardBalance;
    private BigDecimal toCardBalance;
    private final LocalDateTime transferTime = LocalDateTime.now();
}
