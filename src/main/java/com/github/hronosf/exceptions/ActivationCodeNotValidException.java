package com.github.hronosf.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ActivationCodeNotValidException extends LacApiException {

    public static final String ACTIVATION_CODE_NOT_VALID = "Введенный код не верен, либо срок действия кода истёк.";

    public ActivationCodeNotValidException() {
        super(ACTIVATION_CODE_NOT_VALID, "Код верификации не валиден", HttpStatus.BAD_REQUEST);
    }
}
