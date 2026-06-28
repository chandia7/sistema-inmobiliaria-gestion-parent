package com.example.ms_pedidos.service;

import com.example.ms_pedidos.model.Pedido;
import com.example.ms_pedidos.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository repository; // Simulamos la base de datos

    @InjectMocks
    private PedidoService service; // El servicio real

    private Pedido pedidoNuevo;

    @BeforeEach
    void setUp() {
        // Preparamos un pedido BÁSICO, sin estado ni fecha (eso lo debe poner el servicio)
        pedidoNuevo = new Pedido();
        pedidoNuevo.setIdUsuario(100L);
        pedidoNuevo.setIdPropiedad(200L);
        pedidoNuevo.setMontoFinal(new BigDecimal("150000.00"));
    }

    @Test
    void testCrearPedido_AsignaEstadoYFecha() {
        // ARRANGE: preparar mocks
        // Truco Mockito: Le decimos que cuando intente guardar, simplemente devuelva el mismo objeto que recibió
        when(repository.save(any(Pedido.class))).thenAnswer(i -> i.getArguments()[0]);

        // ACT: ejecutar método
        Pedido resultado = service.crearPedido(pedidoNuevo);

        // ASSERT: verificar resultado esperado
        assertNotNull(resultado, "El pedido devuelto no debe ser nulo");
        
        // ¡Aquí probamos la regla de negocio!
        assertEquals(Pedido.EstadoPedido.CREADO, resultado.getEstado(), "El servicio debe inyectar el estado CREADO automáticamente");
        assertNotNull(resultado.getFechaCreacion(), "El servicio debe inyectar la fecha de creación automáticamente");

        // VERIFY: comprobar que el servicio efectivamente llamó al repositorio
        verify(repository).save(any(Pedido.class));
    }

    /* * CASO HIPOTÉTICO DE FALLA PARA QA: 
     * Si un desarrollador borra la línea "pedido.setEstado(EstadoPedido.CREADO);" 
     * en el PedidoService.java, este test fallará lanzando un error en el assertEquals 
     * porque el estado llegará nulo a la base de datos.
     */
}