package com.piwko.booking.api.controller;

import com.piwko.booking.api.form.common.ErrorResponse;
import com.piwko.booking.util.DateTimeUtil;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFoundHandling(ResourceNotFoundException exception, WebRequest request) {
        log.error(exception);
        return new ResponseEntity<>(createErrorResponse(exception, request), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotUniqueException.class)
    public ResponseEntity<ErrorResponse> resourceNotUniqueHandling(ResourceNotUniqueException exception, WebRequest request) {
        log.error(exception);
        return new ResponseEntity<>(createErrorResponse(exception, request), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> globalExceptionHandling(Exception exception, WebRequest request) {
        log.error(exception);
        return new ResponseEntity<>(createErrorResponse(exception, request), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse createErrorResponse(Exception exception, WebRequest request) {
        return new ErrorResponse(DateTimeUtil.getCurrentDate(), exception.getClass().getName() + ": " + exception.getMessage(), request.getDescription(false));
    }
}
