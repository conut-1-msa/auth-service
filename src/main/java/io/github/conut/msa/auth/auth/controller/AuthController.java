package io.github.conut.msa.auth.auth.controller;

import java.time.Duration;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.conut.msa.auth.auth.dto.AuthTokens;
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
        AuthTokens authTokens = authService.login(loginRequest);
        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", authTokens.getRefreshToken())
            .httpOnly(true)
            .secure(true)
            .path("/")
            .sameSite("Strict")
            .maxAge(Duration.ofDays(14))
            .build();
        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
            .body(Map.of("accessToken", authTokens.getAccessToken()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", "")
            .path("/")
            .maxAge(0)
            .build();
        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
            .build();
    }
}
