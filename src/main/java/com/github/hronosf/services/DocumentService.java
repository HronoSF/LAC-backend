package com.github.hronosf.services;

import com.github.hronosf.dto.DocumentDataResponseDTO;
import com.github.hronosf.dto.PostInventoryRequestDTO;
import com.github.hronosf.dto.PreTrialAppealRequestDTO;

import java.util.List;

public interface DocumentService {

    String generatePreTrialAppeal(PreTrialAppealRequestDTO request);

    String generatePostInventory(PostInventoryRequestDTO request);

    List<DocumentDataResponseDTO> getAllDocumentsData();
}
