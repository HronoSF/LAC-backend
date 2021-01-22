package com.github.hronosf.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ClientNotFoundException extends RuntimeException {

    public static final String PROFILE_NOT_EXIST = "Client with phone number: %s not found";

    private final String phoneNumber;

    public ClientNotFoundException(String phoneNumber) {
        super(String.format(PROFILE_NOT_EXIST, phoneNumber));
        this.phoneNumber = phoneNumber;
    }
}
