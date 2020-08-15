package com.github.hronosf.controllers;

import com.github.hronosf.dto.request.PostInventoryRequestDTO;
import com.github.hronosf.dto.request.PreTrialAppealRequestDTO;
import com.github.hronosf.services.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/documents")
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping(value = "/generate_pretrial_appeal", produces = "application/pdf")
    public HttpEntity<Resource> generatePreTrialAppeal(@RequestBody @Valid PreTrialAppealRequestDTO request) {
        return new HttpEntity<>(new FileSystemResource(documentService.generatePreTrialAppeal(request)));
    }

    @PostMapping(value = "/generate_post_inventory", produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    public HttpEntity<Resource> generatePostInventory(@RequestBody PostInventoryRequestDTO request) {
        return new HttpEntity<>(new FileSystemResource(documentService.generatePostInventory(request)));
    }
}
