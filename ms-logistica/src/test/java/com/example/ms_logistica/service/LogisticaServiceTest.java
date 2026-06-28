package com.example.ms_logistica.service;

import com.example.ms_logistica.model.Logistica;
import com.example.ms_logistica.repository.LogisticaRepository;
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
class LogisticaServiceTest {

    @Mock
    private LogisticaRepository repository; // Simulamos la base de datos

    @InjectMocks
    private LogisticaService service; // El servicio real que estamos probando

    private Logistica logisticaNueva;

    @BeforeEach
    void setUp() {
        // Preparamos los datos básicos, SIN estado y SIN fecha (eso lo pone el Service)
        logisticaNueva = new Logistica();
        logisticaNueva.setIdPedido(100L);
        logisticaNueva.setNotaria("Notaría San Miguel"); // Campo obligatorio en tu modelo
    }

    @Test
    void testCrearProceso_AsignaEstadoYFecha() {
        // ARRANGE: Usamos nuestro truco maestro para devolver el mismo objeto guardado
        when(repository.save(any(Logistica.class))).thenAnswer(i -> i.getArguments()[0]);

        // ACT: Ejecutamos la lógica de negocio
        Logistica resultado = service.crearProceso(logisticaNueva);

        // ASSERT: Validamos que las reglas de negocio se cumplan a la perfección
        assertNotNull(resultado, "El objeto logístico devuelto no debe ser nulo");
        assertNotNull(resultado.getFechaActualizacion(), "El servicio debe inyectar la fecha actual automáticamente");
        assertEquals(Logistica.EstadoLogistica.DOCUMENTACION_EN_REVISION, resultado.getEstado(), "El estado inicial debe ser DOCUMENTACION_EN_REVISION");

        // VERIFY: Comprobamos que el servicio intentó guardar en la BD
        verify(repository).save(any(Logistica.class));
    }

    /* * CASO HIPOTÉTICO DE FALLA PARA QA: 
     * Si alguien modifica tu LogisticaService y borra la línea 
     * "logistica.setEstado(EstadoLogistica.DOCUMENTACION_EN_REVISION);", 
     * el test fallará indicando que el registro se intentó crear sin un estado inicial válido.
     */
}