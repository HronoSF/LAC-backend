package com.github.hronosf.services;

import com.github.hronosf.dto.*;
import com.github.hronosf.model.Client;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    @Transactional
    <T extends ClientRegistrationRequestDTO> ClientProfileResponseDTO registerNewUser(T request, boolean isActivate);

    void sendVerificationCode(String phoneNumber);

    ClientProfileResponseDTO updateClientPersonalData(ClientProfileUpdateRequestDTO request);

    ClientProfileResponseDTO getAuthenticatedClient();

    Client getByPhoneNumber(String phoneNumber);

    void activateClientProfile(ClientProfileActivationRequestDTO request);

    void createUserIfNotExistAndAddUserBankData(PreTrialAppealRequestDTO request);
}
