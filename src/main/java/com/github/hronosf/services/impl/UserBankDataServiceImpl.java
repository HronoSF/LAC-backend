package com.github.hronosf.services.impl;

import com.github.hronosf.authentication.providers.UserProvider;
import com.github.hronosf.dto.ClientBankDataResponseDTO;
import com.github.hronosf.dto.PreTrialAppealDTO;
import com.github.hronosf.mappers.ClientAccountMapper;
import com.github.hronosf.model.Client;
import com.github.hronosf.model.ClientBankData;
import com.github.hronosf.repository.ClientBankDataRepository;
import com.github.hronosf.services.UserBankDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserBankDataServiceImpl implements UserBankDataService {

    private final UserProvider userProvider;

    private final ClientAccountMapper mapper;
    private final ClientBankDataRepository clientBankDataRepository;

    @Override
    public ClientBankData saveClientBankData(PreTrialAppealDTO preTrialRequest, Client newClient) {
        ClientBankData newClientBankData = ClientBankData.builder()
                .id(UUID.randomUUID().toString())
                .bik(preTrialRequest.getConsumerBankBik())
                .bankName(preTrialRequest.getConsumerBankName())
                .bankCorrAcc(preTrialRequest.getConsumerBankCorrAcc())
                .accountNumber(preTrialRequest.getCustomerAccountNumber())
                .client(newClient)
                .build();

        // save user's bank data:
        return clientBankDataRepository.save(newClientBankData);
    }

    @Override
    public List<ClientBankDataResponseDTO> getClientBankData() {
        List<ClientBankData> clientBankData = clientBankDataRepository.getByClientId(
                userProvider.getAuthenticatedUser().getId(), Sort.by(Sort.Direction.DESC, "createdAt"));

        return clientBankData.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClientBankDataResponseDTO getClientLatestBankData() {
        Optional<ClientBankData> clientData = clientBankDataRepository
                .findTopByClientIdOrderByCreatedAt(userProvider.getAuthenticatedUser().getId());

        return mapper.toDto(clientData.orElse(new ClientBankData()));
    }
}
