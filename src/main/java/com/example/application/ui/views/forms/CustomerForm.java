package com.example.application.ui.views.forms;

import com.example.application.backend.data.entities.Customer;
import com.example.application.ui.crud.AbstractForm;
import com.vaadin.flow.component.textfield.TextField;

public class CustomerForm extends AbstractForm<Customer> {
  TextField name = new TextField("name");
  TextField email = new TextField("email");
  TextField phone = new TextField("phone");

  public CustomerForm() {
    super(Customer.class);
    getBinder().bindInstanceFields(this);
    add(name, email, phone);;
    addButtons();
  }

}
