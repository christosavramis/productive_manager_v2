package com.example.application.backend.service;

import com.example.application.backend.data.InventoryPolicies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PolicyHelper {
    @Autowired
    PolicyService policyService;

    public boolean isStockPolicyNoOrWarning() {
        String value = policyService.getPolicyValue(InventoryPolicies.STOCK_POLICY.getPolicy());
        return value.equals("NO") || value.equals("WARNING");
    }

    public boolean isStockPolicyNo() {
        String value = policyService.getPolicyValue(InventoryPolicies.STOCK_POLICY.getPolicy());
        return value.equals("NO");
    }
}
