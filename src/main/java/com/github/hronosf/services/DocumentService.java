package com.github.hronosf.services;

import com.github.hronosf.dto.request.PostInventoryDTO;
import com.github.hronosf.dto.request.PreTrialAppealDTO;

public interface DocumentService {

    String generatePreTrialAppeal(PreTrialAppealDTO request);

    String generatePostInventory(PostInventoryDTO request);
}
