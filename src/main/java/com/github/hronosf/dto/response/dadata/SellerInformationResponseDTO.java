package com.github.hronosf.dto.response.dadata;

import lombok.*;

@Getter
@Builder
public class SellerInformationResponseDTO {

    private final String name;
    private final String inn;
    private final String address;
}
