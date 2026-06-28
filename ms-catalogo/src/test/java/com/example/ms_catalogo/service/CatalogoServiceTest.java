package com.example.ms_catalogo.service;

import com.example.ms_catalogo.model.Catalogo;
import com.example.ms_catalogo.repository.CatalogoRepository;
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
class CatalogoServiceTest {

    @Mock
    private CatalogoRepository repository; // Simulamos la BD

    @InjectMocks
    private CatalogoService service; // El servicio real

    private Catalogo catalogoNuevo;

    @BeforeEach
    void setUp() {
        // Preparamos los datos de una propiedad nueva
        catalogoNuevo = new Catalogo();
        catalogoNuevo.setNombre("Casa en la playa");
        catalogoNuevo.setUbicacion("Viña del Mar");
        catalogoNuevo.setPrecio(new BigDecimal("150000000"));
        catalogoNuevo.setTipo("CASA");
        catalogoNuevo.setMetrosCuadrados(120);
        catalogoNuevo.setHabitaciones(3);
    }

    @Test
    void testCrearPropiedad_Exito() {
        // ARRANGE: Simulamos que la BD guarda el objeto y le asigna el ID 10
        when(repository.save(any(Catalogo.class))).thenAnswer(i -> {
            Catalogo c = i.getArgument(0);
            c.setId(10L); // ID autogenerado simulado
            return c;
        });

        // ACT: Ejecutamos el método del servicio
        Catalogo resultado = service.crearPropiedad(catalogoNuevo);

        // ASSERT: Verificamos que los datos se mantengan y se asigne el ID
        assertNotNull(resultado, "La propiedad devuelta no debe ser nula");
        assertEquals(10L, resultado.getId(), "El servicio debe devolver la propiedad con el ID generado por la BD");
        assertEquals("Casa en la playa", resultado.getNombre(), "El nombre no debe ser alterado");

        // VERIFY: Comprobamos que el servicio efectivamente intentó guardar en la BD
        verify(repository).save(any(Catalogo.class));
    }

    /* * CASO HIPOTÉTICO DE FALLA PARA QA: 
     * Si un desarrollador por error borrara o comentara la línea "return catalogoRepository.save(catalogo);" 
     * en el Service, y devolviera null, este test fallaría de inmediato en el assertNotNull, 
     * evitando que el error llegue a producción y dejando la tienda vacía.
     */
}