package com.example.bankcards.exception.entity.bankcard;

public class BankCardNotFoundException extends RuntimeException {
    public BankCardNotFoundException() {
        super();
    }

    public BankCardNotFoundException(String message) {
        super(message);
    }

    public BankCardNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
