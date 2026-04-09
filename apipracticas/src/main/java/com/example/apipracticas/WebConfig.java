package com.example.apipracticas;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  //    /** para cubrir todas las rutas
                .allowedOrigins("https://ruix.iesruizgijon.es") //para permitir peticiones desde otro servidor
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") 
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}