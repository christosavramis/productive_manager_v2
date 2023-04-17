package com.example.application.backend.data.entities;

import lombok.*;

import javax.persistence.Entity;

@Setter @Getter @Builder @AllArgsConstructor @NoArgsConstructor
@Entity
public class StringJPA extends AbstractEntity {
    private String value;
}
