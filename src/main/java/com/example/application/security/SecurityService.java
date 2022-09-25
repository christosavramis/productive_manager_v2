package com.example.application.security;

import com.example.application.backend.data.EmployeeRole;
import com.example.application.backend.data.entity.Employee;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class SecurityService {
    private static final String LOGOUT_SUCCESS_URL = "/";

    public Employee getAuthenticatedUser() {
        return (Employee) Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(Objects::nonNull)
                .map(Authentication::getPrincipal)
                .filter(o -> o instanceof Employee).orElse(null);
    }

    public void logout() {
        UI.getCurrent().getPage().setLocation(LOGOUT_SUCCESS_URL);
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(VaadinServletRequest.getCurrent().getHttpServletRequest(), null,
                null);
    }

    public boolean isAdmin() {
        return EmployeeRole.ADMIN.equals(getAuthenticatedUser().getRole());
    }
}
