package com.nisum.vibe.cart.scm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The {@code WebConfig} class is a configuration class that implements the {@link WebMvcConfigurer} interface
 * to customize the web application's MVC configuration. This class specifically enables Cross-Origin Resource Sharing (CORS)
 * for the application, allowing frontend clients from different origins to access backend APIs.
 * The configuration specifies which origins, HTTP methods, and headers are allowed for CORS requests.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures CORS mappings for the application.
     * This method allows cross-origin requests from the specified frontend origin ("http://localhost:3000")
     * and permits the use of specified HTTP methods and headers. The configuration also allows credentials
     * such as cookies to be included in CORS requests.
     *
     * @param registry the {@link CorsRegistry} object used to configure CORS for the application
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply CORS settings to all endpoints
                .allowedOrigins("*") // Allow any origin
                .allowedMethods("*") // Allow any method (GET, POST, etc.)
                .allowedHeaders("*") // Allow any header
                .allowCredentials(false); // Do not allow credentials (like cookies)
    }

}