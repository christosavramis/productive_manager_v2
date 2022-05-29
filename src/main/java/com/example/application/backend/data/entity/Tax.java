package com.example.application.backend.data.entity;

import lombok.*;

import javax.persistence.*;

@Setter @Getter @Builder @AllArgsConstructor @NoArgsConstructor
@Entity
public class Tax extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String name;

    private double value;

    @Override
    public String toString() {
        return "Tax{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
