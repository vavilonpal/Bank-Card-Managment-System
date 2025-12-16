package com.example.bankcards.dto.bankcard.response;

import com.example.bankcards.util.enums.bankcard.BankCardStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankCardResponse {
    private UUID id;
    private String cardNumber;
    private String cardHolder;
    private BigDecimal cardBalance;
    private Integer expirationMonth;
    private Integer expirationYear;
    private BankCardStatus cardStatus;
    private LocalDateTime createdAt;


    public static String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) return cardNumber;
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }
}
