package com.github.hronosf.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ClientAlreadyRegisteredException extends RuntimeException {

    public static final String PROFILE_ALREADY_REGISTERED = "Client with phone number: %s already exist";

    private final String phoneNumber;

    public ClientAlreadyRegisteredException(String phoneNumber) {
        super(String.format(PROFILE_ALREADY_REGISTERED, phoneNumber));
        this.phoneNumber = phoneNumber;
    }
}
