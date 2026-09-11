package com.keystone.field_service.Security;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JWTUtil jwtUtil;

    public SecurityConfig(
            CustomUserDetailsService customUserDetailsService,
            JWTUtil jwtUtil) {

        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {

        return new JWTAuthenticationFilter(
                jwtUtil,
                customUserDetailsService
        );
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                Arrays.asList("http://localhost:3000")
        );

        configuration.setAllowedMethods(
                Arrays.asList(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "PATCH",
                        "OPTIONS"
                )
        );

        configuration.setAllowedHeaders(
                Arrays.asList("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http

            // JWT does not need CSRF
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // No HTTP session
            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            .authorizeHttpRequests(auth -> auth

                // Login and registration
                .requestMatchers("/api/auth/**")
                .permitAll()

                // Roles
                .requestMatchers("/roles")
                .permitAll()

                // Manager
                .requestMatchers("/api/manager/**")
                .hasRole("MANAGER")

                // Dispatcher
                .requestMatchers("/api/dispatcher/**")
                .hasRole("DISPATCHER")

                // Technician
                .requestMatchers("/api/technician/**")
                .hasRole("TECHNICIAN")

                // Customer
                .requestMatchers("/api/customer/**")
                .hasRole("CUSTOMER")

                // Work Orders
                .requestMatchers("/api/work-orders/**")
                .hasAnyRole("MANAGER", "DISPATCHER","CUSTOMER","TECHINICIAN")

                // All other requests
                .anyRequest()
                .authenticated()
            )

            .addFilterBefore(
                jwtAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class
            );
        

        return http.build();
    }
}