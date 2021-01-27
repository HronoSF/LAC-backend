package com.github.hronosf.services;

import com.github.hronosf.model.Client;
import com.github.hronosf.dto.ClientProfileActivationDTO;
import com.github.hronosf.dto.ClientRegistrationRequestDTO;
import com.github.hronosf.dto.ClientProfileDTO;

public interface UserService {

    <T extends ClientRegistrationRequestDTO> ClientProfileDTO registerNewUser(T request);

    void sendVerificationCode(String phoneNumber);

    ClientProfileDTO getClientById(String id);

    Client getByPhoneNumber(String phoneNumber);

    void activateClientProfile(ClientProfileActivationDTO request);
}
