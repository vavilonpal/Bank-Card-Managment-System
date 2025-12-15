package com.example.bankcards.service;


import com.example.bankcards.dto.bankcard.request.BankCardAdditionRequest;
import com.example.bankcards.entity.BankCard;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.entity.bankcard.BankCardNotFoundException;
import com.example.bankcards.repository.BankCardRepository;
import com.example.bankcards.util.enums.bankcard.BankCardStatus;
import com.example.bankcards.util.mapper.bankcard.BankCardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankCardsService {
    private final BankCardMapper bankCardMapper;

    private final BankCardRepository bankCardRepository;
    private final UserService userService;

    public BankCard createCard(BankCardAdditionRequest cardAdditionRequest) {
        User user = userService.getById(cardAdditionRequest.getUserId());

        BankCard bankCard = bankCardMapper.toEntity(cardAdditionRequest);
        bankCard.setUser(user);
        return bankCardRepository.save(bankCard);
    }

    public BankCard getCardById(UUID cardId) {
        return bankCardRepository.findById(cardId)
                .orElseThrow(() -> new BankCardNotFoundException("Card not found with id: " + cardId));
    }


    public BankCard activateCardById(UUID cardId){
        BankCard card = getCardById(cardId);
        card.setCardStatus(BankCardStatus.ACTIVE);
        return bankCardRepository.save(card);
    }

    public List<BankCard> getCardsByUser(UUID userId) {
        return bankCardRepository.findAllByUserId(userId);
    }

    public List<BankCard> getAllCards() {
        return bankCardRepository.findAll();
    }

    public Page<BankCard> getAllCards(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        if (search == null) search = "";

        return bankCardRepository.findAllAndSearch(search, pageable);
    }


    public BankCard updateCard(UUID cardId, BankCard updatedCard) {
        BankCard existingCard = getCardById(cardId);

        existingCard.setCardHolder(updatedCard.getCardHolder());
        existingCard.setExpirationMonth(updatedCard.getExpirationMonth());
        existingCard.setExpirationYear(updatedCard.getExpirationYear());
        existingCard.setCardStatus(updatedCard.getCardStatus());
        existingCard.setCardBalance(updatedCard.getCardBalance());

        return bankCardRepository.save(existingCard);
    }


    public void deleteCard(UUID cardId) {
        BankCard card = getCardById(cardId);
        bankCardRepository.delete(card);
    }

}

