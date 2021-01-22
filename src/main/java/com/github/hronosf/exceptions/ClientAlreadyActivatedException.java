package com.github.hronosf.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ClientAlreadyActivatedException extends RuntimeException {

    public static final String PROFILE_ALREADY_ACTIVATED = "Client with phone number: %s already activated";

    private final String phoneNumber;

    public ClientAlreadyActivatedException(String phoneNumber) {
        super(String.format(PROFILE_ALREADY_ACTIVATED, phoneNumber));
        this.phoneNumber = phoneNumber;
    }
}
