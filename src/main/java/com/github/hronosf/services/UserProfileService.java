package com.github.hronosf.services;

import com.github.hronosf.domain.Client;
import com.github.hronosf.dto.request.ClientProfileActivationDTO;
import com.github.hronosf.dto.request.RequestWithUserDataDTO;

public interface UserProfileService {

    <T extends RequestWithUserDataDTO> void registerNewUser(T request);

    void sendVerificationCode(String phoneNumber);

    Client getByPhoneNumber(String phoneNumber);

    void activateClientProfile(ClientProfileActivationDTO request);
}
