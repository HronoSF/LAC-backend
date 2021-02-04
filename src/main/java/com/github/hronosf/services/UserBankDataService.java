package com.github.hronosf.services;

import com.github.hronosf.dto.PreTrialAppealRequestDTO;
import com.github.hronosf.model.ClientBankData;

public interface UserBankDataService {

    ClientBankData saveClientBankData(PreTrialAppealRequestDTO preTrialRequest);
}
