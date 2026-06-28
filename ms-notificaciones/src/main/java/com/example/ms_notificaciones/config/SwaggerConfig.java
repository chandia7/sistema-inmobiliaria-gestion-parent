package com.example.ms_notificaciones.config;

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
                        .title("API Notificaciones - Sistema de Gestión Inmobiliaria")
                        .version("1.0")
                        .description("Documentación oficial de la API para el envío, gestión y seguimiento de correos y alertas a los usuarios."));
    }
}
