package com.github.hronosf.services.impl;

import com.github.hronosf.domain.Client;
import com.github.hronosf.domain.ClientBankData;
import com.github.hronosf.dto.request.ClientProfileActivationDTO;
import com.github.hronosf.dto.request.ClientRegistrationRequestDTO;
import com.github.hronosf.dto.request.PreTrialAppealDTO;
import com.github.hronosf.dto.response.ClientProfileDTO;
import com.github.hronosf.exceptions.ClientAlreadyActivatedException;
import com.github.hronosf.exceptions.ClientAlreadyRegisteredException;
import com.github.hronosf.exceptions.ClientNotFoundException;
import com.github.hronosf.mappers.ClientMapper;
import com.github.hronosf.repository.ClientRepository;
import com.github.hronosf.services.UserBankDataService;
import com.github.hronosf.services.UserService;
import com.github.hronosf.services.VerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ClientMapper mapper;

    private final UserBankDataService userBankDataService;
    private final VerificationService verificationService;

    private final ClientRepository clientRepository;

    @Override
    @Transactional
    public <T extends ClientRegistrationRequestDTO> ClientProfileDTO registerNewUser(T request) {
        if (clientRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            log.debug("User with phone number {} already exist", request.getPhoneNumber());
            throw new ClientAlreadyRegisteredException(request.getPhoneNumber());
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
            ClientBankData clientBankData = userBankDataService.saveClientBankData(preTrialRequest, newClient);
            newClient.setBankData(Collections.singletonList(clientBankData));
        }

        clientRepository.save(newClient);

        return mapper.toDto(newClient);
    }

    @Override
    @Transactional
    public ClientProfileDTO getClientById(String id) {
        Optional<Client> clientOptional = clientRepository.findById(id);

        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            client.getBankData().sort(Comparator.comparing(ClientBankData::getCreatedAt));
            return mapper.toDto(client);
        }

        throw new ClientNotFoundException("id", id);
    }

    @Override
    public Client getByPhoneNumber(String phoneNumber) {
        return clientRepository
                .getByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ClientNotFoundException("номером телефона", phoneNumber));
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
