package com.example.tourist_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Spring Security configuration class to manage security settings for the application.
 * This class configures user authentication, URL access restrictions, and password encoding.
 */
@Configuration
public class SecurityConfig {

    // Inject the UserDetailsService to fetch user details from the database
    private final UserDetailsService userDetailsService;

    // Constructor-based Dependency Injection for UserDetailsService
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Configures HTTP security, including authorization rules, login/logout behavior, and CSRF.
     *
     * @param http HttpSecurity instance for configuring security.
     * @return SecurityFilterChain to handle HTTP security.
     * @throws Exception if configuration fails.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for simplicity (enable in production with proper CSRF tokens)
                .csrf(csrf -> csrf.disable())

                // Configure URL-based authorization rules
                .authorizeHttpRequests(auth -> auth
                        // Allow public access to Bootstrap, static images, templates, and registration
                        .requestMatchers(
                                "/bootstrap-5.3.2-dist/**",  // CSS and JS for Bootstrap
                                "/static.images/**",         // Image resources
                                "/templates/**",             // HTML templates
                                "/register",                 // Registration page
                                "/error"                     // Error page (no authentication required)
                        ).permitAll()   // Allow access to these without authentication

                        //.anyRequest().permitAll()  // Permit all requests without authentication

                        // Restrict admin URLs to users with ADMIN role
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Restrict user URLs to users with USER role
                        .requestMatchers("/user/**").hasRole("USER")

                        // Allow unrestricted access to home, login, and register pages
                        .requestMatchers("/", "/login", "/register").permitAll()

                        // Require authentication for any other request
                        .anyRequest().authenticated()
                )

                // Configure form login
                .formLogin(form -> form
                        .loginPage("/login")  // Custom login page
                        .defaultSuccessUrl("/user-home", true)  // Default URL after login
                        .successHandler(customAuthenticationSuccessHandler())  // Custom login success handler
                        .permitAll()  // Allow everyone to access the login page
                )

                // Configure logout behavior
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")  // Redirect to login page after logout
                        .invalidateHttpSession(true)  // Invalidate session to clear user data
                        .clearAuthentication(true)  // Clear authentication after logout
                        .permitAll()  // Allow everyone to access logout URL
                )

                // Set the UserDetailsService for authentication (loads users from DB)
                .userDetailsService(userDetailsService);

        // Build and return the SecurityFilterChain
        return http.build();
    }

    /**
     * Custom authentication success handler to redirect users based on their roles.
     * After successful login, admins are redirected to /admin-home, while users are redirected to /user-home.
     *
     * @return AuthenticationSuccessHandler for role-based redirection.
     */
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            // Check if the authenticated user has ADMIN role
            if (authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                response.sendRedirect("/admin-home");  // Redirect to admin home if admin
            } else {
                response.sendRedirect("/user-home");  // Redirect to user home if regular user
            }
        };
    }

    /**
     * BCrypt password encoder bean to hash passwords before storing in the database.
     * BCrypt automatically handles salting and is a secure way to hash passwords.
     *
     * @return BCryptPasswordEncoder instance for password encoding.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configure the global authentication manager to use UserDetailsService and password encoder.
     * This method links the user details service with the password encoder to handle user authentication.
     *
     * @param auth AuthenticationManagerBuilder to build authentication configuration.
     * @throws Exception if configuration fails.
     */
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
