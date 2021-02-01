package com.github.hronosf.dto;

import com.github.hronosf.validation.annotations.InnSwiftBik;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PreTrialAppealRequestDTO extends ClientRegistrationRequestDTO {

    // Consumer bank info:
    @NotNull
    @InnSwiftBik(message = "Некорректное значение поля INN/SWIFT/BIK")
    private String consumerBankBik;

    @NotNull
    private String consumerBankName;

    @NotNull
    private String consumerBankCorrAcc;

    @NotNull
    @Length(min = 20, max = 20, message = "Некорректное значение номера личного счёта")
    private String customerAccountNumber;

    // Product info:
    @NotNull
    private String purchaseData;

    @NotNull
    private String productName;

    // Seller bank info:
    @NotNull
    private String sellerName;

    @NotNull
    private String sellerAddress;

    @NotNull
    @InnSwiftBik(message = "Некорректное значение поля INN продавца")
    private String sellerINN;
}
