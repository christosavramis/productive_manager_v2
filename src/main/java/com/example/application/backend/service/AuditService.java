package com.example.application.backend.service;

import com.example.application.backend.data.entity.Audit;
import com.example.application.backend.data.entity.Employee;
import com.example.application.backend.exceptions.DuplicateFieldException;
import com.example.application.backend.repository.AuditRepository;
import com.example.application.backend.util.StringUtil;
import com.example.application.security.SecurityService;
import com.example.application.ui.views.NotificationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditService {
    private @Autowired NotificationManager notificationManager;
    private @Autowired SecurityService securityService;
    private AuditRepository auditRepository;
    public AuditService(AuditRepository repository) {
        this.auditRepository = repository;
    }

    public List<Audit> findAll() {
        return auditRepository.findAll();
    }

    public Audit save(Audit audit, Class<?> clazz) throws DuplicateFieldException {
        if (StringUtil.isEmptyOrNull(audit.getCreator())) {
            Employee employee = securityService.getAuthenticatedUser();
            audit.setCreator(employee != null ? employee.getName() : "Admin");
        }
        saveOnLogs(audit, clazz);
        notificationManager.notificationError(audit);
        return save(audit);
    }

    public Audit save(Audit audit) {
        if (StringUtil.isEmptyOrNull(audit.getCreator())) {
            Employee employee = securityService.getAuthenticatedUser();
            audit.setCreator(employee != null ? employee.getName() : "Admin");
        }
        return auditRepository.save(audit);
    }

    public void saveAll(List<Audit> objects) {
        objects.forEach(this::save);
    }

    private void saveOnLogs(Audit audit, Class<?> clazz) {
        Logger logger = LoggerFactory.getLogger(clazz);
        switch (audit.getAuditType()) {
            case INFO:
                logger.info(audit.getMessage());
                break;
            case WARN:
                logger.warn(audit.getMessage());
                break;
            case DEBUG:
                logger.debug(audit.getMessage());
                break;
            case ERROR:
                logger.error(audit.getMessage());
                break;
            case TRACE:
                logger.trace(audit.getMessage());
                break;
        }
    }
}
