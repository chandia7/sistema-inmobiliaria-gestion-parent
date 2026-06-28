package com.example.ms_usuarios.service;

import com.example.ms_usuarios.modelo.Usuario;
import com.example.ms_usuarios.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repo; // Simulamos la base de datos

    @InjectMocks
    private UsuarioService service; // El servicio real que estamos probando

    private Usuario usuarioNuevo;

    @BeforeEach
    void setUp() {
        // Preparamos un usuario SIN fecha de registro (null)
        usuarioNuevo = new Usuario(
                1L,
                "Alejandro",
                "Chandía",
                "+56912345678",
                "alejandro@duoc.cl",
                null, // <-- Fecha nula a propósito
                100L
        );
    }

    @Test
    void testGuardarUsuario_AsignaFechaExito() {
        // ARRANGE: preparar datos y mocks
        // Simulamos que al hacer "save" en BD, simplemente nos devuelve el mismo usuario
        when(repo.save(any(Usuario.class))).thenReturn(usuarioNuevo);

        // ACT: ejecutar método
        Usuario resultado = service.guardarUsuario(usuarioNuevo);

        // ASSERT: verificar resultado esperado
        assertNotNull(resultado, "El usuario guardado no debe ser nulo");
        assertNotNull(resultado.getFechaRegistro(), "La fecha de registro NO debe ser nula después de pasar por el servicio");
        assertEquals(LocalDate.now(), resultado.getFechaRegistro(), "La fecha asignada debe ser la fecha actual (hoy)");

        // VERIFY: comprobar llamadas al mock
        verify(repo).save(any(Usuario.class)); // Verificamos que se llamó al repositorio para guardar
    }

    /* * CASO HIPOTÉTICO DE FALLA PARA QA: 
     * Si el desarrollador borra el bloque 'if(usuario.getFechaRegistro() == null)' 
     * dentro del UsuarioService, el test fallará en el ASSERT 'assertNotNull' 
     * diciendo que la fecha de registro llegó nula.
     */
}