package com.example.bankcards.exception.entity.bankcard;

public class SameCardTransferException extends RuntimeException {
    public SameCardTransferException() {
        super();
    }

    public SameCardTransferException(String message) {
        super(message);
    }

    public SameCardTransferException(String message, Throwable cause) {
        super(message, cause);
    }
}
