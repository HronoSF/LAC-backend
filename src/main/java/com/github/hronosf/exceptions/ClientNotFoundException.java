package com.github.hronosf.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ClientNotFoundException extends LacApiException {

    public static final String PROFILE_NOT_EXIST = "Клиент с указанным %s: %s не существует!";

    private final String qualifier;

    public ClientNotFoundException(String data, String qualifier) {
        super(String.format(PROFILE_NOT_EXIST, data, qualifier), "Профиль клиента не найден", HttpStatus.NOT_FOUND);
        this.qualifier = qualifier;
    }
}
