package com.example.application.ui.views;

import com.example.application.backend.data.entity.Category;
import com.example.application.backend.data.entity.Customer;
import com.example.application.backend.service.CategoryService;
import com.example.application.backend.service.CustomerService;
import com.example.application.ui.MainLayout;
import com.example.application.ui.crud.AbstractCrudView;
import com.example.application.ui.views.forms.CategoryForm;
import com.example.application.ui.views.forms.CustomerForm;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "ordering/customer", layout = MainLayout.class)
@PageTitle("Customer | Productive Manager")
public class CustomerView extends AbstractCrudView<Customer> {

    public CustomerView(CustomerService customerService) {
        super("Customer", new CustomerForm(), customerService, Customer::new);
    }

    @Override
    public void configureGrid() {
        Grid<Customer> grid = new Grid<>(Customer.class);
        grid.addClassNames("product-grid");
        grid.setSizeFull();
        grid.setColumns();
        grid.addColumn(Customer::getId).setHeader("Id");
        grid.addColumn(Customer::getName).setHeader("Name");
        grid.addColumn(Customer::getEmail).setHeader("Email");
        grid.addColumn(Customer::getPhone).setHeader("Phone");
        grid.addColumn(customer -> customer.getOrders().size()).setHeader("Total Orders");
        super.configureGrid(grid);
    }
}
