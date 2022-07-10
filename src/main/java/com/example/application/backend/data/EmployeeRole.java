package com.example.application.backend.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmployeeRole {
    ADMIN("Admin"), BASIC("Basic");
    private final String description;
}
