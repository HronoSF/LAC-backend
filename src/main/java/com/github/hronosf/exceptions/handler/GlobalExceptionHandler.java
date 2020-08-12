package com.github.hronosf.exceptions.handler;

import com.github.hronosf.dto.response.ExceptionResponseDTO;
import com.jayway.jsonpath.PathNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@Component
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PathNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handleDadataRequestMismatches(PathNotFoundException exception) {
        final boolean isSeller = exception.getStackTrace()[4].getMethodName().contains("Seller");

        String who = isSeller ? "продавце" : "вас";
        String cause = isSeller ?
                "ИНН продавца" :
                "ваш ИНН/SWIFT/БИК";

        Map<String, String> errors = new HashMap<>();

        errors.put(isSeller ? "sellerInfo" : "consumerInfo"
                , String.format("Мы не смогли найти информацию о %s,проверьте корректность введённых данных: %s", who, cause));

        return ResponseEntity.badRequest().body(new ExceptionResponseDTO(errors));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> validationResults;

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        if (fieldErrors.isEmpty()) {
            validationResults = ex.getBindingResult()
                    .getAllErrors()
                    .stream()
                    .filter(objectError -> objectError.getDefaultMessage() != null)
                    .collect(Collectors.toMap(DefaultMessageSourceResolvable::getCode, ObjectError::getDefaultMessage));
        } else {
            validationResults = fieldErrors
                    .stream()
                    .filter(objectError -> objectError.getDefaultMessage() != null)
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        }
        Map<String, String> errors = new HashMap<>(validationResults);
        return new ResponseEntity<>(new ExceptionResponseDTO(errors), HttpStatus.valueOf(status.value()));
    }
}
