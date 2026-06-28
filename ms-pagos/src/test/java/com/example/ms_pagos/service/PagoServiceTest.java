package com.example.ms_pagos.service;

import com.example.ms_pagos.model.Pago;
import com.example.ms_pagos.repository.PagoRepository;
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
class PagoServiceTest {

    @Mock
    private PagoRepository repository; // Simulamos la BD

    @InjectMocks
    private PagoService service; // El servicio real

    private Pago pagoNuevo;

    @BeforeEach
    void setUp() {
        // Preparamos un pago sin fecha y sin estado
        pagoNuevo = new Pago();
        pagoNuevo.setIdPedido(100L);
        pagoNuevo.setMonto(new BigDecimal("50000.00")); // Monto válido mayor a 0
        pagoNuevo.setMetodoPago(Pago.MetodoPago.TRANSFERENCIA);
    }

    @Test
    void testProcesarPago_AprobadoExito() {
        // ARRANGE: preparar mocks
        // Le decimos al repositorio que devuelva exactamente lo que se le pide guardar
        when(repository.save(any(Pago.class))).thenAnswer(i -> i.getArguments()[0]);

        // ACT: ejecutar método
        Pago resultado = service.procesarPago(pagoNuevo);

        // ASSERT: verificar las reglas de negocio
        assertNotNull(resultado, "El pago devuelto no debe ser nulo");
        assertNotNull(resultado.getFechaPago(), "El servicio debe inyectar la fecha de pago automáticamente");
        assertEquals(Pago.EstadoPago.APROBADO, resultado.getEstado(), "El estado debe ser APROBADO porque el monto es mayor a 0");

        // VERIFY: comprobar que el servicio guardó en base de datos
        verify(repository).save(any(Pago.class));
    }

    /* * CASO HIPOTÉTICO DE FALLA PARA QA: 
     * Si un desarrollador por error cambia el operador ">" por "<" en la condición 
     * 'if (pago.getMonto().doubleValue() > 0)', este test fallará porque 
     * el pago sería RECHAZADO incorrectamente, protegiendo así la lógica financiera de la empresa.
     */
}