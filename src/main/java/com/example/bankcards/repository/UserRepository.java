package com.example.bankcards.repository;

import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByEmail(String email);

    boolean existsUserByEmail(String email);

    boolean existsByEmailAndIdNot(String email, UUID id);
    boolean existsUserByPhoneNumber(String phoneNumber);

    @Query("SELECT u FROM User u " +
            "WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(u.phoneNumber) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<User> findAllAndSearch(String search, Pageable pageable);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, UUID currentUserId);
}
