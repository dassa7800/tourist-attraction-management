package com.example.tourist_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/attractions/admin/**").authenticated() // Protect admin routes
                        .anyRequest().permitAll() // Allow all requests without authentication
                )
                .formLogin(form -> form
                        .loginPage("/admin/login")  // Custom login page
                        .defaultSuccessUrl("/attractions/admin", true)  // Redirect to admin page after successful login
                        .permitAll()  // Allow everyone to see the login page
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")  // Redirect to home after logout
                        .permitAll()
                );
        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")  // Username for admin
                .password("password")  // Password for admin
                .roles("ADMIN")  // Admin role
                .build();
        return new InMemoryUserDetailsManager(admin);
    }
}
