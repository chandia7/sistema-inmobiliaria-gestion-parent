package com.example.ms_pagos.controller;

import com.example.ms_pagos.model.Pago;
import com.example.ms_pagos.service.PagoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PagoController.class)
class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PagoService service;

    @Test
    void testProcesarPago_Exito() throws Exception {
        // ARRANGE: preparar datos y mocks
        // Preparamos el pago procesado que devolverá el servicio
        Pago pagoMock = new Pago(
                1L, 
                100L, 
                new BigDecimal("50000.00"), 
                Pago.MetodoPago.TRANSFERENCIA, 
                Pago.EstadoPago.APROBADO, 
                LocalDateTime.now()
        );

        // Simulamos que al procesar el pago, el servicio devuelve nuestro mock
        when(service.procesarPago(any(Pago.class))).thenReturn(pagoMock);

        // JSON simulado que el cliente (Frontend) enviaría
        // Ojo: Cumple con @NotNull, @Positive y los Enums
        String requestJson = "{"
                + "\"idPedido\": 100, "
                + "\"monto\": 50000.00, "
                + "\"metodoPago\": \"TRANSFERENCIA\""
                + "}";

        // ACT: ejecutar endpoint
        mockMvc.perform(post("/api/pagos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                
        // ASSERT: verificar resultado esperado
                .andExpect(status().isCreated()) // Esperamos HTTP 201
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.idPedido").value(100))
                .andExpect(jsonPath("$.monto").value(50000.00))
                .andExpect(jsonPath("$.metodoPago").value("TRANSFERENCIA"))
                .andExpect(jsonPath("$.estado").value("APROBADO"));

        // VERIFY: comprobar que el controlador llamó al servicio
        verify(service).procesarPago(any(Pago.class));
    }

    /* * CASO HIPOTÉTICO DE FALLA PARA QA: 
     * Si el usuario intenta enviar un pago malicioso con monto cero o negativo (ej. "monto": 0),
     * la anotación @Positive de la entidad interceptará el JSON antes de que llegue al servicio
     * y el controlador arrojará automáticamente un HTTP 400 Bad Request.
     */
}