package com.github.hronosf.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostInventoryDTO {

    private String consumerName;
    private String sellerName;
    private String sellerAddress;
}
