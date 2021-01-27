package com.github.hronosf.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientProfileActivationDTO {

    private String phoneNumber;
    private int verificationCode;
}
