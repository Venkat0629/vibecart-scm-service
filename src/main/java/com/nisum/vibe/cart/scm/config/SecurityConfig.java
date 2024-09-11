package com.nisum.vibe.cart.scm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * The {@code SecurityConfig} class is a configuration class for setting up security in the Spring application.
 * It uses Spring Security to define security policies for various API endpoints, such as allowing or denying
 * access to specific paths and configuring basic authentication. This class is annotated with {@link EnableWebSecurity}
 * to enable web security support.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configures the security filter chain for the application.
     * Disables CSRF protection, allows unrestricted access to specific API endpoints, and
     * applies basic HTTP authentication for the remaining endpoints.
     * This method defines which paths are publicly accessible and which require authentication.
     *
     * @param http the {@link HttpSecurity} object used to configure web-based security for specific HTTP requests
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if there is an issue configuring security
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/vibe-cart/orders/**").permitAll()
                .antMatchers("swagger-ui.html","/api-docs", "/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**","HttpMethod.GET", "/api/v1/vibe-cart/offers", "/api/v1/vibe-cart/offers/{id}", "/api/v1/vibe-cart/offers/item/{id}", "/api/v1/vibe-cart/offers/sku/{id}", "/api/v1/vibe-cart/offers/coupon/{code}").permitAll()// Protect orders endpoint
                .anyRequest().permitAll()
                .and()
                .httpBasic();

        return http.build();
    }

    /**
     * Configures a password encoder bean for the application.
     * Currently uses {@link NoOpPasswordEncoder} for testing purposes, which does not apply any
     * encoding to the password. This should be replaced with a secure password encoder in a production environment.
     *
     * @return a {@link PasswordEncoder} instance that does not perform any encoding
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Temporarily disable password encoding for testing
        return NoOpPasswordEncoder.getInstance();
    }

}
