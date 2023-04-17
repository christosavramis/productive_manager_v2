package com.example.application.ui.views.forms;

import com.example.application.backend.data.OrderStatus;
import com.example.application.backend.service.PolicyHelper;
import com.example.application.backend.data.entities.*;
import com.example.application.backend.service.OrderService;
import com.example.application.backend.util.StringUtil;
import com.example.application.ui.crud.AbstractForm;
import com.example.application.ui.views.OrderView;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.ValidationException;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class OrderForm extends AbstractForm<Order> {
//  private final ComboBox<OrderStatus> status = new ComboBox("status");
  private final ComboBox<Customer> customer = new ComboBox<>("customer");
  private final Supplier<List<Product>> productSupplier;
  private final Supplier<List<Customer>> customerSupplier;
  private final Span orderStatus = new Span();
  private final Span timeOrderedSpan = new Span();
  private final Span idSpan = new Span();
  private final Span quantityWarningSpan = new Span();
  private ProductOrderMaker productOrderMaker;

  private final Button payButton;
  private boolean isDisabled = false;
  private OrderService orderService;

  private final Button cancelButton;
  private PolicyHelper policyHelper;
  public OrderForm(Supplier<List<Product>> productSupplier, Supplier<List<Customer>> customerSupplier, OrderService orderService, PolicyHelper policyHelper) {
    super(Order.class, true);
    this.setWidth(30, Unit.PERCENTAGE);
    this.customerSupplier = customerSupplier;
    this.productSupplier = productSupplier;
    this.orderService = orderService;
    this.policyHelper = policyHelper;
    getBinder().bindInstanceFields(this);
    customer.setItems(customerSupplier.get());
    customer.setItemLabelGenerator(Customer::getName);

    HorizontalLayout details = new HorizontalLayout();
    details.setWidth(100, Unit.PERCENTAGE);
    details.add(new VerticalLayout(timeOrderedSpan, idSpan), new VerticalLayout(orderStatus, customer));
    VerticalLayout detailsWrapper = new VerticalLayout(new H2("Order details"), details);
    detailsWrapper.setSizeFull();
    OrderStatus savedOrderStatus = getFormObject() != null ? getFormObject().getStatus() : OrderStatus.NEW;

    boolean isNew = OrderStatus.NEW.equals(savedOrderStatus);
    payButton = new Button("Pay Order", e -> payOrder());
    payButton.getStyle().set("background-color", "green").set("color", "black");
    payButton.setVisible(isNew);

    boolean isPaid = OrderStatus.PAID.equals(savedOrderStatus);
    cancelButton = new Button("Cancel Order", e -> cancelOrder());
    cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
    cancelButton.setVisible(isPaid);
    setButtonVisibility(!(isNew || isPaid));

    productOrderMaker = new ProductOrderMaker(productSupplier, newQuantity -> {
        calculateWarnings(getFormObject(), isPaid);
    });
    quantityWarningSpan.getStyle().set("color", "red");
    add(detailsWrapper, productOrderMaker, quantityWarningSpan, payButton, cancelButton);
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
        OrderStatus orderStatus = formObject.getStatus();
        boolean isPaid = OrderStatus.PAID.equals(orderStatus);

        isDisabled =  isPaid || OrderStatus.CANCELLED.equals(orderStatus);
        productOrderMaker.disableCartButtons(isDisabled);
        payButton.setEnabled(!isDisabled);
        customer.setEnabled(!isDisabled);

        this.orderStatus.setText("Status: " + orderStatus.toString());
        payButton.setVisible(OrderStatus.NEW.equals(orderStatus));
        cancelButton.setVisible(isPaid);
        setButtonVisibility(!isDisabled);
        calculateWarnings(formObject, OrderStatus.PAID.equals(orderStatus));

    } else {
        setButtonVisibility(true);
        quantityWarningSpan.setText("");
    }
    productOrderMaker.syncWithOrder(formObject != null ? formObject.getProducts() : new ArrayList<>());
  }

  public void payOrder() {
      try {
          getBinder().writeBean(getFormObject());
      } catch (ValidationException e) {
          throw new RuntimeException(e);
      }
      Notification.show("Order paid!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
      fireEvent(new OrderView.PayEvent(this, getFormObject()));
  }

  public void cancelOrder() {
      try {
          getBinder().writeBean(getFormObject());
      } catch (ValidationException e) {
          throw new RuntimeException(e);
      }
      Notification.show("Order cancelled!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
      fireEvent(new OrderView.CancelEvent(this, getFormObject()));
  }

  public void calculateWarnings(Order order, boolean isPaid) {
      String warnings = null;
      boolean shouldCalcWarnings = policyHelper.isStockPolicyNoOrWarning();
      boolean shouldBlockPayment = policyHelper.isStockPolicyNo();
      if (shouldCalcWarnings) {
          warnings = orderService.getQuantityWarning(order);
      }
      boolean warningsExist = warnings != null;

      payButton.setEnabled(!isPaid && !(shouldBlockPayment && warningsExist));
      quantityWarningSpan.setText(warnings);
  }
}



