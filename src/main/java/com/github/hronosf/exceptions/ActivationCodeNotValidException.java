package com.github.hronosf.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ActivationCodeNotValidException extends RuntimeException {

    public static final String PROFILE_ALREADY_ACTIVATED = "Provided verification code was used or expired";

    public ActivationCodeNotValidException() {
        super(PROFILE_ALREADY_ACTIVATED);
    }
}
