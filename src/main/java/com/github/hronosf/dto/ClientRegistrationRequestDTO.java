package com.github.hronosf.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientRegistrationRequestDTO {

    // Common info about consumer:
    protected String firstName;
    protected String middleName;
    protected String lastName;
    protected String address;

    @NotNull
    protected String phoneNumber;

    protected String password;
}
