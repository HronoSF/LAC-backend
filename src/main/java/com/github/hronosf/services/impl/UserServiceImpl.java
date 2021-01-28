package com.github.hronosf.services.impl;

import com.github.hronosf.authentication.providers.UserProvider;
import com.github.hronosf.dto.ClientProfileActivationDTO;
import com.github.hronosf.dto.ClientProfileDTO;
import com.github.hronosf.dto.ClientRegistrationRequestDTO;
import com.github.hronosf.dto.PreTrialAppealDTO;
import com.github.hronosf.dto.enums.Roles;
import com.github.hronosf.exceptions.ClientAlreadyActivatedException;
import com.github.hronosf.exceptions.ClientAlreadyRegisteredException;
import com.github.hronosf.exceptions.ClientNotFoundException;
import com.github.hronosf.mappers.ClientMapper;
import com.github.hronosf.model.Client;
import com.github.hronosf.model.ClientBankData;
import com.github.hronosf.model.Role;
import com.github.hronosf.repository.ClientRepository;
import com.github.hronosf.repository.RoleRepository;
import com.github.hronosf.services.CognitoService;
import com.github.hronosf.services.UserBankDataService;
import com.github.hronosf.services.UserService;
import com.github.hronosf.services.VerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private Role clientRole;

    private final ClientMapper mapper;

    private final UserProvider userProvider;

    private final CognitoService cognitoService;
    private final UserBankDataService userBankDataService;
    private final VerificationService verificationService;

    private final ClientRepository clientRepository;
    private final RoleRepository roleRepository;

    @PostConstruct
    public void cacheClientRole() {
        clientRole = roleRepository.getByName(Roles.CLIENT.getName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public <T extends ClientRegistrationRequestDTO> ClientProfileDTO registerNewUser(T request, boolean isActivate) {
        Client client;

        Optional<Client> clientOpt = clientRepository.findByPhoneNumber(request.getPhoneNumber());

        if (clientOpt.isPresent()) {
            client = clientOpt.get();

            if (client.isActivated()) {
                log.debug("User with phone number {} already exist", request.getPhoneNumber());
                throw new ClientAlreadyRegisteredException(request.getPhoneNumber());
            }

        } else {
            // Save new client to app-db:
            client = Client.builder()
                    .id(UUID.randomUUID().toString())
                    .firstName(request.getFirstName())
                    .middleName(request.getMiddleName())
                    .lastName(request.getLastName())
                    .address(request.getAddress())
                    .roles(Collections.singletonList(clientRole))
                    .phoneNumber(request.getPhoneNumber())
                    .build();

            clientRepository.save(client);

            // create account in cognito:
            cognitoService.createUser(
                    request.getPhoneNumber(),
                    StringUtils.isNotBlank(request.getPassword()) ?
                            request.getPassword() : "tmpPassword" + UUID.randomUUID(),
                    StringUtils.substringAfter(Roles.CLIENT.getName(), Roles.getPrefix())
            );
        }

        if (isActivate) {
            // send verificationCode:
            sendVerificationCode(request.getPhoneNumber());
        }

        return mapper.toDto(client);
    }

    @Override
    @Transactional
    public ClientProfileDTO getAuthenticatedClient() {
        String id = userProvider.getAuthenticatedUser().getId();

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
                .findByPhoneNumber(phoneNumber)
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

        verificationService.verify(client, request.getVerificationCode());

        client.setActivated(true);
        clientRepository.save(client);
    }

    @Override
    public void createUserIfNotExistAndAddUserBankData(PreTrialAppealDTO request) {
        Client client;
        try {
            client = getByPhoneNumber(request.getPhoneNumber());
        } catch (ClientNotFoundException exception) {
            registerNewUser(request, false);
            client = getByPhoneNumber(request.getPhoneNumber());
        }

        userBankDataService.saveClientBankData(request, client);
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
