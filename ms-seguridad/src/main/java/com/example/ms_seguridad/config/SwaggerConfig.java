package com.example.ms_seguridad.config;

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
                        .title("API Seguridad y Autenticación - Sistema de Gestión Inmobiliaria")
                        .version("1.0")
                        .description("Documentación oficial de la API para el registro de credenciales, autenticación y gestión de accesos seguros al sistema."));
    }
}