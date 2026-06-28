package com.example.ms_notificaciones.service;

import com.example.ms_notificaciones.model.Notificacion;
import com.example.ms_notificaciones.repository.NotificacionRepository;
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
class NotificacionServiceTest {

    @Mock
    private NotificacionRepository repository; // Simulamos la base de datos

    @InjectMocks
    private NotificacionService service; // El servicio real

    private Notificacion notificacionNueva;

    @BeforeEach
    void setUp() {
        // Preparamos el correo SIN fecha y SIN estado
        notificacionNueva = new Notificacion();
        notificacionNueva.setDestinatario("alejandro@duoc.cl");
        notificacionNueva.setAsunto("Bienvenida");
        notificacionNueva.setMensaje("Gracias por registrarte en el sistema inmobiliario");
    }

    @Test
    void testEnviarNotificacion_AsignaEstadoYFecha() {
        // ARRANGE
        // Simulamos que al guardar, el repositorio devuelve el mismo objeto modificado
        when(repository.save(any(Notificacion.class))).thenAnswer(i -> i.getArguments()[0]);

        // ACT
        Notificacion resultado = service.enviarNotificacion(notificacionNueva);

        // ASSERT: ¡Validamos tus reglas de negocio!
        assertNotNull(resultado, "La notificación no debe ser nula");
        
        assertNotNull(resultado.getFechaEnvio(), "El servicio debe inyectar la fecha de envío automáticamente");
        assertEquals(Notificacion.EstadoNotificacion.ENVIADO, resultado.getEstado(), "El estado debe cambiar automáticamente a ENVIADO");

        // VERIFY: Verificamos que se llamó al repositorio para guardar el registro
        verify(repository).save(any(Notificacion.class));
    }

    /* * CASO HIPOTÉTICO DE FALLA PARA QA: 
     * Si alguien borra la línea "notificacion.setEstado(EstadoNotificacion.ENVIADO);" 
     * en el código original, este test fallará en el assertEquals, alertando a QA 
     * que los correos se están enviando pero quedando sin estado en la base de datos.
     */
}