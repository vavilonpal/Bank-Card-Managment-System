package com.example.bankcards.exception.handler.role;


import com.example.bankcards.exception.ApiErrorResponse;
import com.example.bankcards.exception.entity.role.RoleException;
import com.example.bankcards.exception.entity.role.RoleNotFoundException;
import com.example.bankcards.exception.entity.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RoleExceptionHandler {

    @ExceptionHandler(RoleException.class)
    public ResponseEntity<ApiErrorResponse> handleRoleException(RoleException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }
}
