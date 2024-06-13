package com.cocu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置类
 */
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("*") // Allow all origins
                        .allowCredentials(true) // Allow credentials (cookies, authorization headers, etc.)
                        .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS") // Allow all methods including OPTIONS
                        .allowedHeaders("*") // Allow all headers
                        .exposedHeaders("Authorization") // Expose Authorization header
                        .maxAge(3600); // Cache preflight response for 1 hour
            }
        };
    }
}