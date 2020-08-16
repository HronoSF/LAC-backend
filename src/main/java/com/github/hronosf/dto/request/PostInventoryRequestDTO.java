package com.github.hronosf.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostInventoryRequestDTO {

    private String consumerName;
    private String sellerName;
    private String sellerAddress;
}
