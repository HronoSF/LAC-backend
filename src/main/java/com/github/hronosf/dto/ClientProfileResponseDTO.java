package com.github.hronosf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientProfileResponseDTO {

    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private List<String> roles;
    private ClientBankDataResponseDTO latestBankData;
}
