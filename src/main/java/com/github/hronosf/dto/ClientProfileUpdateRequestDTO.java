package com.github.hronosf.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientProfileUpdateRequestDTO {

    private String firstName;
    private String middleName;
    private String lastName;
    private String address;
}
