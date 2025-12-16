package com.example.bankcards.dto.user.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private UUID id;

    private String email;

    private String fullName;

    private String phoneNumber;

    private Boolean isActive;

    private LocalDateTime createdAt;
}

