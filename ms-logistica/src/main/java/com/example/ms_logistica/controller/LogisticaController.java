package com.example.ms_logistica.controller;

import com.example.ms_logistica.model.Logistica;
import com.example.ms_logistica.service.LogisticaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid; // <-- Nuestro candado

import java.util.List;

// IMPORTS DE SWAGGER / OPENAPI
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/logistica")
@Tag(name = "Logística de Pedidos", description = "API para gestionar el estado y seguimiento logístico de los pedidos de propiedades")
public class LogisticaController {

    private final LogisticaService logisticaService;

    public LogisticaController(LogisticaService logisticaService) {
        this.logisticaService = logisticaService;
    }

    // Activamos @Valid y devolvemos 201 CREATED
    @Operation(summary = "Registrar proceso logístico", description = "Inicia un nuevo proceso de logística asociado a un pedido recién creado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Proceso logístico creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error en la validación de los datos enviados")
    })
    @PostMapping
    public ResponseEntity<Logistica> crear(@Valid @RequestBody Logistica logistica) {
        return new ResponseEntity<>(logisticaService.crearProceso(logistica), HttpStatus.CREATED);
    }

    // Devolvemos 200 OK
    @Operation(summary = "Actualizar estado logístico", description = "Modifica el estado actual de la logística de un pedido específico (ej. EN_PREPARACION, EN_RUTA)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado logístico actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Proceso logístico no encontrado para el pedido indicado")
    })
    @PutMapping("/{idPedido}")
    public ResponseEntity<Logistica> actualizarEstado(
            @PathVariable Long idPedido,
            @RequestParam String estado) {
        return new ResponseEntity<>(logisticaService.actualizarEstado(idPedido, estado), HttpStatus.OK);
    }

    // Devolvemos 200 OK
    @Operation(summary = "Buscar logística por ID de Pedido", description = "Obtiene los detalles y el historial de estado de la logística de un pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proceso logístico encontrado"),
            @ApiResponse(responseCode = "404", description = "Proceso logístico no encontrado")
    })
    @GetMapping("/{idPedido}")
    public ResponseEntity<Logistica> obtener(@PathVariable Long idPedido) {
        return new ResponseEntity<>(logisticaService.obtenerPorPedido(idPedido), HttpStatus.OK);
    }

    // Devolvemos 200 OK
    @Operation(summary = "Listar todos los procesos logísticos", description = "Obtiene un registro completo de todas las operaciones logísticas del sistema")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Logistica>> listar() {
        return new ResponseEntity<>(logisticaService.listarProcesos(), HttpStatus.OK);
    }
}