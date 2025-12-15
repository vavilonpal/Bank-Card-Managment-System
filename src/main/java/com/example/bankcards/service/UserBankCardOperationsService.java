package com.example.bankcards.service;


import com.example.bankcards.entity.BankCard;
import com.example.bankcards.repository.BankCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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


    public Page<BankCard> getOwnCards(Pageable pageable, String search, UUID userId) {
        if (search == null) search = "";

        return bankCardRepository.findByUserIdAndSearch(userId, search, pageable);
    }


    public void blockBankCardRequest(UUID bankCardId, UUID userId) {

    }

    public void transferBetweenOwnCards(UUID fromCardId, UUID toCardId, BigDecimal amount) {

    }

    public BigDecimal getOwnCardBalance(UUID cardId) {
        return null;
    }


}