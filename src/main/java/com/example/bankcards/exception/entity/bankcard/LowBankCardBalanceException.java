package com.example.bankcards.exception.entity.bankcard;

public class LowBankCardBalanceException extends RuntimeException {

    public LowBankCardBalanceException() {
        super();
    }

    public LowBankCardBalanceException(String message) {
        super(message);
    }

    public LowBankCardBalanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
