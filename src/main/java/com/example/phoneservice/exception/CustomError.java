package com.example.phoneservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomError {

    private String message;
    private LocalDateTime timeStamp = LocalDateTime.now();

    public CustomError(String msg) {
        this.message = msg;
    }

}
