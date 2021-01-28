package com.github.hronosf.exceptions;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ActivationCodeStillValidException extends LacApiException {

    public static final String ACTIVATION_CODE_STILL_VALID = "Ранее высланный код верификации ещё действителен";

    public ActivationCodeStillValidException() {
        super(ACTIVATION_CODE_STILL_VALID, StringUtils.EMPTY, HttpStatus.BAD_REQUEST);
    }
}
