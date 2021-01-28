package com.github.hronosf.controllers;

import com.github.hronosf.dto.PostInventoryDTO;
import com.github.hronosf.dto.PreTrialAppealDTO;
import com.github.hronosf.services.DocumentService;
import com.github.hronosf.services.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/documents")
public class DocumentController {

    private final DocumentService documentService;
    private final UserServiceImpl userProfileService;

    @Operation(summary = "Generation of Pre-Trial Appeal")
    @PostMapping(value = "/generate_pretrial_appeal", produces = "application/pdf")
    public HttpEntity<Resource> generatePreTrialAppeal(@RequestBody @Valid PreTrialAppealDTO request) {
        userProfileService.createUserIfNotExistAndAddUserBankData(request);
        return new HttpEntity<>(new FileSystemResource(documentService.generatePreTrialAppeal(request)));
    }

    @Operation(summary = "Generation of Russian Post inventory")
    @PostMapping(value = "/generate_post_inventory", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    public HttpEntity<Resource> generatePostInventory(@RequestBody PostInventoryDTO request) {
        return new HttpEntity<>(new FileSystemResource(documentService.generatePostInventory(request)));
    }
}
