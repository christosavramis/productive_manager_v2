package com.example.application.ui.views;



import com.example.application.backend.data.AuditType;
import com.example.application.backend.data.entity.Audit;
import com.example.application.backend.service.AuditService;
import com.example.application.backend.util.StringUtil;
import com.example.application.ui.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;


@Route(value = "reports/audit", layout = MainLayout.class)
@PageTitle("Audit | Productive Manager")
@PermitAll
public class AuditView extends VerticalLayout {

    public AuditView(AuditService auditService) {
        setSizeFull();
        Grid<Audit> grid = new Grid<>(Audit.class);
        grid.addClassNames("product-grid");
        grid.setColumns();
        grid.addComponentColumn(product -> {
            Span colorSpan = new Span(product.getAuditType().name());
            colorSpan.getStyle().set("color", auditTypeToColor(product.getAuditType()));
            return colorSpan;
        }).setHeader("AuditType");
        grid.addColumn(Audit::getCreator).setHeader("Creator").setWidth("70px");
        grid.addColumn(audit -> StringUtil.formatDate(audit.getTimeCreated())).setHeader("TimeCreated");
        grid.addColumn(Audit::getMessage).setHeader("Message");
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
