package com.example.ms_pedidos.controller;

import com.example.ms_pedidos.model.Pedido;
import com.example.ms_pedidos.service.PedidoService;
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

@WebMvcTest(PedidoController.class)
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService service;

    @Test
    void testCrearPedido_Exito() throws Exception {
        // ARRANGE: preparar datos y mocks
        // Preparamos el pedido simulado que devolverá el servicio
        Pedido pedidoMock = new Pedido();
        pedidoMock.setId(1L);
        pedidoMock.setIdUsuario(100L);
        pedidoMock.setIdPropiedad(200L);
        pedidoMock.setMontoFinal(new BigDecimal("150000.00"));
        pedidoMock.setEstado(Pedido.EstadoPedido.CREADO); // Estado inicial autogenerado
        pedidoMock.setFechaCreacion(LocalDateTime.now());

        // Simulamos que al guardar, devuelve nuestro mock
        when(service.crearPedido(any(Pedido.class))).thenReturn(pedidoMock);

        // JSON simulado (cumple con las validaciones @NotNull y @PositiveOrZero)
        String requestJson = "{"
                + "\"idUsuario\": 100, "
                + "\"idPropiedad\": 200, "
                + "\"montoFinal\": 150000.00"
                + "}";

        // ACT: ejecutar endpoint
        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                
        // ASSERT: verificar resultado esperado
                .andExpect(status().isCreated()) // Esperamos HTTP 201
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.idUsuario").value(100))
                .andExpect(jsonPath("$.idPropiedad").value(200))
                .andExpect(jsonPath("$.montoFinal").value(150000.00))
                .andExpect(jsonPath("$.estado").value("CREADO")); // Comprobamos que asigne el estado correcto

        // VERIFY: comprobar que el controlador llamó al servicio
        verify(service).crearPedido(any(Pedido.class));
    }

    /* * CASO HIPOTÉTICO DE FALLA PARA QA: 
     * Si el Frontend envía en el JSON un "montoFinal": -500.00 (negativo), 
     * el controlador rechazará la petición con HTTP 400 Bad Request 
     * gracias a tu excelente anotación financiera @PositiveOrZero en la entidad.
     */
}