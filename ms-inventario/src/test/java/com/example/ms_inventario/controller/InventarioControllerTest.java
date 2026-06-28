package com.example.ms_inventario.controller;

import com.example.ms_inventario.model.Inventario;
import com.example.ms_inventario.service.InventarioService;
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

@WebMvcTest(InventarioController.class)
class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventarioService service;

    @Test
    void testCrearInventario_Exito() throws Exception {
        // ARRANGE: preparar datos y mocks.
        Inventario inventarioMock = new Inventario(
                1L,
                200L, // idPropiedad
                1,    // stock
                Inventario.EstadoInventario.DISPONIBLE,
                0     // version
        );

        // Hacemos que el mock devuelva nuestro objeto preparado
        when(service.crearInventario(any(Inventario.class))).thenReturn(inventarioMock);

        // JSON simulado que cumple con los candados @NotNull y @Min(0)
        String requestJson = "{"
                + "\"idPropiedad\": 200, "
                + "\"stock\": 1, "
                + "\"estado\": \"DISPONIBLE\""
                + "}";

        // ACT: ejecutar método o endpoint.
        mockMvc.perform(post("/api/inventario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                
        // ASSERT: verificar resultado esperado.
                .andExpect(status().isCreated()) // Esperamos HTTP 201
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.idPropiedad").value(200))
                .andExpect(jsonPath("$.stock").value(1))
                .andExpect(jsonPath("$.estado").value("DISPONIBLE"));

        // VERIFY: comprobar llamadas al mock si corresponde.
        verify(service).crearInventario(any(Inventario.class));
    }
    
    /* * CASO HIPOTÉTICO DE FALLA PARA QA: 
     * Si el sistema de Frontend envía un JSON con el campo "stock": -5, 
     * el controlador frenará la petición automáticamente devolviendo HTTP 400 Bad Request
     * gracias a la anotación @Min(0) en la entidad Inventario. 
     * Si en el test esperáramos un 201, fallaría obteniendo un 400.
     */
}