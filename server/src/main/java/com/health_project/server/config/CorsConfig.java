package com.health_project.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

    @Configuration
    public class CorsConfig {

        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/api/**") // Apply CORS to all paths under /api/
                            .allowedOrigins("http://localhost:4200") // Allow requests from your Angular app's URL
                            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed HTTP methods
                            .allowedHeaders("*") // Allow all headers
                            .allowCredentials(true); // Allow sending cookies/auth headers (if applicable)
                }
            };
        }
    }


