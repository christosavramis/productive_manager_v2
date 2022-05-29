package com.example.application.backend.service;

import com.example.application.backend.data.entity.Audit;
import com.example.application.backend.exceptions.DuplicateFieldException;
import com.example.application.backend.repository.AuditRepository;
import com.example.application.ui.views.NotificationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditService extends AbstractService<Audit> {
    private @Autowired NotificationManager notificationManager;
    public AuditService(AuditRepository repository) {
        super(repository);
    }

    public Audit save(Audit audit, Class<?> clazz) throws DuplicateFieldException {
        saveOnLogs(audit, clazz);
        return save(audit);
    }

    @Override
    public Audit save(Audit audit) throws DuplicateFieldException {
        notificationManager.notificationError(audit);
        return super.save(audit);
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
