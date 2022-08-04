package com.example.phoneservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

/**
 * Global advice to be used when handling spring validation errors.
 *
 * @Author Lubos Finka
 * */
@ControllerAdvice
public class ValidationErrorHandlingAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        error.setHttpStatus(HttpStatus.BAD_REQUEST.value());
        if (!CollectionUtils.isEmpty(e.getConstraintViolations())) {
            e.getConstraintViolations()
                    .stream()
                    .map(v -> new Violation(v.getPropertyPath().toString(), v.getMessage()))
                    .forEach(error::addViolation);
        }

        return error;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        error.setHttpStatus(HttpStatus.BAD_REQUEST.value());
        if (!CollectionUtils.isEmpty(e.getBindingResult().getFieldErrors())) {
            e.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(v -> new Violation(v.getField(), v.getDefaultMessage()))
                    .forEach(error::addViolation);
        }

        return error;
    }

}

