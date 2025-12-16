package com.example.bankcards.controller.auth;


import com.example.bankcards.dto.MessageResponse;
import com.example.bankcards.dto.user.request.UserPersistRequest;
import com.example.bankcards.dto.user.request.UserAuthenticationRequest;
import com.example.bankcards.dto.user.response.UserAuthenticationResponse;
import com.example.bankcards.entity.User;
import com.example.bankcards.security.AuthenticationService;
import com.example.bankcards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {


    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<UserAuthenticationResponse> authenticate(@Validated @RequestBody UserAuthenticationRequest request
    ) {
        UserAuthenticationResponse tokenResponse = authenticationService.authenticate(request);

        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> registerUser(@Validated @RequestBody UserPersistRequest userRequest) {
        userService.register(userRequest);
        return ResponseEntity.ok(new MessageResponse("Successful register!"));
    }


}
