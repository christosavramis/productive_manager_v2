package com.example.application.ui.views;

import com.example.application.backend.data.entity.Order;
import com.example.application.backend.data.entity.OrderProduct;
import com.example.application.backend.service.CustomerService;
import com.example.application.backend.service.OrderService;
import com.example.application.backend.service.ProductService;
import com.example.application.backend.util.StringUtil;
import com.example.application.ui.MainLayout;
import com.example.application.ui.crud.AbstractCrudView;
import com.example.application.ui.views.forms.OrderForm;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.stream.Stream;


@Route(value = "inventory/orders", layout = MainLayout.class)
@PageTitle("Orders | Productive Manager")
public class OrderView extends AbstractCrudView<Order> {

    public OrderView(OrderService orderService, ProductService productService, CustomerService customerService) {
        super("Order", new OrderForm(productService::findAll, customerService::findAll), orderService, Order::new);
    }

    @Override
    public void configureGrid() {
        Grid<Order> grid = new Grid<>(Order.class);
        grid.addClassNames("product-grid");
        grid.setSizeFull();
        grid.setColumns("id", "timeOrdered", "status");
        grid.addColumn(order -> StringUtil.formatPrice(order.getPrice())).setHeader("price").setSortOrderProvider(price -> Stream.of(new QuerySortOrder("price", price)));;
        grid.addColumn(order -> order.getCustomer().getName()).setHeader("customer").setSortOrderProvider(customer -> Stream.of(new QuerySortOrder("name", customer)));
        super.configureGrid(grid);
    }

}
