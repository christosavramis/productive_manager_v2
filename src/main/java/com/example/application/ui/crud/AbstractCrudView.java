package com.example.application.ui.crud;

import com.example.application.backend.data.AuditType;
import com.example.application.backend.data.entities.Audit;
import com.example.application.backend.exceptions.DuplicateFieldException;
import com.example.application.backend.exceptions.ReferentialIntegrityException;
import com.example.application.backend.service.AbstractService;
import com.example.application.backend.service.AuditService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.function.Supplier;

public class AbstractCrudView<T> extends VerticalLayout {
    @Getter
    private final AbstractForm<T> genericForm;
    private @Getter Grid<T> grid = new Grid<>();
    private final String toolbarName;
    private final transient AbstractService<T> abstractService;
    private final transient Supplier<T> supplier;
    private final @Getter HorizontalLayout content = new HorizontalLayout();
    @Autowired
    private transient AuditService auditService;
    private Button addNewItemButton;

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

    public void disableAddNewItemButton(){
        addNewItemButton.setVisible(false);
    }

    private HorizontalLayout getToolbar() {
        addNewItemButton = new Button("Add "+toolbarName);
        addNewItemButton.addClickListener(click -> addItem());

        HorizontalLayout toolbar = new HorizontalLayout(addNewItemButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void setEnabledAddItemButton(boolean enabled) {
        addNewItemButton.setEnabled(enabled);
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

    public void updateList() {
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
            auditService.save(new Audit(LocalDateTime.now(), "Saved item successfully", AuditType.INFO), this.getClass());
            closeEditor();
        } catch (DuplicateFieldException e) {
            auditService.save(new Audit(LocalDateTime.now(), e.getMessage(), AuditType.ERROR), this.getClass());
        }

    }

    private void deleteItem(AbstractForm<T> event) {
        try {
            abstractService.delete(event.getFormObject());
            updateList();
            genericForm.updateChildItemsOnStateChange();
            auditService.save(new Audit(LocalDateTime.now(), "deleted item successfully", AuditType.ERROR), this.getClass());
            closeEditor();
        } catch (ReferentialIntegrityException e) {
            auditService.save(new Audit(LocalDateTime.now(), e.getMessage(), AuditType.ERROR), this.getClass());
        }
    }

    protected void closeEditor() {
        genericForm.setFormObjectSync(null);
        genericForm.setVisible(false);
        removeClassName("editing");
        displayTheFormFullScreen(false);
    }

    public void displayTheFormFullScreen(boolean showForm){
        /* nothing */
    }

}
