package com.example.application.backend.service;

import com.example.application.backend.data.EmployeeRole;
import com.example.application.backend.data.entities.Employee;
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
        Employee employee = securityService.getAuthenticatedUser();
        if (employee == null) {
            return List.of();
        }
        List<Employee> employees = super.findAll();

        if (EmployeeRole.USER.equals(employee.getRole())) {
            employees = employees.stream()
                    .filter(savedEmployee -> savedEmployee.equals(employee))
                    .collect(Collectors.toList());
        }

        return employees;
    }

}
