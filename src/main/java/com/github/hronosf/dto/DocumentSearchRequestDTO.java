package com.github.hronosf.dto;

import com.github.hronosf.dto.enums.DocumentType;
import lombok.Data;

import java.util.List;

@Data
public class DocumentSearchRequestDTO {

    private String documentName;

    private String productName;

    private String sellerName;

    private String sellerINN;

    private List<DocumentType> documentTypes;

    private String clientId;
}
