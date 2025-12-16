package com.example.bankcards.service;


import com.example.bankcards.dto.bankcard.response.BalanceResponse;
import com.example.bankcards.dto.bankcard.response.TransferResponse;
import com.example.bankcards.entity.BankCard;
import com.example.bankcards.exception.BankCardByUserIdNotFoundException;
import com.example.bankcards.exception.entity.bankcard.LowBankCardBalanceException;
import com.example.bankcards.repository.BankCardRepository;
import com.example.bankcards.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;


/*
    *Пользователь:
  - Просматривает свои карты (поиск + пагинация)
  - Запрашивает блокировку карты
  - Делает переводы между своими картами
  - Смотрит баланс

    *
    *
    *
*/

@Service
@RequiredArgsConstructor
public class UserBankCardOperationsService {
    private final BankCardRepository bankCardRepository;
    private final AuthenticationService authenticationService;


    public Page<BankCard> getOwnCards(String search, int page, int size) {
        if (search == null) search = "";

        UUID currentUserId = authenticationService.getCurrentUserId();

        Pageable pageable = PageRequest.of(page, size);


        return bankCardRepository.findBAllyUserIdAndSearch(currentUserId, search, pageable);
    }

    @Transactional
    public TransferResponse transferBetweenOwnCards(UUID fromCardId, UUID toCardId, BigDecimal amount) {

        UUID currentUserId = authenticationService.getCurrentUserId();

        BankCard fromCard = getBankCardByUserIdAndCardId(currentUserId, fromCardId);
        BankCard toCard = getBankCardByUserIdAndCardId(currentUserId, toCardId);

        BigDecimal fromCardCardBalance = fromCard.getCardBalance();
        if (fromCardCardBalance.compareTo(amount) < 0) {
            throw new LowBankCardBalanceException("Low card balance on card by Id: " + fromCardId);
        }

        fromCard.setCardBalance(fromCard.getCardBalance().subtract(amount));
        toCard.setCardBalance(toCard.getCardBalance().add(amount));

        bankCardRepository.save(fromCard);
        bankCardRepository.save(toCard);

        return TransferResponse.builder()
                .transferAmount(amount)
                .fromCardBalance(fromCard.getCardBalance())
                .toCardBalance(toCard.getCardBalance())
                .build();
    }


    public BankCard getBankCardByUserIdAndCardId(UUID userId, UUID cardId) {
        return bankCardRepository.findBankCardByUserIdAndId(userId, cardId)
                .orElseThrow(() -> new BankCardByUserIdNotFoundException(String.format("Bank Card not found by this user id: %s and cardId: %s", userId, cardId)));
    }


    public BalanceResponse getOwnCardBalance(UUID cardId) {
        UUID currentUserId = authenticationService.getCurrentUserId();
        BankCard bankCard = getBankCardByUserIdAndCardId(currentUserId, cardId);
        return new BalanceResponse(bankCard.getCardNumber(),bankCard.getCardBalance());
    }


}