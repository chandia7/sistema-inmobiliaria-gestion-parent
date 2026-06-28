package com.example.ms_promociones.service;

import com.example.ms_promociones.entity.Promocion;
import com.example.ms_promociones.repository.PromocionRepository;
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
class PromocionServiceTest {

    @Mock
    private PromocionRepository repository; // Simulamos la base de datos

    @InjectMocks
    private PromocionService service; // El servicio real que estamos probando

    private Promocion promocionNueva;

    @BeforeEach
    void setUp() {
        // Preparamos los datos de la promoción a probar
        promocionNueva = new Promocion();
        promocionNueva.setCodigo("INVIERNO2026");
        promocionNueva.setPorcentajeDescuento(15);
        promocionNueva.setActiva(true);
    }

    @Test
    void testCrearPromocion_Exito() {
        // ARRANGE: preparar datos y mocks
        // Simulamos que al hacer "save" en la BD, nos devuelve la promoción con un ID generado
        Promocion promocionGuardada = new Promocion(10L, "INVIERNO2026", 15, true);
        when(repository.save(any(Promocion.class))).thenReturn(promocionGuardada);

        // ACT: ejecutar método
        Promocion resultado = service.crearPromocion(promocionNueva);

        // ASSERT: verificar resultado esperado
        assertNotNull(resultado, "La promoción devuelta no debe ser nula");
        assertEquals(10L, resultado.getId(), "El ID debe ser el generado por la BD simulada");
        assertEquals("INVIERNO2026", resultado.getCodigo(), "El código debe coincidir");
        assertEquals(15, resultado.getPorcentajeDescuento(), "El descuento debe coincidir");

        // VERIFY: comprobar que el servicio llamó al repositorio para guardar
        verify(repository).save(any(Promocion.class));
    }

    /* * CASO HIPOTÉTICO DE FALLA PARA QA: 
     * Si alguien borra la llamada a "repository.save(promocion)" dentro de PromocionService 
     * y en su lugar devuelve null, el test fallará inmediatamente en el assertNotNull, 
     * detectando que la lógica de guardado fue destruida.
     */
}
