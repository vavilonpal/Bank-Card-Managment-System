package com.example.bankcards.exception.entity.bankcard;

public class NegativeTransferAmountException extends BankCardException {
    public NegativeTransferAmountException(String message) {
        super(message);
    }
}
