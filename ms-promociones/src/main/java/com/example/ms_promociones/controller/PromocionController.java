package com.example.ms_promociones.controller;

import com.example.ms_promociones.entity.Promocion;
import com.example.ms_promociones.service.PromocionService; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid; 

import java.util.List;

// IMPORTS DE SWAGGER / OPENAPI
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/promociones")
@Tag(name = "Gestión de Promociones", description = "API para la administración de descuentos y campañas promocionales de la inmobiliaria")
public class PromocionController {

    private final PromocionService promocionService; 

    // Inyección por constructor (La mejor práctica)
    public PromocionController(PromocionService promocionService) { 
        this.promocionService = promocionService;
    }

    // Activamos @Valid y devolvemos 201 CREATED
    @Operation(summary = "Crear nueva promoción", description = "Registra una nueva campaña de descuento o promoción en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Promoción creada y registrada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error en la validación de los datos enviados")
    })
    @PostMapping
    public ResponseEntity<Promocion> crear(@Valid @RequestBody Promocion promocion) {
        return new ResponseEntity<>(promocionService.crearPromocion(promocion), HttpStatus.CREATED);
    }

    // Devolvemos 200 OK
    @Operation(summary = "Listar todas las promociones", description = "Obtiene un listado completo de todas las promociones registradas")
    @ApiResponse(responseCode = "200", description = "Lista de promociones obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Promocion>> listar() {
        return new ResponseEntity<>(promocionService.obtenerTodas(), HttpStatus.OK);
    }
}