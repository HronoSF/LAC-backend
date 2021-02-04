package com.github.hronosf.controllers;

import com.github.hronosf.model.Document;
import com.github.hronosf.model.User;
import com.github.hronosf.repository.ClientBankDataRepository;
import com.github.hronosf.repository.DocumentRepository;
import com.github.hronosf.repository.ProductDataRepository;
import com.github.hronosf.repository.UserRepository;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Hidden
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final UserRepository<User> userRepository;
    private final DocumentRepository documentRepository;
    private final ProductDataRepository productDataRepository;
    private final ClientBankDataRepository clientBankDataRepository;

    @GetMapping("/get_api_token")
    public String getDadataAccessKey(@Value("${dadata.api.key}") String apiKey) {
        return apiKey;
    }

    @Transactional
    @PostMapping("/delete_client")
    public void deleteUser(@RequestParam String phoneNumber) {
        userRepository.getByPhoneNumber(phoneNumber)
                .ifPresent(user -> {
                    List<Document> userDocs = documentRepository.getAllByClientId(user.getId());
                    userDocs.forEach(doc -> {
                        productDataRepository.deleteById(doc.getProductData().getId());
                        clientBankDataRepository.deleteById(doc.getClientBankData().getId());
                        documentRepository.deleteByClientId(user.getId());
                    });
                    userRepository.deleteById(user.getId());
                });
    }
}
