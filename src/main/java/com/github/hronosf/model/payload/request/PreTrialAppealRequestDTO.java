package com.github.hronosf.model.payload.request;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PreTrialAppealRequestDTO {

    private String firstName;
    private String middleName;
    private String lastName;
    private String address;
    private String consumerInfo;
    private String customerAccountNumber;
    private String sellerINN;
    private String purchaseData;
    private String productName;
}
