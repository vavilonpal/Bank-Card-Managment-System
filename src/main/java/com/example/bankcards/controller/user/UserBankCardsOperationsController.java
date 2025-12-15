package com.example.bankcards.controller.user;


import com.example.bankcards.dto.bankcard.request.BlockBankCardRequest;
import com.example.bankcards.dto.bankcard.request.TransferRequest;
import com.example.bankcards.dto.bankcard.response.BankCardResponse;
import com.example.bankcards.dto.bankcard.response.BlockCardResponse;
import com.example.bankcards.entity.BankCard;
import com.example.bankcards.entity.CardBlock;
import com.example.bankcards.service.BankCardsService;
import com.example.bankcards.service.CardBlockService;
import com.example.bankcards.service.UserBankCardOperationsService;
import com.example.bankcards.util.mapper.bankcard.BankCardBlockMapper;
import com.example.bankcards.util.mapper.bankcard.BankCardMapper;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user/bank-cards")
@RequiredArgsConstructor
public class UserBankCardsOperationsController {
    private final UserBankCardOperationsService bankCardOperationsService;
    private final CardBlockService cardBlockService;
    private final BankCardBlockMapper bankCardBlockMapper;
    private final BankCardMapper bankCardMapper;

    @GetMapping
    public Page<BankCard> getOwnCards(
            @RequestParam(defaultValue = "0") @Min(0) Integer page,
            @RequestParam(defaultValue = "5") @Min(1) @Max(100) Integer size,
            @RequestParam(required = false, defaultValue = "") String searchQuery
    ) {


        return bankCardOperationsService.getOwnCards(searchQuery, page, size);
    }

    @PostMapping("/block")
    public ResponseEntity<BlockCardResponse> blockBankCardRequest(@Validated @RequestBody BlockBankCardRequest blockBankCardRequest) {
        CardBlock blockRequest = cardBlockService.createBlockRequest(blockBankCardRequest);
        BlockCardResponse response = bankCardBlockMapper.toResponse(blockRequest);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/transfer")
    public ResponseEntity<BankCardResponse> transferBetweenOwnCards(@Validated @RequestBody TransferRequest request) {
        BankCard fromBankCard = bankCardOperationsService.transferBetweenOwnCards(
                request.getFromCardId(),
                request.getToCardId(),
                request.getAmount());

        BankCardResponse response = bankCardMapper.toResponse(fromBankCard);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{cardId}/balance")
    public BigDecimal getOwnCardBalance(@PathVariable UUID cardId) {
        return bankCardOperationsService.getOwnCardBalance(cardId);
    }

}
