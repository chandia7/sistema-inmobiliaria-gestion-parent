package com.example.ms_reportes.service;

import com.example.ms_reportes.DTOs.PagoDTO;
import com.example.ms_reportes.DTOs.PedidoDTO;
import com.example.ms_reportes.client.PagoClient;
import com.example.ms_reportes.client.PedidoClient;
import com.example.ms_reportes.model.Reporte;
import com.example.ms_reportes.repository.ReporteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReporteServiceTest {

    @Mock
    private ReporteRepository reporteRepository;

    @Mock
    private PagoClient pagoClient; // Simulamos conexión al ms-pagos

    @Mock
    private PedidoClient pedidoClient; // Simulamos conexión al ms-pedidos

    @InjectMocks
    private ReporteService service; // El servicio real

    private List<PedidoDTO> pedidosMock;
    private List<PagoDTO> pagosMock;

    @BeforeEach
    void setUp() {
        // Preparamos 3 Pedidos: 2 "COMPLETADO" y 1 "PENDIENTE"
        pedidosMock = Arrays.asList(
                new PedidoDTO(1L, "COMPLETADO", LocalDateTime.now()),
                new PedidoDTO(2L, "COMPLETADO", LocalDateTime.now()),
                new PedidoDTO(3L, "PENDIENTE", LocalDateTime.now())
        );

        // Preparamos 2 Pagos: 1 "APROBADO" (por 500.00) y 1 "RECHAZADO" (por 200.00)
        pagosMock = Arrays.asList(
                new PagoDTO(1L, new BigDecimal("500.00"), "APROBADO"),
                new PagoDTO(2L, new BigDecimal("200.00"), "RECHAZADO")
        );
    }

    @Test
    void testGenerarReporte_CalculosCorrectos() {
        // ARRANGE: preparar mocks
        // Cuando el servicio pregunte a Feign, le pasamos nuestras listas trampeadas
        when(pedidoClient.obtenerPedidos()).thenReturn(pedidosMock);
        when(pagoClient.obtenerPagos()).thenReturn(pagosMock);
        
        // Magia de Mockito: Cuando intente guardar, le decimos que devuelva el mismo reporte que armó
        when(reporteRepository.save(any(Reporte.class))).thenAnswer(i -> i.getArguments()[0]);

        // ACT: ejecutar método
        Reporte resultado = service.generarReporte();

        // ASSERT: verificar resultado esperado (¡AQUÍ SE PRUEBAN LAS MATEMÁTICAS!)
        assertNotNull(resultado, "El reporte no debe ser nulo");
        assertEquals(3, resultado.getTotalPedidos(), "Debe contar 3 pedidos en total");
        assertEquals(2L, resultado.getPedidosCompletados(), "Debe contar solo los 2 pedidos completados");
        assertEquals(new BigDecimal("500.00"), resultado.getTotalVentas(), "Debe sumar solo los 500 del pago aprobado (ignorar el rechazado)");

        // VERIFY: comprobar que el servicio efectivamente consultó a los otros microservicios
        verify(pedidoClient).obtenerPedidos();
        verify(pagoClient).obtenerPagos();
        verify(reporteRepository).save(any(Reporte.class));
    }

    /* * CASO HIPOTÉTICO DE FALLA PARA QA: 
     * Si un desarrollador altera el '.filter(p -> p.getEstado().equalsIgnoreCase("COMPLETADO"))' 
     * y por error pone otra palabra o quita el filtro, el test fallará en el ASSERT 
     * indicando que se están contando pedidos erróneos en el reporte de métricas.
     */
}
