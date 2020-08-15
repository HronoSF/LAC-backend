package com.github.hronosf.exceptions.handler;

import com.github.hronosf.dto.response.ExceptionResponseDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        Map<String, String> validationResults = fieldErrors.isEmpty() ?
                getErrorsFromCustomValidation(ex) : getFieldErrors(fieldErrors);

        ResponseEntity<Object> response = new ResponseEntity<>(new ExceptionResponseDTO(validationResults)
                , HttpStatus.valueOf(status.value()));
        response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8");

        return response;
    }

    private Map<String, String> getErrorsFromCustomValidation(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getAllErrors()
                .stream()
                .filter(objectError -> objectError.getDefaultMessage() != null)
                .collect(Collectors.toMap(DefaultMessageSourceResolvable::getCode, ObjectError::getDefaultMessage));
    }

    private Map<String, String> getFieldErrors(List<FieldError> results) {
        return results.stream()
                .filter(objectError -> objectError.getDefaultMessage() != null)
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }
}
