package com.example.blog_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public  class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());
        http.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(auth -> auth

                // -----------------------------
                // Public Access
                // -----------------------------
                .requestMatchers("api/auth/**").permitAll()

                // Anyone can READ posts/comments/categories (GET only)
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/posts/**").permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/comments/**").permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/categories/**").permitAll()

                // -----------------------------
                // ADMIN ONLY routes
                // -----------------------------
                .requestMatchers("/api/users/**").hasRole("ADMIN")

                // -----------------------------
                // SECURED Write Access (POST, PUT, DELETE)
                // Authenticated users only
                // -----------------------------
                .requestMatchers("/api/posts/**").authenticated()
                .requestMatchers("/api/comments/**").authenticated()
                .requestMatchers("/api/categories/**").authenticated()

                // Catch-all
                .anyRequest().denyAll()
        );

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}