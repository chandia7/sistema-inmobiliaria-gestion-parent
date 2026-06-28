package com.example.ms_promociones.controller;

import com.example.ms_promociones.entity.Promocion;
import com.example.ms_promociones.service.PromocionService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PromocionController.class)
class PromocionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PromocionService service;

    @Test
    void testCrearPromocion_Exito() throws Exception {
        // ARRANGE: preparar datos y mocks
        // Preparamos la respuesta ficticia del servicio
        Promocion promocionMock = new Promocion(1L, "VERANO2026", 20, true);

        // Simulamos que al guardar cualquier promoción, nos devuelve nuestro mock
        when(service.crearPromocion(any(Promocion.class))).thenReturn(promocionMock);

        // JSON simulado de la petición con datos válidos
        String requestJson = "{"
                + "\"codigo\": \"VERANO2026\", "
                + "\"porcentajeDescuento\": 20, "
                + "\"activa\": true"
                + "}";

        // ACT: ejecutar endpoint
        mockMvc.perform(post("/api/promociones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                
        // ASSERT: verificar resultado esperado
                .andExpect(status().isCreated()) // Esperamos HTTP 201
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.codigo").value("VERANO2026"))
                .andExpect(jsonPath("$.porcentajeDescuento").value(20))
                .andExpect(jsonPath("$.activa").value(true));

        // VERIFY: comprobar que el controlador llamó al servicio
        verify(service).crearPromocion(any(Promocion.class));
    }

    /* * CASO HIPOTÉTICO DE FALLA PARA QA: 
     * Si el Frontend envía en el JSON un "porcentajeDescuento": 150 (superando el 100%), 
     * el controlador rechazará la petición automáticamente con HTTP 400 Bad Request 
     * debido a la anotación @Max(100) en la entidad Promocion.
     */
}
