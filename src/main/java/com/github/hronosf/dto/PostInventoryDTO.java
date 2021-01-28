package com.github.hronosf.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostInventoryDTO {

    @NotNull
    private String consumerName;

    @NotNull
    private String sellerName;

    @NotNull
    private String sellerAddress;
}
