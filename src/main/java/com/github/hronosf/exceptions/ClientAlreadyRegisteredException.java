package com.github.hronosf.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ClientAlreadyRegisteredException extends LacApiException {

    public static final String PROFILE_ALREADY_REGISTERED = "Клиент с указанным номером телефона: %s уже существует!";

    private final String phoneNumber;

    public ClientAlreadyRegisteredException(String phoneNumber) {
        super(String.format(PROFILE_ALREADY_REGISTERED, phoneNumber), "Профиль клиента уже существует", HttpStatus.BAD_REQUEST);
        this.phoneNumber = phoneNumber;
    }
}
