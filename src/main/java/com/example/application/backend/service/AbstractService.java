package com.example.application.backend.service;

import com.example.application.backend.data.AuditType;
import com.example.application.backend.data.entities.Audit;
import com.example.application.backend.exceptions.DuplicateFieldException;
import com.example.application.backend.exceptions.ReferentialIntegrityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public abstract class AbstractService<T>{
    private final JpaRepository<T, ?> jpaRepository;
    private @Autowired AuditService auditService;

    public AbstractService(JpaRepository<T, ?> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Transactional
    public T save(T object) throws DuplicateFieldException {
        T objectToBesaved = null;
        try {
            objectToBesaved = jpaRepository.save(object);
            auditService.save(new Audit(LocalDateTime.now(), "Saved " + object.toString() +" successfully", AuditType.INFO));
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateFieldException("Item already exist");
        }
        return objectToBesaved;
    }

    public List<T> findAll() {
        return jpaRepository.findAll();
    }

    public void delete(T object) {
        try {
            jpaRepository.delete(object);
        } catch (DataIntegrityViolationException exception) {
            throw new ReferentialIntegrityException("Please delete all references before deleting this");
        }
    }

    public void saveAll(List<T> objects) {
        objects.forEach(this::save);
    }

    @Transactional
    public T saveNoAudit(T object) throws DuplicateFieldException {
        T objectToBesaved = null;
        try {
            objectToBesaved = jpaRepository.save(object);
        } catch (DataIntegrityViolationException exception) {}
        return objectToBesaved;
    }
}
