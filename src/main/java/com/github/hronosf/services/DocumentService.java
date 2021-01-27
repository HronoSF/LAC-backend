package com.github.hronosf.services;

import com.github.hronosf.dto.PostInventoryDTO;
import com.github.hronosf.dto.PreTrialAppealDTO;

public interface DocumentService {

    String generatePreTrialAppeal(PreTrialAppealDTO request);

    String generatePostInventory(PostInventoryDTO request);
}
