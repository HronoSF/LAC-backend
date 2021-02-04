package com.github.hronosf.services;

import com.github.hronosf.dto.DocumentDataResponseDTO;
import com.github.hronosf.dto.DocumentSearchRequestDTO;
import com.github.hronosf.dto.PostInventoryRequestDTO;
import com.github.hronosf.dto.PreTrialAppealRequestDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DocumentService {

    String generatePreTrialAppeal(PreTrialAppealRequestDTO request);

    String generatePostInventory(PostInventoryRequestDTO request);

    List<DocumentDataResponseDTO> getDocumentsOfLoggedUser();

    List<DocumentDataResponseDTO> searchClientDocuments(Pageable pageable, DocumentSearchRequestDTO request);
}
