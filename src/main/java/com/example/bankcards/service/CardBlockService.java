package com.example.bankcards.service;


import com.example.bankcards.dto.bankcard.request.BlockBankCardRequest;
import com.example.bankcards.entity.BankCard;
import com.example.bankcards.entity.CardBlock;
import com.example.bankcards.exception.entity.cardblock.CardBlockNotFoundException;
import com.example.bankcards.exception.entity.cardblock.UnsupportedCardBlockOperation;
import com.example.bankcards.repository.BankCardRepository;
import com.example.bankcards.repository.CardBlockRepository;
import com.example.bankcards.security.AuthenticationService;
import com.example.bankcards.util.enums.bankcard.BankCardBlockStatus;
import com.example.bankcards.util.enums.bankcard.BankCardStatus;
import com.example.bankcards.util.mapper.bankcard.BankCardBlockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.EnumSet;

@Service
@RequiredArgsConstructor
@Transactional
public class CardBlockService {
    private final BankCardBlockMapper bankCardBlockMapper;

    private final BankCardRepository bankCardRepository;
    private final CardBlockRepository cardBlockRepository;

    private final BankCardsService bankCardsService;
    private final AuthenticationService authenticationService;

    public CardBlock createBlockRequest(BlockBankCardRequest blockBankCardRequest) {
        CardBlock cardBlock = bankCardBlockMapper.toEntity(blockBankCardRequest);
        if (blockBankCardRequest.getRequestedBy() == null) {
            cardBlock.setRequestedBy(authenticationService.getCurrentUserId());
        }
        cardBlock.setCard(bankCardsService.getCardById(blockBankCardRequest.getCardId()));

        return cardBlockRepository.save(cardBlock);
    }

    public CardBlock confirmBlockRequest(Long blockRequestId, Boolean temporary) {
        CardBlock cardBlock = getById(blockRequestId);
        BankCard bankCard = cardBlock.getCard();

        if (EnumSet.of(BankCardBlockStatus.BLOCKED, BankCardBlockStatus.CANCELLED).contains(cardBlock.getStatus())) {
            throw new UnsupportedCardBlockOperation("This request was already processed.");
        }

        cardBlock.setBlockedAt(LocalDateTime.now());
        cardBlock.setStatus(BankCardBlockStatus.BLOCKED);
        cardBlock.setTemporary(temporary);

        bankCard.setCardStatus(BankCardStatus.BLOCKED);

        bankCardRepository.save(bankCard);
        return cardBlockRepository.save(cardBlock);
    }

    public CardBlock getById(Long id) {
        return cardBlockRepository.findById(id)
                .orElseThrow(() -> new CardBlockNotFoundException("Card Block request not found, id:" + id));
    }
}
