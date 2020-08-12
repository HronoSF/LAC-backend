package com.github.hronosf.dto.request;

import com.github.hronosf.validation.annotations.InnSwiftBik;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PreTrialAppealRequestDTO {

    private String firstName;
    private String middleName;
    private String lastName;
    private String address;

    @InnSwiftBik(message = "Некорректное значение поля INN/SWIFT/BIK, проверьте введённые данные")
    private String consumerInfo;

    @Length(min = 20, max = 20, message = "Некорректное значение номера личного счёта, проверьте введённые данные")
    private String customerAccountNumber;

    @InnSwiftBik(message = "Некорректное значение поля INN продавца, проверьте введённые данные")
    private String sellerINN;

    private String purchaseData;

    private String productName;
}
