package com.github.hronosf.controllers;

import com.github.hronosf.dto.*;
import com.github.hronosf.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/client")
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/register")
    @Operation(summary = "Register new client in application with sms-verification code sending")
    public ClientProfileResponseDTO registerNewClient(ClientRegistrationRequestDTO request) {
        return clientService.registerNewClient(request, true);
    }

    @PostMapping("/send_code")
    @Operation(summary = "Send verification code via sms by phone number")
    public void sendVerificationCode(@RequestBody PhoneNumberRequestDTO request) {
        clientService.sendVerificationCode(request.getPhoneNumber());
    }

    @PostMapping("/activate")
    @Operation(summary = "Activate client account")
    public void activateClient(@RequestBody ClientProfileActivationRequestDTO request) {
        clientService.activateClientProfile(request);
    }

    @GetMapping
    @PreAuthorize("@userProvider.activatedClient()")
    @Operation(summary = "Get logged client profile")
    public ClientProfileResponseDTO getClientProfile() {
        return clientService.getAuthenticatedClient();
    }

    @PatchMapping
    @PreAuthorize("@userProvider.activatedClient()")
    @Operation(summary = "Change client personal data")
    public ClientProfileResponseDTO changeClientData(ClientProfileUpdateRequestDTO request) {
        return clientService.updateClientPersonalData(request);
    }
}
