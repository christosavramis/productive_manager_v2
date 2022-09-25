package com.example.application.backend.data;

import com.example.application.backend.data.entity.Policy;
import com.example.application.backend.data.entity.StringJPA;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum InventoryPolicies {
    STOCK_POLICY(new Policy("Allow ordering out of stock",
            "NO", null,
            List.of(new StringJPA("NO"),
                    new StringJPA("WARNING"),
                    new StringJPA("YES"))));

    private final Policy policy;

}
