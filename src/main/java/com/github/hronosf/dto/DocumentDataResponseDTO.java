package com.github.hronosf.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.hronosf.dto.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDataResponseDTO {

    private String id;
    private String documentName;
    private String url;
    private ClientBankDataResponseDTO clientBankData;
    private ProductDataResponseDTO productData;
    private DocumentType type;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date updatedAt;
}
