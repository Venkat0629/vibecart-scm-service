package com.nisum.vibe.cart.scm.config;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The {@code OpenApiConfig} class is a configuration class for setting up OpenAPI documentation
 * in the Spring application. It defines the API grouping and specifies the paths that should
 * be included in the generated API documentation. This configuration helps to organize and expose
 * the API endpoints for better visibility and usage.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Creates a bean of type {@link GroupedOpenApi} to configure the public API documentation.
     * This method sets up the API group named "vibe-cart-api" and includes all endpoints
     * matching the path pattern "/vibe-cart/**".
     *
     * @return a {@code GroupedOpenApi} instance for the public API group
     */
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("vibe-cart-api")
                .pathsToMatch("/vibe-cart/**")
                .build();
    }
}

