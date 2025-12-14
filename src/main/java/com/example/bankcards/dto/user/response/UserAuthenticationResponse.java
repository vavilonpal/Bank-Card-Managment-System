package com.example.bankcards.dto.user.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserAuthenticationResponse {
    private String token;
}
