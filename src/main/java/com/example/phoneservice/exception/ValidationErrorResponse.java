package com.example.phoneservice.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Data
public class ValidationErrorResponse {

    private int httpStatus;
    private final List<Violation> violations = new LinkedList<>();

    public void addViolation(Violation v) {
        if (v != null) {
            this.violations.add(v);
        }
    }

}
