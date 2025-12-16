package com.example.bankcards.service;

import com.example.bankcards.dto.user.request.UserPersistRequest;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.entity.user.UserNotFoundException;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.mapper.user.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldRegisterUserSuccessfully() {
        UserPersistRequest request = new UserPersistRequest();
        User userEntity = new User();
        User savedUser = new User();

        when(userMapper.toEntity(request)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(savedUser);

        User result = userService.register(request);

        assertNotNull(result);
        assertEquals(savedUser, result);
        verify(userRepository).save(userEntity);
    }

    @Test
    void shouldReturnUserById() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getById(userId);

        assertEquals(userId, result.getId());
    }

    @Test
    void shouldThrowWhenUserByIdNotFound() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getById(userId));
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        UUID userId = UUID.randomUUID();
        User existing = new User();
        existing.setId(userId);

        User updated = new User();
        updated.setEmail("new@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existing));
        when(userRepository.save(existing)).thenReturn(existing);

        User result = userService.update(userId, updated);

        assertEquals("new@example.com", result.getEmail());
        verify(userRepository).save(existing);
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        UUID userId = UUID.randomUUID();

        when(userRepository.existsById(userId)).thenReturn(true);

        userService.delete(userId);

        verify(userRepository).deleteById(userId);
    }

    @Test
    void shouldThrowWhenDeleteNonExistentUser() {
        UUID userId = UUID.randomUUID();

        when(userRepository.existsById(userId)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.delete(userId));
    }

    @Test
    void shouldReturnUserByEmail() {
        String email = "test@example.com";
        User user = new User();
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));

        User result = userService.getByEmail(email);

        assertEquals(user, result);
    }

    @Test
    void shouldThrowWhenUserByEmailNotFound() {
        String email = "test@example.com";
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getByEmail(email));
    }

    @Test
    void shouldReturnTrueIfEmailExists() {
        String email = "test@example.com";
        when(userRepository.existsUserByEmail(email)).thenReturn(true);

        assertTrue(userService.existsByEmail(email));
    }

    @Test
    void shouldReturnFalseIfPhoneNumberNotExists() {
        String phone = "+1234567890";
        when(userRepository.existsUserByPhoneNumber(phone)).thenReturn(false);

        assertFalse(userService.existsByPhoneNumber(phone));
    }

    @Test
    void shouldReturnPagedUsers() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> page = new PageImpl<>(List.of(new User()));

        when(userRepository.findAllAndSearch("search", pageable)).thenReturn(page);

        Page<User> result = userService.searchUsers("search", pageable);

        assertEquals(1, result.getContent().size());
    }


}
