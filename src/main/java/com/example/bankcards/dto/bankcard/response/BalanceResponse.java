package com.example.bankcards.dto.bankcard.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
public class BalanceResponse {
    private String cardNumber;
    private BigDecimal balance;

    public BalanceResponse(String cardNumber, BigDecimal balance) {
        this.cardNumber = BankCardResponse.maskCardNumber(cardNumber);
        this.balance = balance;
    }

    public void setCardNumber(String cardNumber){
        this.cardNumber = BankCardResponse.maskCardNumber(cardNumber);
    }
}
