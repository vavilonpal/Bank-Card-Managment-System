package com.example.bankcards.entity;

import com.example.bankcards.util.enums.bankcard.BankCardStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "bank_cards")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankCard {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "card_balance", nullable = false, precision = 19, scale = 2)
    private BigDecimal cardBalance;

    @Column(name = "card_number", nullable = false, unique = true, length = 19)
    private String cardNumber;

    @Column(name = "card_holder", nullable = false, length = 150)
    private String cardHolder;

    @Column(name = "cvv_hash", nullable = false, length = 255)
    private String cvvHash;

    @Column(name = "expiration_month", nullable = false)
    private Integer expirationMonth;

    @Column(name = "expiration_year", nullable = false)
    private Integer expirationYear;

    @Column(name = "card_status", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private BankCardStatus cardStatus;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "card")
    private List<CardBlock> blockCardRequests;
}


