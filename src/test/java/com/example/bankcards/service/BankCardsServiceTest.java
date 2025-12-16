package com.example.bankcards.service;

import com.example.bankcards.dto.bankcard.request.BankCardAdditionRequest;
import com.example.bankcards.entity.BankCard;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.entity.bankcard.BankCardNotFoundException;
import com.example.bankcards.repository.BankCardRepository;
import com.example.bankcards.util.enums.bankcard.BankCardStatus;
import com.example.bankcards.util.mapper.bankcard.BankCardMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankCardsServiceTest {

    @Mock
    private BankCardMapper bankCardMapper;

    @Mock
    private BankCardRepository bankCardRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private BankCardsService bankCardsService;

    @Test
    void shouldCreateCard() {
        UUID userId = UUID.randomUUID();
        BankCardAdditionRequest request = new BankCardAdditionRequest();
        request.setUserId(userId);
        request.setCardHolder("Ivan Ivanov");
        request.setCardBalance(BigDecimal.valueOf(1000));
        request.setCardNumber("1234567890123456");
        request.setCvv("123");
        request.setExpirationMonth(12);
        request.setExpirationYear(2030);

        User user = new User();
        user.setId(userId);

        BankCard cardEntity = new BankCard();
        cardEntity.setCardHolder(request.getCardHolder());

        BankCard savedCard = new BankCard();
        savedCard.setCardHolder(request.getCardHolder());

        when(userService.getById(userId)).thenReturn(user);
        when(bankCardMapper.toEntity(request)).thenReturn(cardEntity);
        when(bankCardRepository.save(cardEntity)).thenReturn(savedCard);

        BankCard result = bankCardsService.createCard(request);

        assertNotNull(result);
        assertEquals("Ivan Ivanov", result.getCardHolder());
        verify(bankCardRepository).save(cardEntity);
    }

    @Test
    void shouldReturnCardById() {
        UUID cardId = UUID.randomUUID();
        BankCard card = new BankCard();
        card.setId(cardId);

        when(bankCardRepository.findById(cardId)).thenReturn(Optional.of(card));

        BankCard result = bankCardsService.getCardById(cardId);

        assertNotNull(result);
        assertEquals(cardId, result.getId());
    }

    @Test
    void shouldThrowWhenCardNotFound() {
        UUID cardId = UUID.randomUUID();
        when(bankCardRepository.findById(cardId)).thenReturn(Optional.empty());

        assertThrows(BankCardNotFoundException.class,
                () -> bankCardsService.getCardById(cardId));
    }

    @Test
    void shouldActivateCard() {
        UUID cardId = UUID.randomUUID();
        BankCard card = new BankCard();
        card.setId(cardId);
        card.setCardStatus(BankCardStatus.INACTIVE);

        when(bankCardRepository.findById(cardId)).thenReturn(Optional.of(card));
        when(bankCardRepository.save(any(BankCard.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BankCard result = bankCardsService.activateCardById(cardId);

        assertEquals(BankCardStatus.ACTIVE, result.getCardStatus());
        verify(bankCardRepository).save(card);
    }

    @Test
    void shouldCheckIfCardExistsByNumber() {
        when(bankCardRepository.existsBankCardByCardNumber("1234567890123456")).thenReturn(true);

        boolean exists = bankCardsService.isExistsByCardNumber("1234567890123456");
        assertTrue(exists);
    }

    @Test
    void shouldCheckIfUserIsCardOwner() {
        UUID userId = UUID.randomUUID();
        when(bankCardRepository.existsBankCardByUserId(userId)).thenReturn(true);

        boolean isOwner = bankCardsService.isCardOwner(userId);
        assertTrue(isOwner);
    }






}

