package com.example.bankcards.exception.handler.cardblock;


import com.example.bankcards.exception.ApiErrorResponse;
import com.example.bankcards.exception.entity.bankcard.BankCardException;
import com.example.bankcards.exception.entity.cardblock.CardBlockException;
import com.example.bankcards.exception.entity.role.RoleNotFoundException;
import com.example.bankcards.exception.entity.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CardBlockExceptionHandler {

    @ExceptionHandler(CardBlockException.class)
    public ResponseEntity<ApiErrorResponse> handleBankCardBlockException(CardBlockException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }
}
