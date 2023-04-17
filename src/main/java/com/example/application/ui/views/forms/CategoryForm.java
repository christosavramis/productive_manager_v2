package com.example.application.ui.views.forms;

import com.example.application.backend.data.entities.Category;
import com.example.application.ui.crud.AbstractForm;
import com.vaadin.flow.component.textfield.TextField;

public class CategoryForm extends AbstractForm<Category> {
  TextField name = new TextField("name");

  public CategoryForm() {
    super(Category.class);
    getBinder().bindInstanceFields(this);
    add(name);
    addButtons();
  }

}
