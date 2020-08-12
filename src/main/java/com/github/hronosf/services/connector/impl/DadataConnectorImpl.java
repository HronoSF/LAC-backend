package com.github.hronosf.services.connector.impl;

import com.github.hronosf.dto.response.dadata.CustomerInformationResponseDTO;
import com.github.hronosf.dto.response.dadata.SellerInformationResponseDTO;
import com.github.hronosf.services.connector.DadataConnector;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class DadataConnectorImpl implements DadataConnector {

    @Value("${dadata.customer.info.url}")
    private String dadataCustomerInfoUrl;

    @Value("${dadata.seller.info.url}")
    private String dadataSellerInfoUrl;

    @Value("${dadata.key}")
    private String apiKeyDadata;

    private final RestTemplate restTemplate = new RestTemplate();

    // I decided not to use deserialization (i.e. object mapping) because we need only three-five field from complex response
    // Much easier parse json than create over 9999 classes for mapping.

    @Override
    @SneakyThrows
    public CustomerInformationResponseDTO getCustomerBankInfoByBikOrInn(String bikOrInnOrSwift) {
        String response = sendPostRequestToDadata(bikOrInnOrSwift, dadataCustomerInfoUrl);

        DocumentContext document = JsonPath.parse(response);
        String bankName = document.read("$.suggestions[0].value");
        String bik = document.read("$.suggestions[0].data.bic");
        String corrAcc = document.read("$.suggestions[0].data.correspondent_account");

        return CustomerInformationResponseDTO.builder()
                .name(bankName)
                .bik(bik)
                .corrAcc(corrAcc)
                .build();
    }

    @Override
    @SneakyThrows
    public SellerInformationResponseDTO getSellerInfoByInn(String inn) {
        String response = sendPostRequestToDadata(inn, dadataSellerInfoUrl);

        DocumentContext document = JsonPath.parse(response);
        String name = document.read("$.suggestions[0].data.name.full_with_opf");
        String address = document.read("$.suggestions[0].data.address.unrestricted_value");

        return SellerInformationResponseDTO.builder()
                .name(name)
                .inn(inn)
                .address(address)
                .build();
    }

    private String sendPostRequestToDadata(String query, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Token " + apiKeyDadata);

        Map<String, String> body = new HashMap<>();
        body.put("query", query);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        return restTemplate.postForObject(url, entity, String.class);
    }
}
