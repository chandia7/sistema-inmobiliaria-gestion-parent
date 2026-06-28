package com.example.ms_inventario.controller;

import com.example.ms_inventario.model.Inventario;
import com.example.ms_inventario.service.InventarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid; // <-- Importante para el escudo

import java.util.List;

// IMPORTS DE SWAGGER / OPENAPI
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/inventario")
@Tag(name = "Inventario de Propiedades", description = "API para gestionar el estado y la disponibilidad de las propiedades (Reservar, Vender, Liberar)")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    // Activamos @Valid aquí y devolvemos 201 CREATED
    @Operation(summary = "Registrar nuevo inventario", description = "Crea el registro de disponibilidad inicial para una nueva propiedad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inventario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error en la validación de los datos enviados")
    })
    @PostMapping
    public ResponseEntity<Inventario> crear(@Valid @RequestBody Inventario inventario) {
        return new ResponseEntity<>(inventarioService.crearInventario(inventario), HttpStatus.CREATED);
    }

    // A partir de aquí devolvemos 200 OK para todas las consultas y actualizaciones
    @Operation(summary = "Listar todo el inventario", description = "Obtiene una lista completa con los estados de disponibilidad de todas las propiedades")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Inventario>> listar() {
        return new ResponseEntity<>(inventarioService.listarInventario(), HttpStatus.OK);
    }

    @Operation(summary = "Buscar inventario por ID de Propiedad", description = "Obtiene el estado actual y detalles de inventario de una propiedad específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro de inventario encontrado"),
            @ApiResponse(responseCode = "404", description = "Registro de inventario no encontrado")
    })
    @GetMapping("/{idPropiedad}")
    public ResponseEntity<Inventario> obtener(@PathVariable Long idPropiedad) {
        return new ResponseEntity<>(inventarioService.obtenerPorPropiedad(idPropiedad), HttpStatus.OK);
    }

    @Operation(summary = "Reservar propiedad", description = "Cambia el estado de una propiedad a RESERVADO, bloqueándola para otros clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propiedad reservada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Propiedad no encontrada")
    })
    @PutMapping("/reservar/{idPropiedad}")
    public ResponseEntity<Inventario> reservar(@PathVariable Long idPropiedad) {
        return new ResponseEntity<>(inventarioService.reservar(idPropiedad), HttpStatus.OK);
    }

    @Operation(summary = "Vender propiedad", description = "Cambia el estado de una propiedad a VENDIDO, finalizando su ciclo comercial")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propiedad vendida exitosamente"),
            @ApiResponse(responseCode = "404", description = "Propiedad no encontrada")
    })
    @PutMapping("/vender/{idPropiedad}")
    public ResponseEntity<Inventario> vender(@PathVariable Long idPropiedad) {
        return new ResponseEntity<>(inventarioService.vender(idPropiedad), HttpStatus.OK);
    }

    @Operation(summary = "Liberar propiedad", description = "Cambia el estado de una propiedad a DISPONIBLE, devolviéndola al mercado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propiedad liberada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Propiedad no encontrada")
    })
    @PutMapping("/liberar/{idPropiedad}")
    public ResponseEntity<Inventario> liberar(@PathVariable Long idPropiedad) {
        return new ResponseEntity<>(inventarioService.liberar(idPropiedad), HttpStatus.OK);
    }

    @Operation(summary = "Bloquear propiedad", description = "Cambia el estado de una propiedad a BLOQUEADO (ej. por mantenimiento o problemas legales)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propiedad bloqueada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Propiedad no encontrada")
    })
    @PutMapping("/bloquear/{idPropiedad}")
    public ResponseEntity<Inventario> bloquear(@PathVariable Long idPropiedad) {
        return new ResponseEntity<>(inventarioService.bloquear(idPropiedad), HttpStatus.OK);
    }
}
