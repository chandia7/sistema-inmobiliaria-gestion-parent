package com.example.ms_catalogo.controller;

import com.example.ms_catalogo.model.Catalogo;
import com.example.ms_catalogo.service.CatalogoService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CatalogoController.class)
class CatalogoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatalogoService service;

    @Test
    void testCrearPropiedad_Exito() throws Exception {
        // ARRANGE: preparar datos y mocks.
        // Hacemos que el mock devuelva exactamente el mismo objeto Catalogo que recibe
        when(service.crearPropiedad(any(Catalogo.class))).thenAnswer(i -> i.getArguments()[0]);

        // JSON simulado que cumple con todos los candados @NotBlank, @NotNull y @Positive
        String requestJson = "{"
                + "\"nombre\": \"Casa en la playa\", "
                + "\"descripcion\": \"Hermosa casa con vista al mar\", "
                + "\"ubicacion\": \"Viña del Mar\", "
                + "\"precio\": 150000000, "
                + "\"tipo\": \"CASA\", "
                + "\"metrosCuadrados\": 120, "
                + "\"habitaciones\": 3"
                + "}";

        // ACT: ejecutar método o endpoint.
        mockMvc.perform(post("/api/catalogo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                
        // ASSERT: verificar resultado esperado.
                .andExpect(status().isCreated()) // Esperamos HTTP 201
                .andExpect(jsonPath("$.nombre").value("Casa en la playa"))
                .andExpect(jsonPath("$.ubicacion").value("Viña del Mar"))
                .andExpect(jsonPath("$.precio").value(150000000))
                .andExpect(jsonPath("$.tipo").value("CASA"));

        // VERIFY: comprobar llamadas al mock si corresponde.
        verify(service).crearPropiedad(any(Catalogo.class));
    }

    /* * CASO HIPOTÉTICO DE FALLA PARA QA: 
     * Si el sistema de Frontend intenta registrar una propiedad enviando "precio": -500,
     * el controlador frenará la petición automáticamente devolviendo HTTP 400 Bad Request
     * gracias a la anotación @Positive de la entidad Catalogo. QA debería reportar que 
     * la protección financiera del precio funciona correctamente. Si el test esperara un 201, fallaría.
     */
}
