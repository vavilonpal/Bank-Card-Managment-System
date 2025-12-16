package com.example.bankcards.exception.entity.bankcard;

public class BankCardByUserIdNotFoundException extends RuntimeException {
    public BankCardByUserIdNotFoundException() {
        super();
    }

    public BankCardByUserIdNotFoundException(String message) {
        super(message);
    }

    public BankCardByUserIdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
