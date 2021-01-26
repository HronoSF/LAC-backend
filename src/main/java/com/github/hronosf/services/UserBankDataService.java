package com.github.hronosf.services;

import com.github.hronosf.domain.Client;
import com.github.hronosf.domain.ClientBankData;
import com.github.hronosf.dto.request.PreTrialAppealDTO;
import com.github.hronosf.dto.response.ClientBankDataResponseDTO;

import java.util.List;

public interface UserBankDataService {

    ClientBankData saveClientBankData(PreTrialAppealDTO preTrialRequest, Client newClient);

    List<ClientBankDataResponseDTO> getClientBankData(String clientId);
}
