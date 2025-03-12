package com.example.remind.auth.controller;

import com.example.remind.auth.dto.request.AuthRequestDto;
import com.example.remind.auth.dto.response.AuthResponseDto;
import com.example.remind.auth.dto.request.AuthLoginRequestDto;
import com.example.remind.auth.service.AuthService;
import com.example.remind.common.config.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/signup")
    public void signup(@RequestBody AuthRequestDto dto) {
        authService.signup(dto);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthLoginRequestDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
}
