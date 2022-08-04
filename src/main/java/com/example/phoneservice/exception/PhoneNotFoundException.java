package com.example.phoneservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PhoneNotFoundException extends RuntimeException {

    public PhoneNotFoundException(Long id) {
        super("Could not find phone with id: " + id);
    }

}
