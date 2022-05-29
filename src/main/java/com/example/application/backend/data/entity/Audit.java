package com.example.application.backend.data.entity;

import com.example.application.backend.data.AuditType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Lob;
import java.time.LocalDate;

@Setter @Getter @Builder @AllArgsConstructor @NoArgsConstructor
@Entity
public class Audit extends AbstractEntity {

    private String creator;

    private LocalDate timeCreated;

    @Lob
    private String message;

    private AuditType auditType;
}
