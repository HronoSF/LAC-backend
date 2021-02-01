package com.github.hronosf.exceptions.handler;

import com.github.hronosf.dto.ExceptionResponseDTO;
import com.github.hronosf.exceptions.LacApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final static String VALIDATION_EXCEPTION = "Проверьте корректность введенных данных";

    @ExceptionHandler(value = LacApiException.class)
    public <T extends LacApiException> ResponseEntity<ExceptionResponseDTO> handleApiException(T e) {
        ExceptionResponseDTO responseDTO = new ExceptionResponseDTO()
                .setTitle(e.getTitle())
                .setDetails(e.getMessage());

        log.debug("Exception {} handled: {}", e.getClass(), e.getMessage());

        return new ResponseEntity<>(responseDTO, e.getStatus());
    }

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<ExceptionResponseDTO> anyException(Throwable e) {
        ExceptionResponseDTO responseDTO = new ExceptionResponseDTO()
                .setTitle("Что-то пошло не так, пожалуйста, сделайте скриншот и пришлите нам на email")
                .setDetails(e.getMessage());

        log.debug("Exception {} handled: {}\nStack trace:\n{}", e.getClass(), e.getMessage(), e.getStackTrace());

        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        Map<String, String> validationResults = fieldErrors.isEmpty() ?
                getErrorsFromCustomValidation(ex) : getFieldErrors(fieldErrors);

        return new ResponseEntity<>(
                new ExceptionResponseDTO(
                        VALIDATION_EXCEPTION,
                        validationResults.entrySet().stream()
                                .map(e -> e.getKey() + ":" + e.getValue())
                                .collect(Collectors.joining("\n"))
                ),
                HttpStatus.valueOf(status.value())
        );
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
