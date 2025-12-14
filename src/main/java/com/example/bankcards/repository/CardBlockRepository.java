package com.example.bankcards.repository;

import com.example.bankcards.entity.CardBlock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardBlockRepository extends JpaRepository<CardBlock, Long> {
}
