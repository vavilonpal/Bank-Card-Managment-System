package com.example.bankcards.controller.admin.user;

import com.example.bankcards.dto.MessageResponse;
import com.example.bankcards.dto.user.request.UserPersistRequest;
import com.example.bankcards.dto.user.response.OverallUserResponse;
import com.example.bankcards.dto.user.response.UserResponse;
import com.example.bankcards.entity.User;
import com.example.bankcards.service.UserService;
import com.example.bankcards.util.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class AdminManageUsersController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Validated UserPersistRequest userRequest) {
        User createdUser = userService.register(userRequest);
        UserResponse response = userMapper.toResponse(createdUser);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) {
        User user = userService.getById(id);
        UserResponse response = userMapper.toResponse(user);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID id, @RequestBody @Validated UserPersistRequest request) {
        User updatedUser = userService.update(id, userMapper.toEntity(request));
        UserResponse response = userMapper.toResponse(updatedUser);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity
                .ok(new MessageResponse("Successful User delete!"));
    }

    @GetMapping
    public ResponseEntity<Page<OverallUserResponse>> searchUsers(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fullName") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<OverallUserResponse> usersResponsePage = userService.searchUsers(search, pageable)
                .map(userMapper::toOverallResponse);

        return ResponseEntity.ok(usersResponsePage);
    }
}
