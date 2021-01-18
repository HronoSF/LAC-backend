package com.github.hronosf.exceptions;

public abstract class ApiException extends Exception {

    public ApiException(String message) {
        super(message);
    }
}
