package com.example.bankcards.dto.bankcard.response;


import com.example.bankcards.entity.CardBlock;
import com.example.bankcards.entity.User;
import com.example.bankcards.util.enums.bankcard.BankCardStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OverallBankCardResponse {
    private UUID id;
    private UUID userId;
    private BigDecimal cardBalance;
    private String cardNumber;
    private String cardHolder;
    private String cvvHash;
    private Integer expirationMonth;
    private Integer expirationYear;
    private BankCardStatus cardStatus;
    private LocalDateTime createdAt;
    private List<BlockCardResponse> blockCardRequests;
}
