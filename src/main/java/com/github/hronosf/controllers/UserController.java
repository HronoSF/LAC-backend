package com.github.hronosf.controllers;

import com.github.hronosf.dto.*;
import com.github.hronosf.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register new user in application with sms-verification code sending")
    public ClientProfileResponseDTO registerNewUser(ClientRegistrationRequestDTO request) {
        return userService.registerNewUser(request, true);
    }

    @PostMapping("/send_code")
    @Operation(summary = "Send verification code via sms by phone number")
    public void sendVerificationCode(@RequestBody PhoneNumberRequestDTO request) {
        userService.sendVerificationCode(request.getPhoneNumber());
    }

    @PostMapping("/activate")
    @Operation(summary = "Activate client account")
    public void activateClient(@RequestBody ClientProfileActivationRequestDTO request) {
        userService.activateClientProfile(request);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get logged user profile")
    public ClientProfileResponseDTO getUserProfile() {
        return userService.getAuthenticatedClient();
    }

    @PatchMapping("/update")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Change client personal data")
    public ClientProfileResponseDTO changeClientData(ClientProfileUpdateRequestDTO request) {
        return userService.updateClientPersonalData(request);
    }
}
