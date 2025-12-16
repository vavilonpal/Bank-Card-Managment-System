package com.example.bankcards.repository;

import com.example.bankcards.entity.BankCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankCardRepository extends JpaRepository<BankCard, UUID> {

    boolean existsBankCardByCardNumber(String cardNumber);

    Optional<BankCard> findBankCardByUserIdAndId(UUID userId, UUID id);

    // Поиск по пользователю и частичному совпадению номера или имени держателя
    @Query("SELECT c FROM BankCard c " +
            "WHERE c.user.id = :userId " +
            "AND (LOWER(c.cardNumber) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(c.cardHolder) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<BankCard> findByUserIdAndSearch(@Param("userId") UUID userId,
                                         @Param("search") String search,
                                         Pageable pageable);

    @Query("""
            SELECT c FROM BankCard c
            WHERE (
              :search IS NULL
              OR :search = ''
              OR LOWER(c.cardNumber) LIKE LOWER(CONCAT('%', :search, '%'))
              OR LOWER(c.cardHolder) LIKE LOWER(CONCAT('%', :search, '%'))
            )
            """)
    Page<BankCard> findAllAndSearch(@Param("search") String search,
                                    Pageable pageable);

    @Query("SELECT c.cardBalance FROM BankCard c "+
    "WHERE c.id = :card_id ")
    Optional<BigDecimal> getBankCardBalanceById(@Param("card_id") UUID cardId);

    boolean existsBankCardByUserId(UUID userId);
}
