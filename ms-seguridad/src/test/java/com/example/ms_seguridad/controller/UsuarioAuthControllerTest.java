package com.example.ms_seguridad.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import com.example.ms_seguridad.dto.RegistroRequestDto;
import com.example.ms_seguridad.service.UsuarioAuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

// Si en el futuro quieres quitar la línea amarilla del VSCode, 
// cambia @MockBean por @MockitoBean e importa: 
// import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioAuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class UsuarioAuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioAuthService service;

    @Test
    void testRegistrar_Exito() throws Exception {
        // ARRANGE: preparar datos y mocks
        String mensajeRespuesta = "Usuario registrado con éxito";
        
        when(service.registrarUsuario(any(RegistroRequestDto.class))).thenReturn(mensajeRespuesta);

        // JSON simulado AHORA CON TODOS LOS CAMPOS OBLIGATORIOS
        String requestJson = "{"
                + "\"nombre\":\"Alejandro\", "
                + "\"apellido\":\"Chandía\", "
                + "\"email\":\"alejandro@duoc.cl\", "
                + "\"password\":\"secreta123\", "
                + "\"rol\":\"USER\""
                + "}";

        // ACT: ejecutar endpoint
        mockMvc.perform(post("/api/auth/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                
        // ASSERT: verificar resultado esperado
                .andExpect(status().isCreated()) // Esperamos HTTP 201
                .andExpect(content().string(mensajeRespuesta)); // Esperamos el texto exacto

        // VERIFY: comprobar llamadas al mock
        verify(service).registrarUsuario(any(RegistroRequestDto.class));
    }

    /* * CASO HIPOTÉTICO DE FALLA PARA QA: 
     * Si el desarrollador olvida poner el '@RequestBody' en el parámetro del controlador, 
     * el DTO llegará vacío al servicio. En la prueba esto podría arrojar un HTTP 400 Bad Request 
     * porque las validaciones de Spring rechazarán el JSON mal procesado.
     */
}