package com.github.hronosf.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ClientNotActivatedException extends LacApiException {

    public static final String PROFILE_ALREADY_REGISTERED = "Клиент с указанным номером телефона: %s ещё не активирован!";

    private final String phoneNumber;

    public ClientNotActivatedException(String phoneNumber) {
        super(String.format(PROFILE_ALREADY_REGISTERED, phoneNumber), "Профиль клиента не активирован", HttpStatus.BAD_REQUEST);
        this.phoneNumber = phoneNumber;
    }
}
