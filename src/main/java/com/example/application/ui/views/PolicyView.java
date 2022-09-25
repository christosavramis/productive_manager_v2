package com.example.application.ui.views;

import com.example.application.backend.data.entity.Policy;
import com.example.application.backend.data.entity.Tax;
import com.example.application.backend.service.PolicyService;
import com.example.application.ui.MainLayout;
import com.example.application.ui.crud.AbstractCrudView;
import com.example.application.ui.views.forms.PolicyForm;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;


@Route(value = "reports/setting", layout = MainLayout.class)
@PageTitle("Setting | Productive Manager")
@PermitAll
public class PolicyView extends AbstractCrudView<Policy> {

    public PolicyView(PolicyService PolicyService) {
        super("Policy", new PolicyForm(), PolicyService, Policy::new);
    }

    @Override
    public void configureGrid() {
        setSizeFull();
        Grid<Policy> grid = new Grid<>(Policy.class);
        grid.addClassNames("product-grid");
        grid.setSizeFull();
        grid.setColumns();
        grid.addColumn(Policy::getName).setHeader("Name");
        grid.addColumn(Policy::getUserSelectedValue).setHeader("Value");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        super.configureGrid(grid);
    }
}
