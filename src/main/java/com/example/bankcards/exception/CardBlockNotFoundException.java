package com.example.bankcards.exception;

public class CardBlockNotFoundException extends RuntimeException {
    public CardBlockNotFoundException() {
        super();
    }

    public CardBlockNotFoundException(String message) {
        super(message);
    }

    public CardBlockNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
