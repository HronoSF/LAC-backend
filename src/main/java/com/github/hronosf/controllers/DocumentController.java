package com.github.hronosf.controllers;

import com.github.hronosf.dto.DocumentDataResponseDTO;
import com.github.hronosf.dto.PostInventoryRequestDTO;
import com.github.hronosf.dto.PreTrialAppealRequestDTO;
import com.github.hronosf.services.DocumentService;
import com.github.hronosf.services.impl.ClientServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/documents")
public class DocumentController {

    private final DocumentService documentService;
    private final ClientServiceImpl userService;

    @Operation(summary = "Generation of Pre-Trial Appeal")
    @PostMapping(value = "/generate_pretrial_appeal", produces = "application/pdf")
    public HttpEntity<Resource> generatePreTrialAppeal(@RequestBody @Valid PreTrialAppealRequestDTO request) {
        userService.createClientIfNotExist(request);
        return new HttpEntity<>(new FileSystemResource(documentService.generatePreTrialAppeal(request)));
    }

    @Operation(summary = "Generation of Russian Post inventory")
    @PostMapping(value = "/generate_post_inventory", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    public HttpEntity<Resource> generatePostInventory(@RequestBody PostInventoryRequestDTO request) {
        return new HttpEntity<>(new FileSystemResource(documentService.generatePostInventory(request)));
    }

    @GetMapping("/get_all")
    @PreAuthorize("@userProvider.activatedClient()")
    @Operation(summary = "Get links to all authenticated user documents")
    public List<DocumentDataResponseDTO> searchClientDocuments() {
        return documentService.getDocumentsOfLoggedUser();
    }
}
