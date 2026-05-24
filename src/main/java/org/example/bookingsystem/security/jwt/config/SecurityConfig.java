package org.example.bookingsystem.security.jwt.config;

import org.example.bookingsystem.security.jwt.filter.JwtAuthFilter;
import org.example.bookingsystem.security.jwt.service.MyUserDetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig = the doorman
 * Determines which doors require identification
 * Determines which doors are open to everyone
 * Stops unauthorized access to protected endpoints
 * How Spring Security should handle tokens
 * Which filters should be run
 * How errors should be handled
 */
@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final MyUserDetailsService myUserDetailsService;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, MyUserDetailsService myUserDetailsService, JwtAuthEntryPoint jwtAuthEntryPoint) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.myUserDetailsService = myUserDetailsService;
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
    }

    /*
     * Spring Security needs a PasswordEncoder to compare passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * The one who verifies users at login
     * The one who uses:
     * MyUserDetailsService to fetch the user
     * PasswordEncoder to compare passwords
     * This is used only at login, not during JWT validation
     * DaoAuthenticationProvider -> a built-in component in Spring Security that is responsible for:
     * to log in users
     * to fetch users from the database
     * to compare passwords
     * to throw an error if something is wrong
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(myUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /*
     * Spring Security builds the entire security chain here.
     * .csrf(csrf -> csrf.disable()) -> disable cookies because you are using JWT, not cookies.
     * .authorizeHttpRequests(auth -> auth etc. -> determines who needs a token to be used.
     * .sessionManagement(...) -> make the application stateless.
     * .authenticationProvider(...) -> is a part of the Spring Security library and is a Spring Security interface. Used for login.
     * .exceptionHandling(..) -> connects JwtAuthEntryPoint. Used for JWT-errors.
     * .addFilterBefore(...); -> connects JwtAuthFilter. Used for read token.
     * return http.build(); -> build and return the chain.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(
                csrf -> csrf.disable())
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers("/api/customers", "/api/customers/login")
                        .permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/api/customers/me").authenticated()
                        .anyRequest().authenticated())
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthEntryPoint))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
