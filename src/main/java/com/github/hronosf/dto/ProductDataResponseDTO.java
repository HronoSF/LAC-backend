package com.github.hronosf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDataResponseDTO {

    private String purchaseData;
    private String productName;
    private String sellerName;
    private String sellerAddress;
    private String sellerINN;
}
