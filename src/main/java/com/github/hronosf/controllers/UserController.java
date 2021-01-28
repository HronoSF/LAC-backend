package com.github.hronosf.controllers;

import com.github.hronosf.dto.*;
import com.github.hronosf.services.UserBankDataService;
import com.github.hronosf.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final UserBankDataService userBankDataService;

    @PostMapping("/register")
    @Operation(summary = "Register new user in application with sms-verification code sending")
    public ClientProfileDTO registerNewUser(ClientRegistrationRequestDTO request) {
        return userService.registerNewUser(request, true);
    }

    @PostMapping("/send_code")
    @Operation(summary = "Send verification code via sms by phone number")
    public void sendVerificatinCode(@RequestBody PhoneNumberRequestDTO request) {
        userService.sendVerificationCode(request.getPhoneNumber());
    }

    @PostMapping("/activate")
    @Operation(summary = "Activate client account")
    public void activateClient(@RequestBody ClientProfileActivationDTO request) {
        userService.activateClientProfile(request);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get logged user profile")
    public ClientProfileDTO getUserProfile() {
        return userService.getAuthenticatedClient();
    }

    @GetMapping("/bank_data")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get logged user all bank data")
    public List<ClientBankDataResponseDTO> getClientBankData() {
        return userBankDataService.getClientBankData();
    }

    @GetMapping("/bank_data/latest")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get logged user latest one bank data")
    public ClientBankDataResponseDTO getLatestClientBankData() {
        return userBankDataService.getClientLatestBankData();
    }
}
