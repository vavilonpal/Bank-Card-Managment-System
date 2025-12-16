package com.example.bankcards.dto.user.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class UserResponse {

    private UUID id;

    private String email;

    private String fullName;

    private String phoneNumber;

    private Boolean isActive;

    private LocalDateTime createdAt;
}

