package com.github.hronosf.controllers;

import com.github.hronosf.dto.request.ClientProfileActivationDTO;
import com.github.hronosf.dto.request.ClientRegistrationRequestDTO;
import com.github.hronosf.dto.response.ClientBankDataResponseDTO;
import com.github.hronosf.services.UserBankDataService;
import com.github.hronosf.services.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final UserBankDataService userBankDataService;

    @PostMapping("/register")
    public void registerNewUser(ClientRegistrationRequestDTO request) {
        userService.registerNewUser(request);
    }

    @PostMapping("/send_code")
    @ApiOperation(value = "Send verification code via sms by phone number")
    public void sendVerificatinCode(@RequestBody String phone) {
        userService.sendVerificationCode(phone);
    }

    @PostMapping("/activate")
    @ApiOperation(value = "Activate client account")
    public void activateClient(@RequestBody ClientProfileActivationDTO request) {
        userService.activateClientProfile(request);
    }

    @GetMapping("/bank_data/{id}")
    public List<ClientBankDataResponseDTO> getClientBankData(@PathVariable("id") String id) {
        return userBankDataService.getClientBankData(id);
    }
}
