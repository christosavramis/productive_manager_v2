package com.example.application.backend.service;

import com.example.application.backend.data.entity.Employee;
import com.example.application.backend.repository.EmployeeRepository;
import com.example.application.security.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService extends AbstractService<Employee> implements UserDetailsService {
    private EmployeeRepository employeeRepository;
    private SecurityService securityService;

    public EmployeeService(EmployeeRepository repository, SecurityService securityService) {
        super(repository);
        this.employeeRepository = repository;
        this.securityService = securityService;
    }

    @Override
    public Employee save(Employee employee) {
        return super.save(employee);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findEmployeeByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user"));
        return employee;
    }

    @Override
    public List<Employee> findAll() {
        return super.findAll().stream()
                .filter(employee -> !employee.equals(securityService.getAuthenticatedUser()))
                .collect(Collectors.toList());
    }

}
