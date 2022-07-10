package com.example.application.backend.data.entity;

import com.example.application.backend.data.AuditType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Lob;
import java.time.LocalDateTime;

@Setter @Getter @Builder @AllArgsConstructor @NoArgsConstructor
@Entity
public class Audit extends AbstractEntity {

    private String creator;

    private LocalDateTime timeCreated;

    @Lob
    private String message;

    private AuditType auditType;


    public Audit(LocalDateTime timeCreated, String message, AuditType auditType) {
        this.timeCreated = timeCreated;
        this.message = message;
        this.auditType = auditType;
    }
}
