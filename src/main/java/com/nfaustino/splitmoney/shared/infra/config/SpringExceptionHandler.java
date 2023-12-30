package com.nfaustino.splitmoney.shared.infra.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.nfaustino.splitmoney.shared.base.ApiResponse;
import com.nfaustino.splitmoney.shared.base.exceptions.BadRequest;
import com.nfaustino.splitmoney.shared.base.exceptions.NotFound;

@ControllerAdvice
@Component("error")
public class SpringExceptionHandler
        extends ResponseEntityExceptionHandler {

    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleExceptionInternal(ex, ApiResponse.validationError(getFieldError(ex)),
                new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(value = { BadRequest.class })
    public ResponseEntity<Object> handleException(
            BadRequest ex, WebRequest request) {
        ex.printStackTrace();
        return handleExceptionInternal(ex, ApiResponse.badRequest(ex.getMessage()),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { NotFound.class })
    public ResponseEntity<Object> handleException(
            NotFound ex, WebRequest request) {
        ex.printStackTrace();
        return handleExceptionInternal(ex, ApiResponse.notFound(ex.getMessage()),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handleGenericException(
            Exception ex, WebRequest request) {
        ex.printStackTrace();
        return handleExceptionInternal(ex, ApiResponse.internalServerError(),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private String getFieldError(MethodArgumentNotValidException ex) {
        var field = ex.getFieldError();
        return field != null ? field.getDefaultMessage() : "Bad request";
    }
}