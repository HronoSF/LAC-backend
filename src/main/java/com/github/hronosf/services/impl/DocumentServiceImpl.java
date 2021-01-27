package com.github.hronosf.services.impl;

import com.github.hronosf.dto.PostInventoryDTO;
import com.github.hronosf.dto.PreTrialAppealDTO;
import com.github.hronosf.services.DocumentGenerationService;
import com.github.hronosf.services.DocumentService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentGenerationService documentGenerationService;

    public String generatePreTrialAppeal(PreTrialAppealDTO request) {
        Map<String, String> consumerAndSellerData = new HashMap<>();

        // seller's data:
        consumerAndSellerData.put("SELLER", request.getSellerName());
        consumerAndSellerData.put("INN", request.getSellerINN());
        consumerAndSellerData.put("SELADR", request.getSellerAddress());

        // consumer's data:
        final String consumerFullName = String.format("%s %s %s"
                , request.getLastName()
                , request.getFirstName()
                , request.getMiddleName() == null ? StringUtils.EMPTY : request.getMiddleName());

        consumerAndSellerData.put("CONSUMER", consumerFullName);
        consumerAndSellerData.put("CONADR", request.getAddress());

        consumerAndSellerData.put("BANKNAME", request.getConsumerBankName());
        consumerAndSellerData.put("BIK", request.getConsumerBankBik());
        consumerAndSellerData.put("CORRACC", request.getConsumerBankCorrAcc());
        consumerAndSellerData.put("CONSACC", request.getFirstName());

        // purchase data:
        consumerAndSellerData.put("PURCHDATA",
                DateTimeFormat.forPattern("dd.MM.yyyy")
                        .print(
                                DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                                        .parseDateTime(request.getPurchaseData()
                                        )
                        )
        );

        consumerAndSellerData.put("PRODUCT", request.getProductName());

        // date:
        consumerAndSellerData.put("CLAIMDATA", new SimpleDateFormat("dd.MM.yyyy").format(new Date()));

        return documentGenerationService.generatePretrialAppeal(consumerAndSellerData);
    }

    public String generatePostInventory(PostInventoryDTO request) {
        Map<String, String> sellerData = new HashMap<>();

        // seller's data:
        sellerData.put("CONSUMER", request.getConsumerName());
        sellerData.put("SELLER", request.getSellerName());
        sellerData.put("SELADR", request.getSellerAddress());

        return documentGenerationService.generatePostInventory(sellerData);
    }
}


