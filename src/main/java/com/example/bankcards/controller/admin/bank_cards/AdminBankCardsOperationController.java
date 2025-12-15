package com.example.bankcards.controller.admin.bank_cards;


import com.example.bankcards.dto.bankcard.request.BankCardAdditionRequest;
import com.example.bankcards.dto.bankcard.response.BankCardResponse;
import com.example.bankcards.entity.BankCard;
import com.example.bankcards.service.BankCardsService;
import com.example.bankcards.util.mapper.bankcard.BankCardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/bank-cards")
@RequiredArgsConstructor
public class AdminBankCardsOperationController {
    private final BankCardsService bankCardsService;
    private final BankCardMapper bankCardMapper;

    @PostMapping
    public ResponseEntity<BankCardResponse> createCard(
            @RequestBody @Validated BankCardAdditionRequest request
    ) {
        BankCard card = bankCardsService.createCard(request);

        return ResponseEntity.ok(bankCardMapper.toResponse(card));
    }

    @PatchMapping("/{cardId}/activate")
    public ResponseEntity<Void> activateCard(
            @PathVariable UUID cardId
    ) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{cardId}/block")
    public ResponseEntity<Void> blockCard(
            @PathVariable UUID cardId
    ) {
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{cardId}")
    public ResponseEntity<Void> deleteCard(
            @PathVariable UUID cardId
    ) {
        return ResponseEntity.noContent().build();
    }

    /**
     * Получить все карты (поиск + пагинация)
     */
    @GetMapping
    public ResponseEntity<Page<BankCardResponse>> getAllCards(
            @RequestParam(required = false) String search,
            Pageable pageable
    ) {
        return ResponseEntity.ok().build();
    }
}
