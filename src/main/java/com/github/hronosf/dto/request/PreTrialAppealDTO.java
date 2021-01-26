package com.github.hronosf.dto.request;

import com.github.hronosf.validation.annotations.InnSwiftBik;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PreTrialAppealDTO extends ClientRegistrationRequestDTO {

    // Consumer bank info:
    @InnSwiftBik(message = "Некорректное значение поля INN/SWIFT/BIK, проверьте введённые данные")
    private String consumerBankBik;

    private String consumerBankName;

    private String consumerBankCorrAcc;

    @Length(min = 20, max = 20, message = "Некорректное значение номера личного счёта, проверьте введённые данные")
    private String customerAccountNumber;

    // Product info:
    private String purchaseData;
    private String productName;

    // Seller bank info:
    private String sellerName;
    private String sellerAddress;

    @InnSwiftBik(message = "Некорректное значение поля INN продавца, проверьте введённые данные")
    private String sellerINN;

    private boolean saveUserData;
}
