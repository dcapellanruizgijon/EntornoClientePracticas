package com.example.apipracticas;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // /** para cubrir todas las rutas
                .allowedOrigins(
                    "https://ruix.iesruizgijon.es",
                    "http://localhost:8080",
                    "http://localhost:5432",
                    "http://localhost:5174",
                    "http://localhost:5173"
                ) // permitir peticiones desde los orígenes listados
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}