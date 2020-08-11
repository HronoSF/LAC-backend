package com.github.hronosf.controllers;

import com.github.hronosf.model.payload.request.LoginRequestDTO;
import com.github.hronosf.model.payload.request.RefreshTokensDTO;
import com.github.hronosf.model.payload.response.AuthTokensResponseDTO;
import com.github.hronosf.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthTokensResponseDTO> authorization(@RequestBody LoginRequestDTO loginRequest) {
        AuthTokensResponseDTO tokens = authService.login(loginRequest);
        return ResponseEntity.ok().body(tokens);
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AuthTokensResponseDTO> authorization(@RequestBody RefreshTokensDTO refreshRequest) {
        AuthTokensResponseDTO tokens = authService.refresh(refreshRequest);
        return ResponseEntity.ok().body(tokens);
    }
}