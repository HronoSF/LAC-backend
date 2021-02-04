package com.github.hronosf.services;

import com.github.hronosf.dto.PostInventoryRequestDTO;
import com.github.hronosf.dto.PreTrialAppealRequestDTO;

public interface DocumentGenerationService {

    String generatePretrialAppeal(PreTrialAppealRequestDTO request);

    String generatePostInventory(PostInventoryRequestDTO request);
}
