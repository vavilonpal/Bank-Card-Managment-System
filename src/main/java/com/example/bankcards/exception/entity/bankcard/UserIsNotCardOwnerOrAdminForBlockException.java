package com.example.bankcards.exception.entity.bankcard;

public class UserIsNotCardOwnerOrAdminForBlockException extends RuntimeException {
    public UserIsNotCardOwnerOrAdminForBlockException(String message) {
        super(message);
    }

    public UserIsNotCardOwnerOrAdminForBlockException() {
        super();
    }
}
