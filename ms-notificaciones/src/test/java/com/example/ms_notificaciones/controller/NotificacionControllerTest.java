package com.example.ms_notificaciones.controller;

import com.example.ms_notificaciones.model.Notificacion;
import com.example.ms_notificaciones.service.NotificacionService;
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

@WebMvcTest(NotificacionController.class)
class NotificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificacionService service;

    @Test
    void testEnviarNotificacion_Exito() throws Exception {
        // ARRANGE: preparamos el mock con los 6 datos EXACTOS de tu modelo
        Notificacion notifMock = new Notificacion(
                1L, 
                "alejandro@duoc.cl", 
                "Pago Aprobado", 
                "Tu pago fue aprobado", 
                LocalDateTime.now(), 
                Notificacion.EstadoNotificacion.ENVIADO
        );
        
        // CORRECCIÓN: usamos enviarNotificacion()
        when(service.enviarNotificacion(any(Notificacion.class))).thenReturn(notifMock);

        // JSON con los datos obligatorios (@NotBlank)
        String requestJson = "{"
                + "\"destinatario\": \"alejandro@duoc.cl\", "
                + "\"asunto\": \"Pago Aprobado\", "
                + "\"mensaje\": \"Tu pago fue aprobado\""
                + "}";

        // ACT & ASSERT
        mockMvc.perform(post("/api/notificaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.asunto").value("Pago Aprobado"))
                .andExpect(jsonPath("$.mensaje").value("Tu pago fue aprobado"));

        // VERIFY: verificamos con enviarNotificacion()
        verify(service).enviarNotificacion(any(Notificacion.class));
    }
}