package com.github.hronosf.services.impl;

import com.github.hronosf.domain.Client;
import com.github.hronosf.domain.ClientAccount;
import com.github.hronosf.dto.request.ClientProfileActivationDTO;
import com.github.hronosf.dto.request.PreTrialAppealDTO;
import com.github.hronosf.dto.request.RequestWithUserDataDTO;
import com.github.hronosf.exceptions.ClientAlreadyActivatedException;
import com.github.hronosf.exceptions.ClientNotFoundException;
import com.github.hronosf.repository.ClientAccountRepository;
import com.github.hronosf.repository.ClientRepository;
import com.github.hronosf.services.UserProfileService;
import com.github.hronosf.services.VerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final VerificationService verificationService;

    private final ClientRepository clientRepository;
    private final ClientAccountRepository clientAccountRepository;

    @Override
    @Transactional
    public <T extends RequestWithUserDataDTO> void registerNewUser(T request) {
        if (clientRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            log.debug("User with email {} already exist", request.getPhoneNumber());
            return;
        }

        Client newClient = Client.builder()
                .id(UUID.randomUUID().toString())
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .build();

        clientRepository.save(newClient);

        if (request instanceof PreTrialAppealDTO) {
            PreTrialAppealDTO preTrialRequest = (PreTrialAppealDTO) request;

            ClientAccount newClientAccount = ClientAccount.builder()
                    .id(UUID.randomUUID().toString())
                    .bik(preTrialRequest.getConsumerBankBik())
                    .bankName(preTrialRequest.getConsumerBankName())
                    .bankCorrAcc(preTrialRequest.getConsumerBankCorrAcc())
                    .info(preTrialRequest.getConsumerInfo())
                    .accountNumber(preTrialRequest.getCustomerAccountNumber())
                    .client(newClient)
                    .build();

            // save user's bank data:
            clientAccountRepository.save(newClientAccount);

            // update user entity with bank data:
            newClient.setBankData(Collections.singletonList(newClientAccount));
        }

        clientRepository.save(newClient);
    }

    @Override
    public Client getByPhoneNumber(String phoneNumber) {
        return clientRepository
                .getByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ClientNotFoundException(phoneNumber));
    }

    @Override
    public void sendVerificationCode(String phoneNumber) {
        Client client = getClientIfNotActivated(phoneNumber);

        verificationService.sendVerificationCode(client);
    }

    @Override
    @Transactional
    public void activateClientProfile(ClientProfileActivationDTO request) {
        Client client = getClientIfNotActivated(request.getPhoneNumber());
        verificationService.markVerificationCodeAsUsed(client);

        client.setActivated(true);
        clientRepository.save(client);
    }

    private Client getClientIfNotActivated(String phoneNumber) {
        Client client = getByPhoneNumber(phoneNumber);

        if (!client.isActivated()) {
            return client;
        } else {
            throw new ClientAlreadyActivatedException(client.getPhoneNumber());
        }
    }
}
