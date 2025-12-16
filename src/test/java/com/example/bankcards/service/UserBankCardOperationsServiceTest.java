package com.example.bankcards.service;

import com.example.bankcards.dto.bankcard.response.BalanceResponse;
import com.example.bankcards.dto.bankcard.response.BankCardResponse;
import com.example.bankcards.dto.bankcard.response.TransferResponse;
import com.example.bankcards.entity.BankCard;
import com.example.bankcards.exception.entity.bankcard.BankCardByUserIdNotFoundException;
import com.example.bankcards.exception.entity.bankcard.LowBankCardBalanceException;
import com.example.bankcards.repository.BankCardRepository;
import com.example.bankcards.security.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserBankCardOperationsServiceTest {

    @Mock
    private BankCardRepository bankCardRepository;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private UserBankCardOperationsService service;


    @Test
    void shouldReturnOwnCards() {
        UUID userId = UUID.randomUUID();
        when(authenticationService.getCurrentUserId()).thenReturn(userId);

        BankCard card = new BankCard();
        card.setId(UUID.randomUUID());

        Page<BankCard> page = new PageImpl<>(List.of(card));
        when(bankCardRepository.findBAllyUserIdAndSearch(eq(userId), anyString(), any(Pageable.class)))
                .thenReturn(page);

        Page<BankCard> result = service.getOwnCards("search", 0, 5);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(card.getId(), result.getContent().get(0).getId());
    }

    @Test
    void shouldTransferBetweenOwnCards() {
        UUID userId = UUID.randomUUID();
        UUID fromCardId = UUID.randomUUID();
        UUID toCardId = UUID.randomUUID();

        BankCard fromCard = new BankCard();
        fromCard.setId(fromCardId);
        fromCard.setCardBalance(BigDecimal.valueOf(1000));

        BankCard toCard = new BankCard();
        toCard.setId(toCardId);
        toCard.setCardBalance(BigDecimal.valueOf(500));

        when(authenticationService.getCurrentUserId()).thenReturn(userId);
        when(bankCardRepository.findBankCardByUserIdAndId(userId, fromCardId)).thenReturn(Optional.of(fromCard));
        when(bankCardRepository.findBankCardByUserIdAndId(userId, toCardId)).thenReturn(Optional.of(toCard));
        when(bankCardRepository.save(any(BankCard.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BigDecimal amount = BigDecimal.valueOf(300);

        TransferResponse response = service.transferBetweenOwnCards(fromCardId, toCardId, amount);

        assertEquals(BigDecimal.valueOf(700), response.getFromCardBalance());
        assertEquals(BigDecimal.valueOf(800), response.getToCardBalance());
        assertEquals(amount, response.getTransferAmount());
    }

    @Test
    void shouldThrowWhenLowBalance() {
        UUID userId = UUID.randomUUID();
        UUID fromCardId = UUID.randomUUID();
        UUID toCardId = UUID.randomUUID();

        BankCard fromCard = new BankCard();
        fromCard.setId(fromCardId);
        fromCard.setCardBalance(BigDecimal.valueOf(100));

        BankCard toCard = new BankCard();
        toCard.setId(toCardId);
        toCard.setCardBalance(BigDecimal.valueOf(500));

        when(authenticationService.getCurrentUserId()).thenReturn(userId);
        when(bankCardRepository.findBankCardByUserIdAndId(userId, fromCardId)).thenReturn(Optional.of(fromCard));
        when(bankCardRepository.findBankCardByUserIdAndId(userId, toCardId)).thenReturn(Optional.of(toCard));

        BigDecimal amount = BigDecimal.valueOf(200);

        assertThrows(LowBankCardBalanceException.class,
                () -> service.transferBetweenOwnCards(fromCardId, toCardId, amount));
    }

    @Test
    void shouldReturnBankCardByUserIdAndCardId() {
        UUID userId = UUID.randomUUID();
        UUID cardId = UUID.randomUUID();
        BankCard card = new BankCard();
        card.setId(cardId);

        when(bankCardRepository.findBankCardByUserIdAndId(userId, cardId)).thenReturn(Optional.of(card));

        BankCard result = service.getBankCardByUserIdAndCardId(userId, cardId);

        assertEquals(cardId, result.getId());
    }

    @Test
    void shouldThrowIfBankCardNotFound() {
        UUID userId = UUID.randomUUID();
        UUID cardId = UUID.randomUUID();

        when(bankCardRepository.findBankCardByUserIdAndId(userId, cardId)).thenReturn(Optional.empty());

        assertThrows(BankCardByUserIdNotFoundException.class,
                () -> service.getBankCardByUserIdAndCardId(userId, cardId));
    }

    @Test
    void shouldReturnOwnCardBalance() {
        UUID userId = UUID.randomUUID();
        UUID cardId = UUID.randomUUID();
        BankCard card = new BankCard();
        card.setId(cardId);
        card.setCardNumber("1234567890123456");
        card.setCardBalance(BigDecimal.valueOf(1000));

        when(authenticationService.getCurrentUserId()).thenReturn(userId);
        when(bankCardRepository.findBankCardByUserIdAndId(userId, cardId)).thenReturn(Optional.of(card));

        BalanceResponse balance = service.getOwnCardBalance(cardId);

        assertEquals(BankCardResponse.maskCardNumber("1234567890123456"), balance.getCardNumber());
        assertEquals(BigDecimal.valueOf(1000), balance.getBalance());
    }
}
