package com.example.bankcards.exception.entity.cardblock;

public class UnsupportedCardBlockOperation extends RuntimeException {
    public UnsupportedCardBlockOperation(String message) {
        super(message);
    }
}
