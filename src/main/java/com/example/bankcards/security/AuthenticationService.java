package com.example.bankcards.security;

import com.example.bankcards.dto.user.request.UserAuthenticationRequest;
import com.example.bankcards.dto.user.response.UserAuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserAuthenticationResponse authenticate(final UserAuthenticationRequest request) {
        final var  authToken = UsernamePasswordAuthenticationToken
                .unauthenticated(request.getEmail(), request.getPassword());

        final var authentication = authenticationManager.authenticate(authToken);

        final var token = jwtService.generateToken(request.getEmail());

        return new UserAuthenticationResponse(token);
    }

    public UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
            throw new IllegalStateException("Пользователь не авторизован или токен некорректен");
        }

        String userIdStr = jwt.getClaim("user_id");
        if (userIdStr == null) {
            throw new IllegalStateException("JWT не содержит claim 'user_id'");
        }

        return UUID.fromString(userIdStr);
    }
}
