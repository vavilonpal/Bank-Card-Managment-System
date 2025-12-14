package com.example.bankcards.entity;

import com.example.bankcards.util.enums.bankcard.BankCardBlockReason;
import com.example.bankcards.util.enums.bankcard.BankCardBlockStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "card_blocks")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private BankCard card;

    @Column(name = "requested_by", nullable = false)
    private UUID requestedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "reason", nullable = false, length = 50)
    private BankCardBlockReason reason;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "temporary", nullable = false)
    private Boolean temporary = false;

    @Column(name = "notify_client", nullable = false)
    private Boolean notifyClient = true;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "blocked_at")
    private LocalDateTime blockedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private BankCardBlockStatus status = BankCardBlockStatus.PENDING;
}

