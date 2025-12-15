package com.example.bankcards.exception.entity.cardblock;

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
