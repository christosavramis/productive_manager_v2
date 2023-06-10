package com.example.application.security;

import com.example.application.backend.service.EmployeeService;
import com.example.application.ui.views.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurityConfigurerAdapter {
    private @Autowired EmployeeService employeeService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Delegating the responsibility of general configurations
        // of http security to the super class. It is configuring
        // the followings: Vaadin's CSRF protection by ignoring
        // framework's internal requests, default request cache,
        // ignoring public views annotated with @AnonymousAllowed,
        // restricting access to other views/endpoints, and enabling
        // ViewAccessChecker authorization.
        // You can add any possible extra configurations of your own
        // here (the following is just an example):

        // http.rememberMe().alwaysRemember(false);

        super.configure(http);

        // This is important to register your login view to the
        // view access checker mechanism:
        setLoginView(http, LoginView.class);
    }

    /**
     * Allows access to static resources, bypassing Spring security.
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // Configure your static resources with public access here:
        web.ignoring().antMatchers(
                "/images/**"
        );

        // Delegating the ignoring configuration for Vaadin's
        // related static resources to the super class:
        super.configure(web);
    }

    /**
     * Demo UserDetailService which only provide two hardcoded
     * in memory users and their roles.
     * NOTE: This should not be used in real world applications.
     */

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

//    @Override
//    public void configure(WebSecurity web) {
//        web.ignoring().antMatchers(
//                // Client-side JS
//                "/VAADIN/**",
//
//                // the standard favicon URI
//                "/favicon.ico",
//
//                // the robots exclusion standard
//                "/robots.txt",
//
//                // web application manifest
//                "/manifest.webmanifest",
//                "/sw.js",
//                "/sw-runtime-resources-precache.js",
//                "/offline.html",
//
//                // icons and images
//                "/icons/**",
//                "/images/**",
//                "/styles/**",
//
//                // (development mode) H2 debugging console
//                "/h2-console/**");
//    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new PlainEncoder();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return  employeeService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    private class PlainEncoder extends BCryptPasswordEncoder {
        @Override
        public String encode(CharSequence rawPassword) {
            return rawPassword.toString();
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return rawPassword.toString().equals(encodedPassword);
        }
    }
}
