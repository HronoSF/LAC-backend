package com.github.hronosf.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientRegistrationRequestDTO {

    @NotBlank
    protected String firstName;

    protected String middleName;

    @NotBlank
    protected String lastName;

    @NotBlank
    protected String address;

    @NotNull
    protected String phoneNumber;

    protected String password;
}
