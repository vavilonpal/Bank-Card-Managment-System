package com.example.bankcards.service;


import com.example.bankcards.entity.BankCard;
import com.example.bankcards.repository.BankCardRepository;
import com.example.bankcards.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
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
    private final AuthenticationService authenticationService;


    public Page<BankCard> getOwnCards(String search, int page, int size) {
        if (search == null) search = "";

        UUID currentUserId = authenticationService.getCurrentUserId();

        Pageable pageable = PageRequest.of(page, size, Sort.by("balance").descending());


        return bankCardRepository.findByUserIdAndSearch(currentUserId, search, pageable);
    }


    public void blockBankCard() {

    }

    public void transferBetweenOwnCards(UUID fromCardId, UUID toCardId, BigDecimal amount) {

    }

    public BigDecimal getOwnCardBalance(UUID cardId) {
        return null;
    }


}