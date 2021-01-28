package com.github.hronosf.services;

import com.github.hronosf.dto.ClientProfileActivationDTO;
import com.github.hronosf.dto.ClientProfileDTO;
import com.github.hronosf.dto.ClientRegistrationRequestDTO;
import com.github.hronosf.dto.PreTrialAppealDTO;
import com.github.hronosf.model.Client;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    @Transactional
    <T extends ClientRegistrationRequestDTO> ClientProfileDTO registerNewUser(T request, boolean isActivate);

    void sendVerificationCode(String phoneNumber);

    ClientProfileDTO getAuthenticatedClient();

    Client getByPhoneNumber(String phoneNumber);

    void activateClientProfile(ClientProfileActivationDTO request);

    void createUserIfNotExistAndAddUserBankData(PreTrialAppealDTO request);
}
