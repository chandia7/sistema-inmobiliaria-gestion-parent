package com.example.ms_usuarios.controller;

import com.example.ms_usuarios.modelo.Usuario;
import com.example.ms_usuarios.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario usuarioMock;

    @BeforeEach
    void setUp() {
        // Preparamos datos ficticios antes de cada test
        usuarioMock = new Usuario(
                1L, 
                "Alejandro", 
                "Chandía", 
                "+56912345678", 
                "alejandro@duoc.cl", 
                LocalDate.now(), 
                100L
        );
    }

    @Test
    void testCrearUsuario_Exito() throws Exception {
        // ARRANGE: preparar datos y mocks
        // Simulamos que el correo no existe en la base de datos
        when(service.existeCorreo("alejandro@duoc.cl")).thenReturn(false);
        // Simulamos que al guardar, el servicio devuelve nuestro usuarioMock
        when(service.guardarUsuario(any(Usuario.class))).thenReturn(usuarioMock);

        // ACT: ejecutar método o endpoint
        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioMock)))
                
        // ASSERT: verificar resultado esperado
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Alejandro"))
                .andExpect(jsonPath("$.email").value("alejandro@duoc.cl"));

        // VERIFY: comprobar llamadas al mock
        verify(service).existeCorreo("alejandro@duoc.cl");
        verify(service).guardarUsuario(any(Usuario.class));
    }

    /* * CASO HIPOTÉTICO DE FALLA PARA QA: 
     * Si el desarrollador olvida poner "HttpStatus.CREATED" en el controlador 
     * y deja "HttpStatus.OK", el ASSERT 'status().isCreated()' fallará.
     */
    @Test
    void testCrearUsuario_CorreoDuplicado() throws Exception {
        // ARRANGE: preparar datos y mocks
        // Simulamos que el correo SÍ existe en la base de datos
        when(service.existeCorreo("alejandro@duoc.cl")).thenReturn(true);

        // ACT: ejecutar endpoint
        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioMock)))
                
        // ASSERT: verificar resultado esperado
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("El correo alejandro@duoc.cl ya está registrado."));

        // VERIFY: comprobar llamadas al mock
        verify(service).existeCorreo("alejandro@duoc.cl");
        // Verificamos estrictamente que NUNCA se haya llamado al método guardarUsuario
        verify(service, org.mockito.Mockito.never()).guardarUsuario(any(Usuario.class));
    }
}