package com.example.bankcards.exception.handler.bankcard;


import com.example.bankcards.dto.error.response.ApiErrorResponse;
import com.example.bankcards.exception.entity.bankcard.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BankCardExceptionHandler {

    @ExceptionHandler(BankCardException.class)
    public ResponseEntity<ApiErrorResponse> handleBankCardException(BankCardException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }
}
