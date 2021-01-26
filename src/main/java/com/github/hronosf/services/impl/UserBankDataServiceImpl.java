package com.github.hronosf.services.impl;

import com.github.hronosf.domain.Client;
import com.github.hronosf.domain.ClientBankData;
import com.github.hronosf.dto.request.PreTrialAppealDTO;
import com.github.hronosf.dto.response.ClientBankDataResponseDTO;
import com.github.hronosf.mappers.ClientAccountMapper;
import com.github.hronosf.repository.ClientBankDataRepository;
import com.github.hronosf.services.UserBankDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserBankDataServiceImpl implements UserBankDataService {

    private final ClientAccountMapper clientAccountMapper;
    private final ClientBankDataRepository clientBankDataRepository;

    @Override
    public ClientBankData saveClientBankData(PreTrialAppealDTO preTrialRequest, Client newClient) {
        ClientBankData newClientBankData = ClientBankData.builder()
                .id(UUID.randomUUID().toString())
                .bik(preTrialRequest.getConsumerBankBik())
                .bankName(preTrialRequest.getConsumerBankName())
                .bankCorrAcc(preTrialRequest.getConsumerBankCorrAcc())
                .info(preTrialRequest.getConsumerInfo())
                .accountNumber(preTrialRequest.getCustomerAccountNumber())
                .client(newClient)
                .build();

        // save user's bank data:
        return clientBankDataRepository.save(newClientBankData);
    }

    @Override
    public List<ClientBankDataResponseDTO> getClientBankData(String clientId) {
        List<ClientBankData> clientBankData = clientBankDataRepository.getByClientId(
                clientId, Sort.by(Sort.Direction.DESC, "createdAt"));

        return clientBankData.stream()
                .map(clientAccountMapper::toDto)
                .collect(Collectors.toList());
    }
}
