package com.github.hronosf.services.facedes;

import com.github.hronosf.dto.request.PreTrialAppealRequestDTO;
import com.github.hronosf.dto.response.dadata.CustomerInformationResponseDTO;
import com.github.hronosf.dto.response.dadata.SellerInformationResponseDTO;
import com.github.hronosf.services.DocumentGenerationService;
import com.github.hronosf.services.connector.DadataConnector;
import lombok.RequiredArgsConstructor;
import org.joda.time.format.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DocumentGeneratorFacade {

    private final DadataConnector dadataConnector;
    private final DocumentGenerationService documentGenerationService;

    public FileInputStream generatePreTrialAppeal(PreTrialAppealRequestDTO request) {
        SellerInformationResponseDTO sellerInfo = dadataConnector.getSellerInfoByInn(request.getSellerINN());
        CustomerInformationResponseDTO consumerInfo = dadataConnector.getCustomerBankInfoByBikOrInn(request.getConsumerInfo());

        Map<String, String> consumerAndSellerData = new HashMap<>();

        // seller's data:
        consumerAndSellerData.put("SELLER", sellerInfo.getName());
        consumerAndSellerData.put("INN", sellerInfo.getInn());
        consumerAndSellerData.put("SELADR", sellerInfo.getAddress());

        // consumer's data:
        consumerAndSellerData.put("CONSUMER"
                , String.format("%s %s %s"
                        , request.getLastName()
                        , request.getFirstName().charAt(0) + "."
                        , request.getMiddleName().charAt(0) + "."));
        consumerAndSellerData.put("CONADR", request.getAddress());

        consumerAndSellerData.put("BANKNAME", consumerInfo.getName());
        consumerAndSellerData.put("BIK", consumerInfo.getBik());
        consumerAndSellerData.put("CORRACC", consumerInfo.getCorrAcc());
        consumerAndSellerData.put("CONSACC", request.getCustomerAccountNumber());

        // purchase data:

        // parse date:
        String date = DateTimeFormat.forPattern("dd.MM.yyyy")
                .print(DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parseDateTime(request.getPurchaseData()));

        consumerAndSellerData.put("PURCHDATA", date);
        consumerAndSellerData.put("PRODUCT", request.getProductName());

        // date:
        consumerAndSellerData.put("CLAIMDATA", new SimpleDateFormat("dd.MM.yyyy").format(new Date()));

        return documentGenerationService.generatePretrialDocument(consumerAndSellerData);
    }
}
