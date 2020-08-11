package com.github.hronosf.exceptions.handler;

import com.github.hronosf.exceptions.LacException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Component
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(LacException.class)
    public void handleError(LacException exception) {
        // TODO: finish exception handling
    }
}
