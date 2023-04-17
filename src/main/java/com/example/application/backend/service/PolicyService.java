package com.example.application.backend.service;

import com.example.application.backend.data.entities.Policy;
import com.example.application.backend.repository.PolicyRepository;
import org.springframework.stereotype.Service;


@Service
public class PolicyService extends  AbstractService<Policy> {
    private PolicyRepository policyRepository;
    public PolicyService(PolicyRepository repository) {
        super(repository);
        this.policyRepository = repository;
    }

    public String getPolicyValue(Policy policy) {
        return policyRepository.findAll()
                .stream()
                .filter(policy2 -> policy2.getName().equals(policy.getName()))
                .findFirst()
                .map(Policy::getUserSelectedValue)
                .orElse(null);
    }
}
