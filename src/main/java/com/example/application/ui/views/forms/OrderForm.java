package com.example.application.ui.views.forms;

import com.example.application.backend.data.OrderStatus;
import com.example.application.backend.data.entity.*;
import com.example.application.backend.util.StringUtil;
import com.example.application.ui.crud.AbstractForm;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class OrderForm extends AbstractForm<Order> {
  private final ComboBox<OrderStatus> status = new ComboBox("status");
  private final ComboBox<Customer> customer = new ComboBox<>("customer");
  private final Supplier<List<Product>> productSupplier;
  private final Supplier<List<Customer>> customerSupplier;
  private final Span timeOrderedSpan = new Span();
  private final Span idSpan = new Span();
  private ProductOrderMaker productOrderMaker;


  public OrderForm(Supplier<List<Product>> productSupplier, Supplier<List<Customer>> customerSupplier) {
    super(Order.class);
    this.setWidth(30, Unit.PERCENTAGE);
    this.customerSupplier = customerSupplier;
    this.productSupplier = productSupplier;
    getBinder().bindInstanceFields(this);
    customer.setItems(customerSupplier.get());
    customer.setItemLabelGenerator(Customer::getName);
    status.setItems(List.of(OrderStatus.values()));

    HorizontalLayout details = new HorizontalLayout();
    details.setWidth(100, Unit.PERCENTAGE);
//    details.getStyle().set("background-color", "blue");
    details.add(new VerticalLayout(timeOrderedSpan, idSpan), new VerticalLayout(status, customer));
    VerticalLayout detailsWrapper = new VerticalLayout(new H2("Order details"), details);
    detailsWrapper.setSizeFull();

    productOrderMaker = new ProductOrderMaker(productSupplier.get());
    add(detailsWrapper, productOrderMaker);
    addButtons();
  }

  private void updateIdAndSpan(){
      if (getFormObject() != null) {
          timeOrderedSpan.setText("Timeordered: " + StringUtil.formatDate(getFormObject().getTimeOrdered()));
          idSpan.setText("Id: " + getFormObject().getId());
      }
  }

  @Override
  public void setFormObjectSync(Order formObject) {
    super.setFormObjectSync(formObject);
    if (formObject != null) {
        updateIdAndSpan();
    }
      productOrderMaker.syncWithOrder(formObject != null ? formObject.getProducts() : new ArrayList<>());
  }

}



