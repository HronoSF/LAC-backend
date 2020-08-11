package com.github.hronosf.model.payload.response.dadata;

import lombok.*;

@Getter
@Builder
public class SellerInformationDTO {

    private final String name;
    private final String inn;
    private final String address;
}
