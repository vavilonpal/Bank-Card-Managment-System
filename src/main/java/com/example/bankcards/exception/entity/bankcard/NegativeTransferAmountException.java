package com.example.bankcards.exception.entity.bankcard;

public class NegativeTransferAmountException extends RuntimeException {
    public NegativeTransferAmountException() {
        super();
    }

    public NegativeTransferAmountException(String message) {
        super(message);
    }

    public NegativeTransferAmountException(String message, Throwable cause) {
        super(message, cause);
    }
}
