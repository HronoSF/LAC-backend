package com.github.hronosf.model.payload.response.dadata;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerInformationDTO {

    private final String name;
    private final String bik;
    private final String corrAcc;
}
