package com.example.ms_promociones.config;

import io.swagger.v3.oas.models.OpenAPI; // [cite: 229]
import io.swagger.v3.oas.models.info.Info; // [cite: 230]
import org.springframework.context.annotation.Bean; // [cite: 231]
import org.springframework.context.annotation.Configuration; // [cite: 232]

@Configuration // [cite: 233]
public class SwaggerConfig { // [cite: 234]

    @Bean // [cite: 235]
    public OpenAPI customOpenAPI() { // [cite: 236]
        return new OpenAPI() // [cite: 237]
                .info(new Info() // [cite: 238]
                        .title("API Promociones - Sistema de Gestión Inmobiliaria")
                        .version("1.0") // [cite: 240]
                        .description("Documentación oficial de la API para la administración de descuentos y campañas promocionales de la inmobiliaria."));
    }
}