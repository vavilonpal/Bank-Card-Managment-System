package com.example.bankcards.dto.user.response;


import com.example.bankcards.entity.Role;
import com.example.bankcards.util.enums.RoleType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OverallUserResponse { //Response for Admin
    private UUID id;
    private String email;
    private String passwordHash;
    private RoleType role;
    private String fullName;
    private String phoneNumber;
    private Boolean isActive = true;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
