package com.github.hronosf.services.impl;

import com.github.hronosf.dto.PreTrialAppealRequestDTO;
import com.github.hronosf.mappers.ClientBankDataMapper;
import com.github.hronosf.model.ClientBankData;
import com.github.hronosf.repository.ClientBankDataRepository;
import com.github.hronosf.services.UserBankDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserBankDataServiceImpl implements UserBankDataService {

    private final ClientBankDataMapper mapper;

    private final ClientBankDataRepository clientBankDataRepository;

    @Override
    public ClientBankData saveClientBankData(PreTrialAppealRequestDTO request) {
        ClientBankData newClientBankData = mapper.toClientBankDataData(request);

        // save user's bank data:
        return clientBankDataRepository.save(newClientBankData);
    }
}
