package com.be.tapchi.pjtapchi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.be.tapchi.pjtapchi.jwt.JwtRequestFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF protection for REST APIs
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login").permitAll() // Allow access to login endpoint without authentication
                .requestMatchers("/admin/**").hasRole("ADMIN") // Only ADMINs can access /admin/**
                .requestMatchers("/user/**").hasRole("USER") // Only USERs can access /user/**
                .anyRequest().permitAll() // All other requests need to be authenticated
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No session management, stateless
            )
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter before UsernamePasswordAuthenticationFilter

        return http.build();
    }
    

}
