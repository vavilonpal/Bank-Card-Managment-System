package com.example.bankcards.exception.entity.bankcard;

public class SameCardTransferException extends BankCardException {
    public SameCardTransferException(String message) {
        super(message);
    }
}
