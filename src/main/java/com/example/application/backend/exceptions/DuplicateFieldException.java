package com.example.application.backend.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class DuplicateFieldException extends DataIntegrityViolationException {

    public DuplicateFieldException(String s) {
        super(s);
    }
}
