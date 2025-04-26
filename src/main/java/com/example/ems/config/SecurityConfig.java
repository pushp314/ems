package com.example.ems.config;

import com.example.ems.security.CustomUserDetailsService;
import com.example.ems.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS with custom configuration
                .csrf(csrf -> csrf.disable()) // Disable CSRF for API-based authentication (stateless)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login").permitAll() // Permit login endpoint
                        .requestMatchers("/auth/signup").permitAll() // Permit signup endpoint (if you have one)
                        
                        // Explicitly permit all employee-related endpoints
                        .requestMatchers("/employee").permitAll() // GET /employee
                        .requestMatchers("/employee/**").permitAll() // All employee-related POST, PUT, DELETE
                        .requestMatchers("/employee/{id}").permitAll() // GET /employee/{id}
                        .requestMatchers("/employee/{id}/update").permitAll() // PUT /employee/{id}/update
                        .requestMatchers("/employee/{id}/delete").permitAll() // DELETE /employee/{id}/delete
                        

                        .requestMatchers("/dashboard/admin").permitAll() // Allow access to admin dashboard
                        .requestMatchers("/dashboard/employee/**").permitAll() // Allow access to employee dashboard
                        .requestMatchers("/department/**").permitAll() // Permit all department related APIs
                        .requestMatchers("/api/dashboard/**").permitAll() // Permit dashboard-related APIs
                        .requestMatchers("/admin/**").permitAll() // Permit all admin APIs (if required)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // No HTTP session, use JWT for stateless authentication
                .userDetailsService(userDetailsService) // Custom user details service
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // JWT filter should be applied before UsernamePasswordAuthenticationFilter
                .build();
    }

    // CORS Configuration: Allow frontend to communicate with the backend
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true); // Allow credentials (cookies, JWT, etc.)
        configuration.addAllowedOrigin("http://localhost:5173"); // Allow only the frontend URL (React app) for CORS requests
        configuration.addAllowedHeader("*"); // Allow all headers
        configuration.addAllowedMethod("*"); // Allow all HTTP methods (GET, POST, PUT, DELETE, etc.)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply the CORS configuration to all endpoints
        return source;
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // Return the authentication manager
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCryptPasswordEncoder for password encryption
    }
}
