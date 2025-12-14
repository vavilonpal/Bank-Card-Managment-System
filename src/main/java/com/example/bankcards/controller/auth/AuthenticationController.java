package com.example.bankcards.controller.auth;


import com.example.bankcards.dto.user.request.RegisterUserRequest;
import com.example.bankcards.entity.User;
import com.example.bankcards.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {


    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Validated RegisterUserRequest userRequest){
        User registeredUser = userService.register(userRequest);
        return ResponseEntity.ok(registeredUser.toString());
    }

    @GetMapping
    public ResponseEntity<String> getSome(){
        return ResponseEntity.ok("hello");
    }
}
