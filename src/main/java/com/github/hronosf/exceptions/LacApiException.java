package com.github.hronosf.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class LacApiException extends RuntimeException {

    private final String title;
    private final HttpStatus status;

    public LacApiException(String message, String title, HttpStatus status) {
        super(message);
        this.title = title;
        this.status = status;
    }
}
