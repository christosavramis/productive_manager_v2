package com.example.application.backend.data.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Setter @Getter @Builder @AllArgsConstructor @NoArgsConstructor
@Entity
public class Policy extends AbstractEntity {
    private String name;
    private String defaultValue;
    private String selectedValue;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn
    private List<StringJPA> values;

    public String getUserSelectedValue() {
        if (selectedValue == null) {
            return defaultValue;
        }

        return selectedValue;
    }

    public List<String> getValues(){
        return values.stream().map(StringJPA::getValue).collect(Collectors.toList());
    }
}
