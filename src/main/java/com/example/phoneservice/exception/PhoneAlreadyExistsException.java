package com.example.phoneservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PhoneAlreadyExistsException extends RuntimeException {

    public PhoneAlreadyExistsException(Long id) {
        super("Phone with id: " + id + " already exists!");
    }
}
