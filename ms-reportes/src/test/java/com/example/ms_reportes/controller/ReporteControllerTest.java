package com.example.ms_reportes.controller;

import com.example.ms_reportes.model.Reporte;
import com.example.ms_reportes.service.ReporteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReporteController.class)
class ReporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReporteService service;

    @Test
    void testGenerarReporte_Exito() throws Exception {
        // ARRANGE: preparar datos y mocks
        // Preparamos un reporte ficticio simulando que el servicio hizo los cálculos
        Reporte reporteMock = new Reporte(
                1L,
                new BigDecimal("150000.00"), // Total Ventas
                10,                          // Total Pedidos
                8L,                          // Pedidos Completados
                LocalDateTime.now()
        );

        // Simulamos que al llamar a generarReporte() se devuelve nuestro mock
        when(service.generarReporte()).thenReturn(reporteMock);

        // ACT: ejecutar endpoint
        // Nota: Este endpoint no recibe un @RequestBody, así que solo disparamos el POST vacío
        mockMvc.perform(post("/api/reportes/generar")
                .contentType(MediaType.APPLICATION_JSON))
                
        // ASSERT: verificar resultado esperado
                .andExpect(status().isCreated()) // Esperamos HTTP 201
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.totalPedidos").value(10))
                .andExpect(jsonPath("$.pedidosCompletados").value(8));

        // VERIFY: comprobar que el controlador efectivamente llamó al servicio
        verify(service).generarReporte();
    }

    /* * CASO HIPOTÉTICO DE FALLA PARA QA: 
     * Si el desarrollador por error cambia el mapeo del endpoint de @PostMapping a @GetMapping,
     * esta prueba fallará con un Error HTTP 405 (Method Not Allowed), alertando a QA de que
     * la ruta de generación de reportes fue alterada incorrectamente.
     */
}
