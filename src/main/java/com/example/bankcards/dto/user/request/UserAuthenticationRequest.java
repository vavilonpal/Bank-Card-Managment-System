package com.example.bankcards.dto.user.request;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthenticationRequest {
    private String email;
    private String password;
}
