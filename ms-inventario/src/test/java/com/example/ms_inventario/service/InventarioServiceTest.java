package com.example.ms_inventario.service;

import com.example.ms_inventario.model.Inventario;
import com.example.ms_inventario.repository.InventarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    private InventarioRepository repository; // Simulamos la base de datos

    @InjectMocks
    private InventarioService service; // El servicio real

    private Inventario inventarioNuevo;

    @BeforeEach
    void setUp() {
        // Preparamos un inventario BÁSICO, sin estado inicial
        inventarioNuevo = new Inventario();
        inventarioNuevo.setIdPropiedad(500L);
        inventarioNuevo.setStock(1);
    }

    @Test
    void testCrearInventario_ExitoAsignaEstado() {
        // ARRANGE: preparar mocks
        // 1. Simulamos que la propiedad NO existe en el inventario (para que pase la validación)
        when(repository.existsByIdPropiedad(500L)).thenReturn(false);
        // 2. Simulamos que al guardar, el repositorio devuelve el mismo objeto modificado
        when(repository.save(any(Inventario.class))).thenAnswer(i -> i.getArguments()[0]);

        // ACT: ejecutar método
        Inventario resultado = service.crearInventario(inventarioNuevo);

        // ASSERT: verificar reglas de negocio
        assertNotNull(resultado, "El inventario devuelto no debe ser nulo");
        // Comprobamos que el servicio inyectó el estado DISPONIBLE automáticamente
        assertEquals(Inventario.EstadoInventario.DISPONIBLE, resultado.getEstado(), "El estado inicial debe ser DISPONIBLE");

        // VERIFY: comprobar que el servicio llamó a las validaciones y al guardado
        verify(repository).existsByIdPropiedad(500L);
        verify(repository).save(any(Inventario.class));
    }

    /* * CASO HIPOTÉTICO DE FALLA PARA QA: 
     * Si un desarrollador borra la línea "inventario.setEstado(EstadoInventario.DISPONIBLE);" 
     * en el InventarioService, este test fallará en el assertEquals indicando que el 
     * estado devolvió "null" en lugar de "DISPONIBLE", alertando de inmediato a QA 
     * sobre una falla crítica en la lógica de registro.
     */
}