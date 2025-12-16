package com.example.bankcards.dto.error.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiErrorResponse {
    private String message;
    private int status;
}
