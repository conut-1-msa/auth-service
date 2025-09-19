package io.github.conut.msa.auth.auth.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.conut.msa.auth.auth.dto.LoginRequest;
import io.github.conut.msa.auth.auth.dto.RegisterRequest;
import io.github.conut.msa.auth.auth.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(Map.of("accessToken", authService.login(loginRequest)));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
