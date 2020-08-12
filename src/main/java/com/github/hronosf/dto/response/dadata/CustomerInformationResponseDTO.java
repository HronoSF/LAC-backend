package com.github.hronosf.dto.response.dadata;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerInformationResponseDTO {

    private final String name;
    private final String bik;
    private final String corrAcc;
}
