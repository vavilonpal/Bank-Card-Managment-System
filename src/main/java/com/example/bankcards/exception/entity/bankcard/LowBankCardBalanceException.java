package com.example.bankcards.exception.entity.bankcard;

public class LowBankCardBalanceException extends BankCardException {
    public LowBankCardBalanceException(String message) {
        super(message);
    }
}
