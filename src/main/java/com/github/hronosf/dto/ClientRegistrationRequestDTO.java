package com.github.hronosf.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientRegistrationRequestDTO {

    // Common info about consumer:
    protected String firstName;
    protected String middleName;
    protected String lastName;
    protected String address;
    protected String phoneNumber;
    protected String password;
}
