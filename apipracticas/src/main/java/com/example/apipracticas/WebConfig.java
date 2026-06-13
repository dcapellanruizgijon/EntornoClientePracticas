package com.example.apipracticas;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Leer URL del frontend desde variable de entorno en producción
        String frontend = System.getenv("FRONTEND_URL");
        if (frontend == null || frontend.isBlank()) {
            // Rutas de desarrollo por defecto
            registry.addMapping("/**")
                    .allowedOrigins(
                        "https://ruix.iesruizgijon.es",
                        "http://localhost:8080",
                        "http://localhost:5432",
                        "http://localhost:5174",
                        "http://localhost:5173"
                    )
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true);
        } else {
            registry.addMapping("/api/**")
                    .allowedOrigins(frontend)
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true);
        }
    }
}