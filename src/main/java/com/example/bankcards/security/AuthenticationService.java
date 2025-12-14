package com.example.bankcards.security;

import com.example.bankcards.dto.user.request.UserAuthenticationRequest;
import com.example.bankcards.dto.user.response.UserAuthenticationResponse;
import com.example.bankcards.service.auth.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserAuthenticationResponse authenticate(final UserAuthenticationRequest request) {
        final var  authToken = UsernamePasswordAuthenticationToken
                .unauthenticated(request.getEmail(), request.getPassword());

        final var authentication = authenticationManager.authenticate(authToken);

        // Генерируем токен
        final var token = jwtService.generateToken(request.getEmail());

        return new UserAuthenticationResponse(token);
    }
}
