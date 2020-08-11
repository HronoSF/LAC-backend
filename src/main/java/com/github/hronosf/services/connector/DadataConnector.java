package com.github.hronosf.services.connector;

import com.github.hronosf.model.payload.response.dadata.CustomerInformationDTO;
import com.github.hronosf.model.payload.response.dadata.SellerInformationDTO;

public interface DadataConnector {

    CustomerInformationDTO getCustomerBankInfoByBikOrInn(String bikOrInnOrSwift);

    SellerInformationDTO getSellerInfoByInn(String inn);
}
