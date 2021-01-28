package com.github.hronosf.services;

import com.github.hronosf.dto.ClientBankDataResponseDTO;
import com.github.hronosf.dto.PreTrialAppealDTO;
import com.github.hronosf.model.Client;
import com.github.hronosf.model.ClientBankData;

import java.util.List;

public interface UserBankDataService {

    ClientBankData saveClientBankData(PreTrialAppealDTO preTrialRequest, Client newClient);

    List<ClientBankDataResponseDTO> getClientBankData();

    ClientBankDataResponseDTO getClientLatestBankData();
}
