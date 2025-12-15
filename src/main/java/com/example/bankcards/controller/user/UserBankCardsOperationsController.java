package com.example.bankcards.controller.user;


import com.example.bankcards.entity.BankCard;
import com.example.bankcards.service.BankCardsService;
import com.example.bankcards.service.UserBankCardOperationsService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user/bank-cards")
@RequiredArgsConstructor
public class UserBankCardsOperationsController {
    private final UserBankCardOperationsService bankCardOperationsService;

    @GetMapping
    public Page<BankCard> getOwnCards(
            @RequestParam(defaultValue = "0") @Min(0) Integer page,
            @RequestParam(defaultValue = "5") @Min(1) @Max(100) Integer size,
            @RequestParam(required = false, defaultValue = "") String searchQuery
    ) {


        return bankCardOperationsService.getOwnCards(searchQuery, page, size);
    }

    @PostMapping("/{cardId}/block")
    public void blockBankCardRequest(
            @PathVariable("cardId") UUID bankCardId
    ) {
        bankCardOperationsService.blockBankCardRequest(bankCardId, userId);
    }

    @PostMapping("/transfer")
    public void transferBetweenOwnCards(
            @RequestParam("fromCardId") UUID fromCardId,
            @RequestParam("toCardId") UUID toCardId,
            @RequestParam("amount") BigDecimal amount
    ) {
        bankCardOperationsService.transferBetweenOwnCards(fromCardId, toCardId, amount);
    }

    @GetMapping("/{cardId}/balance")
    public BigDecimal getOwnCardBalance(@PathVariable("cardId") UUID cardId) {
        return bankCardOperationsService.getOwnCardBalance(cardId);
    }

}
