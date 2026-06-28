package com.example.ms_seguridad.service;

import com.example.ms_seguridad.dto.RegistroRequestDto;
import com.example.ms_seguridad.dto.UsuarioDto;
import com.example.ms_seguridad.entity.UsuarioAuth;
import com.example.ms_seguridad.feign.UsuarioFeignClient;
import com.example.ms_seguridad.repository.UsuarioAuthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioAuthServiceTest {

    @Mock
    private UsuarioAuthRepository repository; // Simulamos la BD

    @Mock
    private UsuarioFeignClient feignClient; // Simulamos la llamada al otro microservicio

    @Mock
    private PasswordEncoder passwordEncoder; // Simulamos el encriptador

    @InjectMocks
    private UsuarioAuthService service; // El servicio real

    private RegistroRequestDto requestDto;
    private UsuarioAuth authGuardado;

    @BeforeEach
    void setUp() {
        // 1. Preparamos los datos de entrada
        requestDto = new RegistroRequestDto();
        requestDto.setNombre("Alejandro");
        requestDto.setApellido("Chandía");
        requestDto.setEmail("alejandro@duoc.cl");
        requestDto.setPassword("secreta123");
        requestDto.setRol("USER");

        // 2. Preparamos lo que devolverá la BD al guardar
        authGuardado = new UsuarioAuth();
        authGuardado.setId(100L); // ID autogenerado
        authGuardado.setEmail("alejandro@duoc.cl");
        authGuardado.setPassword("hash_muy_seguro_123");
        authGuardado.setRol("USER");
    }

    @Test
    void testRegistrarUsuario_ExitoCompleto() {
        // ARRANGE: preparar mocks
        // Cuando el servicio intente encriptar "secreta123", le decimos que devuelva un hash
        when(passwordEncoder.encode("secreta123")).thenReturn("hash_muy_seguro_123");
        
        // Cuando intente guardar en la BD, le devolvemos nuestro objeto con el ID 100L
        when(repository.save(any(UsuarioAuth.class))).thenReturn(authGuardado);

        // ACT: ejecutar método
        String resultado = service.registrarUsuario(requestDto);

        // ASSERT: verificar resultado esperado
        assertEquals("Usuario y credenciales creados exitosamente", resultado);

        // VERIFY: comprobar que el servicio realmente usó sus herramientas
        verify(passwordEncoder).encode("secreta123"); // Verificamos que se encriptó la clave
        verify(repository).save(any(UsuarioAuth.class)); // Verificamos que se guardó en BD
        verify(feignClient).crearUsuario(any(UsuarioDto.class)); // ¡Verificamos que se llamó a Feign!
    }

    /* * CASO HIPOTÉTICO DE FALLA PARA QA: 
     * Si un desarrollador por error borra la línea "feignClient.crearUsuario(usuarioDto);" 
     * el test fallará en el último VERIFY, alertando a QA de que los microservicios dejaron de comunicarse.
     */
}
