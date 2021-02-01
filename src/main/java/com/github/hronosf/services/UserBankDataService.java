package com.github.hronosf.services;

import com.github.hronosf.dto.ClientBankDataResponseDTO;
import com.github.hronosf.dto.PreTrialAppealRequestDTO;
import com.github.hronosf.model.Client;
import com.github.hronosf.model.ClientBankData;

import java.util.List;

public interface UserBankDataService {

    ClientBankData saveClientBankData(PreTrialAppealRequestDTO preTrialRequest, Client newClient);

    List<ClientBankDataResponseDTO> getClientBankData();

    ClientBankDataResponseDTO getClientLatestBankData();
}
