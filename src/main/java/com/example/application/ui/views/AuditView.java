package com.example.application.ui.views;



import com.example.application.backend.data.AuditType;
import com.example.application.backend.data.entity.Audit;
import com.example.application.backend.data.entity.Tax;
import com.example.application.backend.service.AuditService;
import com.example.application.backend.service.TaxService;
import com.example.application.ui.MainLayout;
import com.example.application.ui.crud.AbstractCrudView;
import com.example.application.ui.views.forms.TaxForm;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.persistence.Lob;
import java.time.LocalDate;

@Route(value = "reports/audit", layout = MainLayout.class)
@PageTitle("Audit | Productive Manager")
public class AuditView extends VerticalLayout {

    public AuditView(AuditService auditService) {
        setSizeFull();
        Grid<Audit> grid = new Grid<>(Audit.class);
        grid.addClassNames("product-grid");
        grid.setColumns("auditType", "creator", "timeCreated", "message");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.setSizeFull();
        grid.setItems(auditService.findAll());
        add(grid);
    }

    private String auditTypeToColor(AuditType auditType) {
        String color = "white";
        switch (auditType) {

            case WARN:
            case DEBUG:
                color = "yellow";
                break;
            case ERROR:
                color = "red";
                break;
            case INFO:
            case TRACE:
                color = "blue";
                break;
        }
        return color;
    }

}
