package com.example.application.ui.crud;

import com.vaadin.flow.data.provider.HasListDataView;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@AllArgsConstructor
@RequiredArgsConstructor
public class SyncField<T> {
    private final HasListDataView<T, ?> field;
    private final Supplier<List<T>> serviceMethod;
    private Predicate<? super T> filterOutput;

    public void run(){
        field.setItems(serviceMethod.get().stream().filter(filterOutput).collect(Collectors.toList()));
    }
}
