package com.example.bankcards.controller.auth;

import com.example.bankcards.dto.user.request.UserAuthenticationRequest;
import com.example.bankcards.dto.user.request.UserPersistRequest;
import com.example.bankcards.dto.user.response.UserAuthenticationResponse;
import com.example.bankcards.entity.User;
import com.example.bankcards.security.AuthenticationService;
import com.example.bankcards.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationService authenticationService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void shouldAuthenticateUser() throws Exception {
        UserAuthenticationRequest request = new UserAuthenticationRequest();
        request.setEmail("ivan.ivanov@example.com");
        request.setPassword("Password1");

        UserAuthenticationResponse response = new UserAuthenticationResponse();
        response.setToken("mocked-jwt-token");

        when(authenticationService.authenticate(any(UserAuthenticationRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mocked-jwt-token"));

        verify(authenticationService).authenticate(any(UserAuthenticationRequest.class));
    }

    @Test
    void shouldRegisterUser() throws Exception {
        UserPersistRequest request = UserPersistRequest.builder()
                .fullName("Ivan Ivanov")
                .email("ivan.ivanov@example.com")
                .phoneNumber("+37369123456")
                .password("Password1")
                .passwordConfirm("Password1")
                .build();

        when(userService.register(any(UserPersistRequest.class))).thenReturn(new User());

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Successful register!"));

        verify(userService).register(any(UserPersistRequest.class));
    }
}
