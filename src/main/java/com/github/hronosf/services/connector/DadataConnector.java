package com.github.hronosf.services.connector;

import com.github.hronosf.dto.response.dadata.CustomerInformationResponseDTO;
import com.github.hronosf.dto.response.dadata.SellerInformationResponseDTO;

public interface DadataConnector {

    CustomerInformationResponseDTO getCustomerBankInfoByBikOrInn(String bikOrInnOrSwift);

    SellerInformationResponseDTO getSellerInfoByInn(String inn);
}
