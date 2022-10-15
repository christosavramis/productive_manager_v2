package com.example.application.ui.views;

import com.example.application.backend.data.OrderStatus;
import com.example.application.backend.data.PolicyHelper;
import com.example.application.backend.data.entity.Order;
import com.example.application.backend.service.CustomerService;
import com.example.application.backend.service.OrderService;
import com.example.application.backend.service.PolicyService;
import com.example.application.backend.service.ProductService;
import com.example.application.backend.util.StringUtil;
import com.example.application.ui.MainLayout;
import com.example.application.ui.crud.AbstractCrudView;
import com.example.application.ui.crud.AbstractForm;
import com.example.application.ui.views.forms.OrderForm;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;
import java.util.stream.Stream;


@Route(value = "inventory/orders", layout = MainLayout.class)
@PageTitle("Orders | Productive Manager")
@PermitAll
public class OrderView extends AbstractCrudView<Order> {
    private final OrderService orderService;
    public OrderView(OrderService orderService, ProductService productService, CustomerService customerService, PolicyHelper policyHelper) {
        super("Order", new OrderForm(productService::findAllEnabled, customerService::findAll, orderService, policyHelper), orderService, Order::new);
        this.orderService = orderService;
    }

    @Override
    public void configureGrid() {
        Grid<Order> grid = new Grid<>(Order.class);
        grid.addClassNames("product-grid");
        grid.setSizeFull();
        grid.setColumns();
        grid.addColumn(Order::getId).setHeader("Id");
        grid.addColumn(order -> StringUtil.formatDate(order.getTimeOrdered())).setHeader("TimeOrdered");
        grid.addColumn(Order::getStatus).setHeader("Status");
        grid.addComponentColumn(product -> {
            OrderStatus orderStatus = product.getStatus();
            Span colorSpan = new Span(orderStatus.name());
            colorSpan.getStyle().set("color", orderStatusToColor(orderStatus));
            return colorSpan;
        }).setHeader("AuditType");
        grid.addColumn(order -> StringUtil.formatPrice(order.getPrice())).setHeader("Price").setSortOrderProvider(price -> Stream.of(new QuerySortOrder("price", price)));;
        grid.addColumn(order -> order.getCustomer().getName()).setHeader("Customer").setSortOrderProvider(customer -> Stream.of(new QuerySortOrder("name", customer)));
        super.configureGrid(grid);

       super.getGenericForm().addListener(PayEvent.class, e -> payOrder(e.getSource()));
        super.getGenericForm().addListener(CancelEvent.class, e -> cancelOrder(e.getSource()));
    }

    private String orderStatusToColor(OrderStatus orderStatus) {
        String color = "black";
        switch (orderStatus) {
            case PAID:
                color = "Green";
                break;
            case CANCELLED:
                color = "Red";
                break;
            case NEW:
                color = "Yellow";
                break;
        }
        return color;
    }

    public void payOrder(AbstractForm<Order> event) {
        Order updatedOrder = orderService.pay(event.getFormObject());
        super.getGenericForm().setFormObjectSync(updatedOrder);
        super.closeEditor();
        updateList();
    };

    public void cancelOrder(AbstractForm<Order> event) {
        Order updatedOrder = orderService.cancel(event.getFormObject());
        super.getGenericForm().setFormObjectSync(updatedOrder);
        super.closeEditor();
        updateList();
    };

    public static class PayEvent extends AbstractForm.FormEvent {
        public PayEvent(AbstractForm source, Object object) {
            super(source, object);
        };
    }

    public static class CancelEvent extends AbstractForm.FormEvent {
        public CancelEvent(AbstractForm source, Object object) {
            super(source, object);
        };
    }

}
