package com.example.bankcards.exception;

public class UnsupportedCardBlockOperation extends RuntimeException {
    public UnsupportedCardBlockOperation(String message) {
        super(message);
    }
}
