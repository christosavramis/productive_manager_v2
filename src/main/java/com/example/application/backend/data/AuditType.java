package com.example.application.backend.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuditType {
    TRACE, DEBUG, INFO, WARN, ERROR
}
