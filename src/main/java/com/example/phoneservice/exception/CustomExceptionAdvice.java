package com.example.phoneservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Global advice to be used when handling custom errors.
 *
 * @Author Lubos Finka
 * */
@ControllerAdvice
public class CustomExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(PhoneNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<CustomError> phoneNotFoundHandler(PhoneNotFoundException e) {
        return new ResponseEntity<>(new CustomError(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<CustomError> invalidRequestExceptionHandler(InvalidRequestException e) {
        return new ResponseEntity<>(new CustomError(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(PhoneAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<CustomError> phoneAlreadyExistsHandler(PhoneNotFoundException e) {
        return new ResponseEntity<>(new CustomError(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
