package com.example.bankcards.controller.user;


import com.example.bankcards.dto.bankcard.request.BlockBankCardRequest;
import com.example.bankcards.dto.bankcard.request.TransferRequest;
import com.example.bankcards.dto.bankcard.response.BalanceResponse;
import com.example.bankcards.dto.bankcard.response.BankCardResponse;
import com.example.bankcards.dto.bankcard.response.BlockCardResponse;
import com.example.bankcards.dto.bankcard.response.TransferResponse;
import com.example.bankcards.entity.BankCard;
import com.example.bankcards.entity.CardBlock;
import com.example.bankcards.service.CardBlockService;
import com.example.bankcards.service.UserBankCardOperationsService;
import com.example.bankcards.util.mapper.bankcard.BankCardBlockMapper;
import com.example.bankcards.util.mapper.bankcard.BankCardMapper;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user/bankcards")
@RequiredArgsConstructor
public class UserBankCardsOperationsController {

    private final CardBlockService cardBlockService;
    private final UserBankCardOperationsService bankCardOperationsService;

    private final BankCardMapper bankCardMapper;
    private final BankCardBlockMapper bankCardBlockMapper;

    @GetMapping
    public ResponseEntity<Page<BankCardResponse>> getOwnCards(
            @RequestParam(defaultValue = "0") @Min(0) Integer page,
            @RequestParam(defaultValue = "5") @Min(1) @Max(100) Integer size,
            @RequestParam(required = false, defaultValue = "") String searchQuery
    ) {
        Page<BankCardResponse> ownCardsResponses = bankCardOperationsService.getOwnCards(searchQuery, page, size)
                .map(bankCardMapper::toResponse);

        return ResponseEntity.ok(ownCardsResponses);
    }

    @PostMapping("/block")
    public ResponseEntity<BlockCardResponse> blockBankCardRequest(@Validated @RequestBody BlockBankCardRequest blockBankCardRequest) {
        CardBlock blockRequest = cardBlockService.createBlockRequest(blockBankCardRequest);
        BlockCardResponse response = bankCardBlockMapper.toResponse(blockRequest);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/transfer")
    public ResponseEntity<TransferResponse> transferBetweenOwnCards(@Validated @RequestBody TransferRequest request) {
        TransferResponse transferResponse = bankCardOperationsService.transferBetweenOwnCards(
                request.getFromCardId(),
                request.getToCardId(),
                request.getAmount());

        return ResponseEntity.ok(transferResponse);
    }

    @GetMapping("/{cardId}/balance")
    public ResponseEntity<BalanceResponse> getOwnCardBalance(@PathVariable UUID cardId) {
        BalanceResponse ownCardBalance = bankCardOperationsService.getOwnCardBalance(cardId);

        return ResponseEntity.ok(ownCardBalance);
    }

}
