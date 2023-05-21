package com.example.application.ui.views.forms;

import com.example.application.backend.data.EmployeeRole;
import com.example.application.backend.data.entities.Employee;
import com.example.application.ui.crud.AbstractForm;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;

public class EmployeeForm extends AbstractForm<Employee> {
  TextField name = new TextField("name");
  TextField username = new TextField("username");
  private final ComboBox<EmployeeRole> role = new ComboBox("role");
  PasswordField password = new PasswordField("password");


  public EmployeeForm() {
    super(Employee.class);
    getBinder().bindInstanceFields(this);
    role.setItems(List.of(EmployeeRole.values()));
    add(name, username, password, role);

    addButtons();
  }

  @Override
  public void setFormObjectSync(Employee formObject) {
    super.setFormObjectSync(formObject);
  }

}
