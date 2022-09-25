package com.example.application.ui.views;



import com.example.application.backend.data.entity.Employee;
import com.example.application.backend.data.entity.Tax;
import com.example.application.backend.service.EmployeeService;
import com.example.application.backend.service.TaxService;
import com.example.application.security.SecurityService;
import com.example.application.ui.MainLayout;
import com.example.application.ui.crud.AbstractCrudView;
import com.example.application.ui.views.forms.EmployeeForm;
import com.example.application.ui.views.forms.TaxForm;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;


@Route(value = "reports/employee", layout = MainLayout.class)
@PageTitle("Employee | Productive Manager")
@PermitAll
public class EmployeeView extends AbstractCrudView<Employee> {

    public EmployeeView(EmployeeService employeeService, SecurityService securityService) {
        super("employee", new EmployeeForm(), employeeService, Employee::new);
        setEnabledAddItemButton(securityService.isAdmin());
    }

    @Override
    public void configureGrid() {
        Grid<Employee> grid = new Grid<>(Employee.class);
        grid.addClassNames("product-grid");
        grid.setColumns();
        grid.addColumn(Employee::getName).setHeader("Name");
        grid.addColumn(Employee::getUsername).setHeader("Username");
        grid.addColumn(Employee::getRole).setHeader("Role");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.setSizeFull();
        super.configureGrid(grid);
    }
}
