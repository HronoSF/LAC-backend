package com.github.hronosf.services;

import com.github.hronosf.domain.Client;
import com.github.hronosf.dto.request.ClientProfileActivationDTO;
import com.github.hronosf.dto.request.ClientRegistrationRequestDTO;
import com.github.hronosf.dto.response.ClientProfileDTO;

public interface UserService {

    <T extends ClientRegistrationRequestDTO> ClientProfileDTO registerNewUser(T request);

    void sendVerificationCode(String phoneNumber);

    ClientProfileDTO getClientById(String id);

    Client getByPhoneNumber(String phoneNumber);

    void activateClientProfile(ClientProfileActivationDTO request);
}
