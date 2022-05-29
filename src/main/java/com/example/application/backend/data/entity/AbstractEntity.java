package com.example.application.backend.data.entity;

import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class AbstractEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private int version;

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractEntity that = (AbstractEntity) o;
        return version == that.version &&
                Objects.equals(id, that.id);
    }
}
