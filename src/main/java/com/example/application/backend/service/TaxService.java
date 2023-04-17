package com.example.application.backend.service;

import com.example.application.backend.data.entities.Tax;
import com.example.application.backend.repository.TaxRepository;
import org.springframework.stereotype.Service;

@Service
public class TaxService extends AbstractService<Tax> {

    public TaxService(TaxRepository taxRepository) {
        super(taxRepository);
    }

}
