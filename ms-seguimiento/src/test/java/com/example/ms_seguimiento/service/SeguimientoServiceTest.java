package com.example.ms_seguimiento.service;

import com.example.ms_seguimiento.model.Seguimiento;
import com.example.ms_seguimiento.repository.SeguimientoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SeguimientoServiceTest {

    @Mock
    private SeguimientoRepository repository; // Simulamos la base de datos

    @InjectMocks
    private SeguimientoService service; // El servicio real que estamos probando

    private Seguimiento seguimientoNuevo;

    @BeforeEach
    void setUp() {
        // Preparamos un seguimiento SIN fecha de registro (null)
        seguimientoNuevo = new Seguimiento();
        seguimientoNuevo.setIdPedido(100L);
        seguimientoNuevo.setEstado(Seguimiento.EstadoSeguimiento.PEDIDO_CREADO);
        seguimientoNuevo.setDescripcion("Iniciando el seguimiento del pedido");
        seguimientoNuevo.setFechaRegistro(null); // <-- Fecha nula a propósito
    }

    @Test
    void testRegistrarSeguimiento_AsignaFechaExito() {
        // ARRANGE: preparar datos y mocks
        // Simulamos que al hacer "save" en la BD, nos devuelve el mismo objeto
        when(repository.save(any(Seguimiento.class))).thenReturn(seguimientoNuevo);

        // ACT: ejecutar método
        Seguimiento resultado = service.registrarSeguimiento(seguimientoNuevo);

        // ASSERT: verificar resultado esperado
        assertNotNull(resultado, "El seguimiento guardado no debe ser nulo");
        assertNotNull(resultado.getFechaRegistro(), "La fecha de registro NO debe ser nula después de pasar por el servicio");
        assertEquals(Seguimiento.EstadoSeguimiento.PEDIDO_CREADO, resultado.getEstado());

        // VERIFY: comprobar que el servicio llamó al repositorio para guardar
        verify(repository).save(any(Seguimiento.class));
    }

    /* * CASO HIPOTÉTICO DE FALLA PARA QA: 
     * Si un desarrollador por error borra la línea "seguimiento.setFechaRegistro(LocalDateTime.now());" 
     * en la clase SeguimientoService, el test fallará en el ASSERT 'assertNotNull' 
     * informando que la fecha llegó nula a la base de datos, lo cual rompería la trazabilidad del sistema.
     */
}