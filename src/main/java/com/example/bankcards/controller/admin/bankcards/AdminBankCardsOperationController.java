package com.example.bankcards.controller.admin.bankcards;

import com.example.bankcards.dto.bankcard.request.BankCardAdditionRequest;
import com.example.bankcards.dto.bankcard.request.BlockBankCardRequest;
import com.example.bankcards.dto.bankcard.response.BankCardResponse;
import com.example.bankcards.entity.BankCard;
import com.example.bankcards.entity.CardBlock;
import com.example.bankcards.service.BankCardsService;
import com.example.bankcards.service.CardBlockService;
import com.example.bankcards.util.mapper.bankcard.BankCardMapper;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/bankcards")
@RequiredArgsConstructor
public class AdminBankCardsOperationController {
    private final BankCardsService bankCardsService;
    private final BankCardMapper bankCardMapper;
    private final CardBlockService cardBlockService;

    @PostMapping
    public ResponseEntity<BankCardResponse> createCard(@RequestBody @Validated BankCardAdditionRequest request) {
        BankCard card = bankCardsService.createCard(request);
        return ResponseEntity.ok(bankCardMapper.toResponse(card));
    }

    @PatchMapping("/{cardId}/activate")
    public ResponseEntity<BankCardResponse> activateCard(@PathVariable UUID cardId) {
        BankCard bankCard = bankCardsService.activateCardById(cardId);

        return ResponseEntity.ok(bankCardMapper.toResponse(bankCard));
    }

    @PatchMapping("/{cardId}/block")
    public ResponseEntity<String> blockCard(@RequestBody @Validated BlockBankCardRequest blockBankCardRequest) {
        CardBlock blockRequest = cardBlockService.createBlockRequest(blockBankCardRequest);
        cardBlockService.confirmBlockRequest(blockRequest.getId(), blockRequest.getTemporary());
        return ResponseEntity.ok("Successful card block");
    }


    @DeleteMapping("/{cardId}")
    public ResponseEntity<String> deleteCard(@PathVariable UUID cardId) {
        bankCardsService.deleteCard(cardId);

        return ResponseEntity.ok("Successful card delete!");
    }


    @GetMapping
    public Page<BankCardResponse> getAllCards(@RequestParam(required = false) String search,
                                              @RequestParam(defaultValue = "0") @Min(0) int page,
                                              @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        Page<BankCard> allCards = bankCardsService.getAllCards(search, page, size);

        return allCards.map(bankCardMapper::toAdminResponse);
    }
}
