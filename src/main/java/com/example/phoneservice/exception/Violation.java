package com.example.phoneservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Violation {

    private final String fieldName;
    private final String message;

}
