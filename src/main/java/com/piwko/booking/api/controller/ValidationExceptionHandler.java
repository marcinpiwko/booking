package com.piwko.booking.api.controller;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.piwko.booking.api.form.common.ErrorResponse;
import com.piwko.booking.util.DateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @Getter
    @Setter
    @AllArgsConstructor
    private static class ValidationErrorResponse {

        private LocalDateTime timestamp;

        private Integer status;

        private Map<String, String> errors;

        private String path;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorResponse handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ValidationErrorResponse(DateTimeUtil.getCurrentDate(), HttpStatus.BAD_REQUEST.value(), errors, request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ValidationErrorResponse handleValidationException(ConstraintViolationException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(c -> errors.put(c.getPropertyPath().toString(), c.getMessage()));
        return new ValidationErrorResponse(DateTimeUtil.getCurrentDate(), HttpStatus.BAD_REQUEST.value(), errors, request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnrecognizedPropertyException.class)
    public ValidationErrorResponse handleValidationException(UnrecognizedPropertyException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getPropertyName(), "unrecognized field");
        return new ValidationErrorResponse(DateTimeUtil.getCurrentDate(), HttpStatus.BAD_REQUEST.value(), errors, request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        return new ErrorResponse(DateTimeUtil.getCurrentDate(), HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.name(), ex.getMessage(), request.getRequestURI());
    }

}
