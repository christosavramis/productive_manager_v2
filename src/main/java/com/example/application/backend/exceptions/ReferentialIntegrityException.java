package com.example.application.backend.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class ReferentialIntegrityException extends DataIntegrityViolationException {

    public ReferentialIntegrityException(String s) {
        super(s);
    }
}
