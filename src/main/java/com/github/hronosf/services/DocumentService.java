package com.github.hronosf.services;

import com.github.hronosf.dto.request.PostInventoryRequestDTO;
import com.github.hronosf.dto.request.PreTrialAppealRequestDTO;

public interface DocumentService {

    String generatePreTrialAppeal(PreTrialAppealRequestDTO request);

    String generatePostInventory(PostInventoryRequestDTO request);
}
