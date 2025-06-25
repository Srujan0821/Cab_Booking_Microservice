package com.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // Disable CSRF for stateless APIs
                .authorizeExchange(auth -> auth
                        // Allow public access to specific routes
                        .pathMatchers(
                                "/api/users/register",
                                "/api/users/login",
                                "/api/drivers/register",
                                "/api/drivers/login"
                        ).permitAll()

                        // All other routes require authentication
                        .anyExchange().authenticated()
                );

        return http.build();
    }
}