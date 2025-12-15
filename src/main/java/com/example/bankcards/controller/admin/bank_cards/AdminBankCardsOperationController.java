package com.example.bankcards.controller.admin.bank_cards;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/bank-cards")
public class BankCardsOperationController {

    @PostMapping
    public ResponseEntity<Void> createCard(
            @RequestBody @Valid BankCardAdditionRequest request
    ) {
        return ResponseEntity.ok().build();
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
    public ResponseEntity<Page<BankCard>> getAllCards(
            @RequestParam(required = false) String search,
            Pageable pageable
    ) {
        return ResponseEntity.ok().build();
    }
}
