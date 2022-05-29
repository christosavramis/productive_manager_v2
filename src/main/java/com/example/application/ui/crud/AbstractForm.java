package com.example.application.ui.crud;

import com.example.application.backend.data.AuditType;
import com.example.application.backend.data.entity.Audit;
import com.example.application.backend.service.AuditService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractForm<S> extends FormLayout {
    @Getter @Setter
    private S formObject;
    private Binder<S> binder;

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button close = new Button("Cancel");
    private List<SyncField<?>> syncFields = new ArrayList<>();
    public void addSyncField(SyncField syncField) {
        syncFields.add(syncField);
    }
    public @Autowired AuditService auditService;

    @SneakyThrows
    public AbstractForm(Class<S> classUsed) {
        binder = new BeanValidationBinder<>(classUsed);
        formObject = classUsed.getDeclaredConstructor().newInstance();
        addClassName(getClass().getSimpleName());
        setWidth("30em");
    }

    public void addButtons(){
        add(createButtonsLayout());
    }
    public void addButtons(VerticalLayout verticalLayout){
        verticalLayout.add(createButtonsLayout());
    }

    public Button getSaveButton() {
        return save;
    }

    public Binder<S> getBinder() {return binder;}
    public void setFormObjectSync(S formObject) {
        this.formObject = formObject;
        binder.readBean(formObject);
    }

    public void validateAndSave() {
        if (!isValidForSave())
            return;
        try {
            binder.writeBean(formObject);
            fireEvent(new SaveEvent(this, formObject));
        } catch (ValidationException e) {
            auditService.save(new Audit("Guest", LocalDate.now(), e.getMessage(), AuditType.ERROR), this.getClass());
        }
    }

    public boolean isValidForSave() {
        return true;
    }

    public void delete(){
        fireEvent(new DeleteEvent(this, formObject));
    }

    public void close(){
        fireEvent(new CloseEvent(this));
    }

    public void updateChildItemsOnStateChange() {
        syncFields.forEach(SyncField::run);
    };

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> delete());
        close.addClickListener(event -> close());

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    public static abstract class FormEvent extends ComponentEvent<AbstractForm> {
        private final Object formObject;

        public Object getFormObject() {
            return formObject;
        }

        protected FormEvent(AbstractForm source, Object formObject) {
            super(source, false);
            this.formObject = formObject;
        }

    }

    public static class SaveEvent extends FormEvent {
        SaveEvent(AbstractForm source, Object object) {
            super(source, object);
        }
    }

    public static class DeleteEvent extends FormEvent {
        DeleteEvent(AbstractForm source, Object object) {
            super(source, object);
        }

    }

    public static class CloseEvent extends FormEvent {
        CloseEvent(AbstractForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
