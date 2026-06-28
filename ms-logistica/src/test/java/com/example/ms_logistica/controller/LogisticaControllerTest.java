package com.example.ms_logistica.controller;

import com.example.ms_logistica.model.Logistica;
import com.example.ms_logistica.service.LogisticaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LogisticaController.class)
class LogisticaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LogisticaService service;

    @Test
    void testCrearLogistica_Exito() throws Exception {
        // ARRANGE: Truco a prueba de balas para evitar errores de constructores.
        // Hacemos que el mock devuelva exactamente el mismo objeto Logistica que Spring armó desde el JSON.
        when(service.crearProceso(any(Logistica.class))).thenAnswer(i -> i.getArguments()[0]);

        // OJO ACÁ: En este JSON puse datos genéricos. 
        // Si tu modelo Logistica tiene candados @NotNull, asegúrate de que todos los campos 
        // obligatorios estén escritos aquí adentro, de lo contrario te dará HTTP 400.
        String requestJson = "{"
                + "\"idPedido\": 100, "
                + "\"direccionDestino\": \"Av. Providencia 1234\", "
                + "\"responsable\": \"Juan Perez\""
                + "}";

        // ACT & ASSERT: Probamos tu endpoint oficial "crear"
        mockMvc.perform(post("/api/logistica")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated()); // Esperamos tu HTTP 201

        // VERIFY: ¡Ahora sí llamamos al método real de tu servicio!
        verify(service).crearProceso(any(Logistica.class));
    }
}