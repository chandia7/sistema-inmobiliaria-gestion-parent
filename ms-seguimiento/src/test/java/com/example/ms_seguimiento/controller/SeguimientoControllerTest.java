package com.example.ms_seguimiento.controller;

import com.example.ms_seguimiento.model.Seguimiento;
import com.example.ms_seguimiento.service.SeguimientoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SeguimientoController.class)
class SeguimientoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SeguimientoService service;

    @Test
    void testRegistrar_Exito() throws Exception {
        // ARRANGE: preparar datos y mocks
        // Preparamos la respuesta que nos dará el servicio simulado
        Seguimiento seguimientoMock = new Seguimiento(
                1L, 
                100L, 
                Seguimiento.EstadoSeguimiento.PEDIDO_CREADO, 
                "Iniciando el seguimiento del pedido", 
                LocalDateTime.now()
        );

        // Simulamos que al recibir cualquier objeto, el servicio devuelve nuestro mock
        when(service.registrarSeguimiento(any(Seguimiento.class))).thenReturn(seguimientoMock);

        // Armamos el JSON con los campos obligatorios del modelo (idPedido, estado, descripcion)
        String requestJson = "{"
                + "\"idPedido\": 100, "
                + "\"estado\": \"PEDIDO_CREADO\", "
                + "\"descripcion\": \"Iniciando el seguimiento del pedido\""
                + "}";

        // ACT: ejecutar endpoint
        mockMvc.perform(post("/api/seguimiento")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                
        // ASSERT: verificar resultado esperado
                .andExpect(status().isCreated()) // Esperamos HTTP 201
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("PEDIDO_CREADO"))
                .andExpect(jsonPath("$.descripcion").value("Iniciando el seguimiento del pedido"));

        // VERIFY: comprobar llamadas al mock
        verify(service).registrarSeguimiento(any(Seguimiento.class));
    }

    /* * CASO HIPOTÉTICO DE FALLA PARA QA: 
     * Si el Frontend envía el estado en minúsculas (ej. "pedido_creado") 
     * o un texto que no existe en el Enum (ej. "CANCELADO"), 
     * el controlador rechazará la petición con un error HTTP 400 Bad Request 
     * porque fallará la conversión al Enum EstadoSeguimiento.
     */
}
