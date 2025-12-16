package com.example.bankcards.service;

import com.example.bankcards.dto.bankcard.request.BlockBankCardRequest;
import com.example.bankcards.entity.BankCard;
import com.example.bankcards.entity.CardBlock;
import com.example.bankcards.exception.entity.bankcard.UserIsNotCardOwnerOrAdminForBlockException;
import com.example.bankcards.exception.entity.cardblock.CardBlockNotFoundException;
import com.example.bankcards.exception.entity.cardblock.UnsupportedCardBlockOperation;
import com.example.bankcards.repository.BankCardRepository;
import com.example.bankcards.repository.CardBlockRepository;
import com.example.bankcards.security.AuthenticationService;
import com.example.bankcards.util.enums.bankcard.BankCardBlockStatus;
import com.example.bankcards.util.enums.bankcard.BankCardStatus;
import com.example.bankcards.util.mapper.bankcard.BankCardBlockMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardBlockServiceTest {

    @Mock
    private BankCardBlockMapper bankCardBlockMapper;

    @Mock
    private BankCardRepository bankCardRepository;

    @Mock
    private CardBlockRepository cardBlockRepository;

    @Mock
    private BankCardsService bankCardsService;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private CardBlockService cardBlockService;


    @Test
    void shouldCreateBlockRequestSuccessfully() {
        UUID userId = UUID.randomUUID();
        UUID cardId = UUID.randomUUID();

        BlockBankCardRequest request = new BlockBankCardRequest();
        request.setCardId(cardId);
        request.setTemporary(true);

        CardBlock cardBlockEntity = new CardBlock();
        BankCard bankCard = new BankCard();

        when(authenticationService.getCurrentUserId()).thenReturn(userId);
        when(authenticationService.isCurrentUserAdmin()).thenReturn(true);
        when(bankCardsService.isCardOwner(userId)).thenReturn(true);
        when(bankCardsService.getCardById(cardId)).thenReturn(bankCard);
        when(bankCardBlockMapper.toEntity(request)).thenReturn(cardBlockEntity);
        when(cardBlockRepository.save(cardBlockEntity)).thenReturn(cardBlockEntity);

        CardBlock result = cardBlockService.createBlockRequest(request);

        assertNotNull(result);
        assertEquals(userId, result.getRequestedBy());
        assertEquals(bankCard, result.getCard());
        verify(cardBlockRepository).save(cardBlockEntity);
    }

    @Test
    void shouldConfirmBlockRequestSuccessfully() {
        Long blockRequestId = 1L;
        CardBlock cardBlock = new CardBlock();
        BankCard bankCard = new BankCard();

        cardBlock.setCard(bankCard);
        cardBlock.setStatus(BankCardBlockStatus.PENDING);

        when(cardBlockRepository.findById(blockRequestId)).thenReturn(Optional.of(cardBlock));
        when(cardBlockRepository.save(any(CardBlock.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(bankCardRepository.save(any(BankCard.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CardBlock result = cardBlockService.confirmBlockRequest(blockRequestId, true);

        assertEquals(BankCardBlockStatus.BLOCKED, result.getStatus());
        assertTrue(result.getTemporary());
        assertEquals(BankCardStatus.BLOCKED, bankCard.getCardStatus());
        assertNotNull(result.getBlockedAt());
    }

    @Test
    void shouldThrowWhenConfirmAlreadyProcessedBlock() {
        CardBlock cardBlock = new CardBlock();
        cardBlock.setStatus(BankCardBlockStatus.BLOCKED);

        when(cardBlockRepository.findById(1L)).thenReturn(Optional.of(cardBlock));

        assertThrows(UnsupportedCardBlockOperation.class,
                () -> cardBlockService.confirmBlockRequest(1L, true));
    }


    @Test
    void shouldReturnCardBlockById() {
        CardBlock cardBlock = new CardBlock();
        cardBlock.setId(1L);

        when(cardBlockRepository.findById(1L)).thenReturn(Optional.of(cardBlock));

        CardBlock result = cardBlockService.getById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void shouldThrowWhenCardBlockNotFound() {
        when(cardBlockRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CardBlockNotFoundException.class,
                () -> cardBlockService.getById(1L));
    }








}
