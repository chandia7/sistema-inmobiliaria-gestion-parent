package com.example.ms_logistica.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Logística - Sistema de Gestión Inmobiliaria")
                        .version("1.0")
                        .description("Documentación oficial de la API para la gestión, control y actualización de los estados logísticos de los pedidos."));
    }
}