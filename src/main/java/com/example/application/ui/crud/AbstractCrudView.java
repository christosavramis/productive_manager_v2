package com.example.application.ui.crud;

import com.example.application.backend.data.AuditType;
import com.example.application.backend.data.entity.Audit;
import com.example.application.backend.exceptions.DuplicateFieldException;
import com.example.application.backend.exceptions.ReferentialIntegrityException;
import com.example.application.backend.service.AbstractService;
import com.example.application.backend.service.AuditService;
import com.example.application.ui.views.NotificationManager;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.function.Supplier;

public class AbstractCrudView<T> extends VerticalLayout {
    private T viewObject;
    private @Getter final AbstractForm<T> genericForm;
    private @Getter Grid<T> grid = new Grid<>();
    private final String toolbarName;
    private final AbstractService<T> abstractService;
    private final Supplier<T> supplier;
    private final @Getter HorizontalLayout content = new HorizontalLayout();
    private @Autowired NotificationManager notificationManager;
    private @Autowired AuditService auditService;

    public AbstractCrudView(String toolbarName, AbstractForm<T> genericForm, AbstractService<T> abstractService, Supplier<T> supplier) {
        this.toolbarName = toolbarName;
        this.genericForm = genericForm;
        this.abstractService = abstractService;
        this.supplier = supplier;

        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();
        add(getToolbar(), getContentLayout());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getToolbar() {
        Button addContactButton = new Button("Add "+toolbarName);
        addContactButton.addClickListener(click -> addItem());

        HorizontalLayout toolbar = new HorizontalLayout(addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void configureForm() {
        genericForm.addListener(AbstractForm.SaveEvent.class, e -> saveItem(e.getSource()));
        genericForm.addListener(AbstractForm.DeleteEvent.class, e -> deleteItem(e.getSource()));
        genericForm.addListener(AbstractForm.CloseEvent.class, e -> closeEditor());
    }

    private Component getContentLayout() {
        content.add(grid, genericForm);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void updateList() {
        grid.setItems(abstractService.findAll());
    }

    public void configureGrid() {
        grid.setSizeFull();
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> editItem(event.getValue()));
    }

    public void configureGrid(Grid<T> newGrid) {
        grid = newGrid;
        grid.setSizeFull();
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event -> editItem(event.getValue()));
    }

    private void addItem() {
        grid.asSingleSelect().clear();
        editItem(supplier.get());
    }

    public void editItem(T item) {
        if (item == null) {
            closeEditor();
        } else {
            genericForm.setFormObjectSync(item);
            displayTheFormFullScreen(true);
            genericForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void saveItem(AbstractForm<T> event) {
        try {
            abstractService.save(event.getFormObject());
            updateList();
            genericForm.updateChildItemsOnStateChange();
            auditService.save(new Audit("Guest", LocalDate.now(), "Saved item successfully", AuditType.INFO), this.getClass());
            closeEditor();
        } catch (DuplicateFieldException e) {
            auditService.save(new Audit("Guest", LocalDate.now(), e.getMessage(), AuditType.ERROR), this.getClass());
        }

    }

    private void deleteItem(AbstractForm<T> event) {
        try {
            abstractService.delete(event.getFormObject());
            updateList();
            genericForm.updateChildItemsOnStateChange();
            auditService.save(new Audit("Guest", LocalDate.now(), "deleted item successfully", AuditType.ERROR), this.getClass());
            closeEditor();
        } catch (ReferentialIntegrityException e) {
            auditService.save(new Audit("Guest", LocalDate.now(), e.getMessage(), AuditType.ERROR), this.getClass());
        }
    }

    private void closeEditor() {
        genericForm.setFormObjectSync(null);
        genericForm.setVisible(false);
        removeClassName("editing");
        displayTheFormFullScreen(false);
    }

    public void displayTheFormFullScreen(boolean showForm){
        /* nothing */
    }

}
