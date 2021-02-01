package com.github.hronosf.controllers;

import com.github.hronosf.dto.ClientBankDataResponseDTO;
import com.github.hronosf.services.UserBankDataService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/bank_data")
public class UserBankDataController {

    private final UserBankDataService userBankDataService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get logged user all bank data")
    public List<ClientBankDataResponseDTO> getClientBankData() {
        return userBankDataService.getClientBankData();
    }

    @GetMapping("/latest")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get logged user latest one bank data")
    public ClientBankDataResponseDTO getLatestClientBankData() {
        return userBankDataService.getClientLatestBankData();
    }
}
