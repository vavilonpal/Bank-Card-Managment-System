package com.example.bankcards.exception.handler.user;


import com.example.bankcards.exception.ApiErrorResponse;
import com.example.bankcards.exception.entity.user.UserException;
import com.example.bankcards.exception.entity.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ApiErrorResponse> handleUserException(UserException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }
}
