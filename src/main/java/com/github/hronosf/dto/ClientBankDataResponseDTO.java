package com.github.hronosf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientBankDataResponseDTO {

    private String bik;
    private String bankName;
    private String bankCorrAcc;
    private String accountNumber;
    private String address;
}
