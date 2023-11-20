package com.autoya.autoya_api.autoya.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for Cross-Origin Resource Sharing (CORS) in the application.
 * Enables Web MVC and defines CORS mappings for the specified origins and HTTP methods.
 */
@Configuration
@EnableWebMvc
public class CorsConig implements WebMvcConfigurer {
    /**
     * Adds CORS mappings to the provided CorsRegistry.
     *
     * @param registry The CorsRegistry to which CORS mappings are added.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")    // Reemplaza con el origen de tu frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                 .allowCredentials(true);
    }

}
