package com.github.hronosf.services;

import com.github.hronosf.domain.Client;
import com.github.hronosf.dto.request.ClientProfileActivationDTO;
import com.github.hronosf.dto.request.ClientRegistrationRequestDTO;

public interface UserService {

    <T extends ClientRegistrationRequestDTO> void registerNewUser(T request);

    void sendVerificationCode(String phoneNumber);

    Client getByPhoneNumber(String phoneNumber);

    void activateClientProfile(ClientProfileActivationDTO request);
}
