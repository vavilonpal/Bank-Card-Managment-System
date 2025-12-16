package com.example.bankcards.service;


import com.example.bankcards.dto.user.request.RegisterUserRequest;
import com.example.bankcards.exception.entity.user.UserNotFoundException;
import com.example.bankcards.util.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import com.example.bankcards.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.bankcards.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User register(RegisterUserRequest request) {
        User user = userMapper.toEntity(request);
        return userRepository.save(user);
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User getById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + id));
    }

    public User update(UUID id, User updatedUser) {
        User existing = getById(id);

        existing.setEmail(updatedUser.getEmail());
        existing.setPasswordHash(updatedUser.getPasswordHash());
        existing.setFullName(updatedUser.getFullName());
        existing.setPhoneNumber(updatedUser.getPhoneNumber());
        existing.setRole(updatedUser.getRole());
        existing.setIsActive(updatedUser.getIsActive());

        return userRepository.save(existing);
    }

    public void delete(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found: " + id);
        }
        userRepository.deleteById(id);
    }

    public User getByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(()->  new UserNotFoundException("User by email not found: " + email));
    }

    public Page<User> searchUsers(String search, Pageable pageable) {
        return userRepository.findAllAndSearch(search, pageable);
    }

    public boolean isExistsByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    public boolean isExistsByPhoneNumber(String phoneNumber) {
        return isExistsByPhoneNumber(phoneNumber);
    }
}
