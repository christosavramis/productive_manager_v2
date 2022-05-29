package com.example.application.backend.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class MissingFieldException extends DataIntegrityViolationException {

    public MissingFieldException(String s) {
        super(s);
    }
}
