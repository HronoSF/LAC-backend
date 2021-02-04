package com.github.hronosf.services.impl;

import com.github.hronosf.authentication.providers.UserProvider;
import com.github.hronosf.dto.DocumentDataResponseDTO;
import com.github.hronosf.dto.DocumentSearchRequestDTO;
import com.github.hronosf.dto.PostInventoryRequestDTO;
import com.github.hronosf.dto.PreTrialAppealRequestDTO;
import com.github.hronosf.dto.enums.DocumentType;
import com.github.hronosf.mappers.DocumentMapper;
import com.github.hronosf.model.*;
import com.github.hronosf.repository.DocumentRepository;
import com.github.hronosf.services.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final UserProvider userProvider;

    private final DocumentMapper mapper;

    private final DocumentRepository documentRepository;

    private final S3Service s3Service;
    private final ClientService clientService;
    private final ProductDataService productDataService;
    private final UserBankDataService userBankDataService;
    private final DocumentGenerationService documentGenerationService;

    @Override
    public String generatePreTrialAppeal(PreTrialAppealRequestDTO request) {
        // Generate doc:
        String pathToDocument = documentGenerationService.generatePretrialAppeal(request);

        // Upload to S3:
        Client client = clientService.getByPhoneNumber(request.getPhoneNumber());
        uploadGeneratedDocumentToS3(pathToDocument, client.getId());

        // save client bank data:
        ProductData productData = productDataService.saveProductData(request);
        ClientBankData clientBankData = userBankDataService.saveClientBankData(request);

        // save document data:
        saveDocument(
                client,
                clientBankData,
                productData,
                getFileName(pathToDocument),
                DocumentType.PRE_TRIAL
        );

        return pathToDocument;
    }

    @Override
    public String generatePostInventory(PostInventoryRequestDTO request) {
        Client client = clientService.getByPhoneNumber(request.getPhoneNumber());
        String pathToDocument = documentGenerationService.generatePostInventory(request);

        // upload to S3:
        uploadGeneratedDocumentToS3(pathToDocument, client.getId());

        saveDocument(
                client,
                null,
                null,
                getFileName(pathToDocument),
                DocumentType.POST_INVENTORY
        );

        return pathToDocument;
    }

    @Override
    public List<DocumentDataResponseDTO> getDocumentsOfLoggedUser() {
        String clientId = userProvider.getAuthenticatedUser().getId();

        // TODO: add filtering
        return documentRepository.getAllByClientId(clientId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DocumentDataResponseDTO> searchClientDocuments(Pageable pageable, DocumentSearchRequestDTO request) {
        User user = userProvider.getAuthenticatedUser();

        if (userProvider.isUserClient()) {

        } else if (userProvider.isUserAdministrator()) {

        }

        return null;
    }

    private void saveDocument(Client client, ClientBankData clientBankData,
                              ProductData productData, String fileName, DocumentType documentType) {
        Document document = new Document()
                .setId(UUID.randomUUID().toString())
                .setClient(client)
                .setClientBankData(clientBankData)
                .setProductData(productData)
                .setCreatedAt(new Date())
                .setType(documentType)
                .setSavedBeforeActivation(!client.isActivated())
                .setUrl(s3Service.getS3Url(fileName))
                .setDocumentName(fileName);

        documentRepository.save(document);
    }

    private void uploadGeneratedDocumentToS3(String path, String clientId) {
        String keyName = clientId + "/";
        String pathToFileInBucket = StringUtils.substringAfter(path, "generatedDocuments" + File.separator);

        s3Service.uploadFileToS3(keyName + pathToFileInBucket, path, s3Service.getS3BucketName());
    }

    private String getFileName(String pathToDocument) {
        return StringUtils.substringAfterLast(pathToDocument, File.separator);
    }
}


