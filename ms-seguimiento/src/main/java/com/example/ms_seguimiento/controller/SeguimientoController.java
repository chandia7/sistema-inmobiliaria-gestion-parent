package com.example.ms_seguimiento.controller;

import com.example.ms_seguimiento.model.Seguimiento;
import com.example.ms_seguimiento.service.SeguimientoService;
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
@RequestMapping("/api/seguimiento")
@Tag(name = "Seguimiento de Pedidos", description = "API para registrar y consultar el historial de eventos y trazabilidad de un pedido")
public class SeguimientoController {

    private final SeguimientoService seguimientoService;

    public SeguimientoController(SeguimientoService seguimientoService) {
        this.seguimientoService = seguimientoService;
    }

    // Activamos @Valid y devolvemos 201 CREATED
    @Operation(summary = "Registrar nuevo evento de seguimiento", description = "Crea un nuevo registro o hito en la línea de tiempo de seguimiento de un pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento de seguimiento registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error en la validación de los datos enviados")
    })
    @PostMapping
    public ResponseEntity<Seguimiento> registrar(@Valid @RequestBody Seguimiento seguimiento) {
        return new ResponseEntity<>(seguimientoService.registrarSeguimiento(seguimiento), HttpStatus.CREATED);
    }

    // A partir de aquí, 200 OK
    @Operation(summary = "Listar todos los seguimientos", description = "Obtiene un historial global de todos los eventos de seguimiento registrados en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de seguimientos obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Seguimiento>> listar() {
        return new ResponseEntity<>(seguimientoService.listarSeguimientos(), HttpStatus.OK);
    }

    @Operation(summary = "Buscar evento por ID", description = "Obtiene los detalles de un evento de seguimiento específico mediante su identificador único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento de seguimiento encontrado"),
            @ApiResponse(responseCode = "404", description = "Evento de seguimiento no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Seguimiento> obtener(@PathVariable Long id) {
        return new ResponseEntity<>(seguimientoService.obtenerPorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Obtener historial por ID de Pedido", description = "Recupera la línea de tiempo completa y cronológica de todos los eventos asociados a un pedido específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Historial del pedido obtenido exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron eventos de seguimiento para el pedido indicado")
    })
    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<List<Seguimiento>> historial(@PathVariable Long idPedido) {
        return new ResponseEntity<>(seguimientoService.obtenerHistorial(idPedido), HttpStatus.OK);
    }
}