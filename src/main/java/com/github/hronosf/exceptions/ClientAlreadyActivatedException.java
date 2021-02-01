package com.github.hronosf.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ClientAlreadyActivatedException extends LacApiException {

    public static final String PROFILE_ALREADY_ACTIVATED = "Клиент с указанным номером телефона: %s уже активирован!";

    private final String phoneNumber;

    public ClientAlreadyActivatedException(String phoneNumber) {
        super(String.format(PROFILE_ALREADY_ACTIVATED, phoneNumber), "Клиент уже был активирован", HttpStatus.BAD_REQUEST);
        this.phoneNumber = phoneNumber;
    }
}
