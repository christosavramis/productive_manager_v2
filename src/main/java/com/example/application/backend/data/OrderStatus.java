package com.example.application.backend.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    CANCELED, NEW, CLOSED;
}
