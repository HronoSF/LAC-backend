package com.github.hronosf.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientBankDataResponseDTO {

    private String bik;
    private String bankName;
    private String bankCorrAcc;
    private String accountNumber;
    private Date createdAt;
}
