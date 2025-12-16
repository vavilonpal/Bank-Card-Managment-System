package com.example.bankcards.exception.entity.bankcard;

public class UserIsNotCardOwnerOrAdminForBlockException extends BankCardException {
    public UserIsNotCardOwnerOrAdminForBlockException(String message) {
        super(message);
    }

}
