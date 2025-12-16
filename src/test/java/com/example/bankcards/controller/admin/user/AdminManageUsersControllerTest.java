package com.example.bankcards.controller.admin.user;

import com.example.bankcards.dto.user.request.UserPersistRequest;
import com.example.bankcards.dto.user.response.OverallUserResponse;
import com.example.bankcards.dto.user.response.UserResponse;
import com.example.bankcards.entity.User;
import com.example.bankcards.security.AuthenticationService;
import com.example.bankcards.service.UserService;
import com.example.bankcards.util.mapper.user.UserMapper;
import com.example.bankcards.util.validation.validator.PasswordMatchValidator;
import com.example.bankcards.util.validation.validator.UserByEmailOccupyValidator;
import com.example.bankcards.util.validation.validator.UserByPhoneNumberOccupyValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AdminManageUsersController.class)
@AutoConfigureMockMvc(addFilters = false) // без security на старте
class AdminManageUsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private UserByPhoneNumberOccupyValidator phoneNumberNotOccupyValidator;
    @MockBean
    private UserByEmailOccupyValidator emailNotOccupyValidator;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private PasswordMatchValidator passwordMatchValidator;


    @BeforeEach
    void setup() {
        when(passwordMatchValidator.isValid(any(), any())).thenReturn(true);
        when(phoneNumberNotOccupyValidator.isValid(any(), any())).thenReturn(true);
        when(emailNotOccupyValidator.isValid(any(), any())).thenReturn(true);
    }
    @Test
    void shouldCreateUser() throws Exception {
        UserPersistRequest request = UserPersistRequest.builder()
                .fullName("Ivan Ivanov")
                .phoneNumber("+37369123456")
                .email("ivan.ivanov@example.com")
                .password("Password1")
                .passwordConfirm("Password1")
                .build();
        User user = new User();
        UserResponse response = new UserResponse();

        when(userService.register(any())).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(response);

        mockMvc.perform(post("/api/v1/admin/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(userService).register(any());
        verify(userMapper).toResponse(user);
    }

    @Test
    void shouldGetUserById() throws Exception {
        UUID userId = UUID.randomUUID();

        User user = new User();
        UserResponse response = new UserResponse();

        when(userService.getById(userId)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(response);

        mockMvc.perform(get("/api/v1/admin/users/{id}", userId))
                .andExpect(status().isOk());

        verify(userService).getById(userId);
    }

    @Test
    void shouldUpdateUser() throws Exception {
        UUID userId = UUID.randomUUID();

        UserPersistRequest request = UserPersistRequest.builder()
                .fullName("Ivan Ivanov")
                .phoneNumber("+37369123456")
                .email("ivan.ivanov@example.com")
                .password("Password1")
                .passwordConfirm("Password1")
                .build();
        User userEntity = new User();
        User updatedUser = new User();
        UserResponse response = new UserResponse();

        when(userMapper.toEntity(any())).thenReturn(userEntity);
        when(userService.update(eq(userId), eq(userEntity))).thenReturn(updatedUser);
        when(userMapper.toResponse(updatedUser)).thenReturn(response);

        mockMvc.perform(put("/api/v1/admin/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(userService).update(eq(userId), any());
    }

    @Test
    void shouldDeleteUser() throws Exception {
        UUID userId = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/admin/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Successful User delete!"));

        verify(userService).delete(userId);
    }


    @Test
    void shouldSearchUsers() throws Exception {
        User user = new User();
        OverallUserResponse response = new OverallUserResponse();

        Page<User> page = new PageImpl<>(List.of(user));

        when(userService.searchUsers(anyString(), any(Pageable.class)))
                .thenReturn(page);
        when(userMapper.toOverallResponse(user))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/admin/users")
                        .param("search", "john")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "fullName"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1));
    }
}
