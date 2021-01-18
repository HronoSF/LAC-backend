package com.github.hronosf.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class RequestWithUserDataDTO {

    // Common info about consumer:
    protected String firstName;
    protected String middleName;
    protected String lastName;
    protected String address;

    protected String phoneNumber;
    protected String password;
}
