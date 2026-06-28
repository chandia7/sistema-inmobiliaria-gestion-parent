package com.example.ms_pagos.controller;

import com.example.ms_pagos.model.Pago;
import com.example.ms_pagos.service.PagoService;
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
@RequestMapping("/api/pagos")
@Tag(name = "Gestión de Pagos", description = "API para el procesamiento de transacciones y consulta de pagos asociados a los pedidos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    // Activamos @Valid y aseguramos 201 CREATED para cuando entra plata
    @Operation(summary = "Procesar nuevo pago", description = "Registra y procesa una transacción de pago para un pedido específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pago procesado y registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error en la validación de los datos del pago")
    })
    @PostMapping
    public ResponseEntity<Pago> procesar(@Valid @RequestBody Pago pago) {
        return new ResponseEntity<>(pagoService.procesarPago(pago), HttpStatus.CREATED);
    }

    // Puro 200 OK para las consultas
    @Operation(summary = "Listar todos los pagos", description = "Obtiene un registro histórico de todos los pagos procesados en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de pagos obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Pago>> listar() {
        return new ResponseEntity<>(pagoService.listarPagos(), HttpStatus.OK);
    }

    @Operation(summary = "Buscar pago por ID", description = "Obtiene los detalles de una transacción de pago específica mediante su identificador único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago encontrado"),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtener(@PathVariable Long id) {
        return new ResponseEntity<>(pagoService.obtenerPorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Buscar pago por ID de Pedido", description = "Obtiene el pago asociado a un número de pedido específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago encontrado para el pedido"),
            @ApiResponse(responseCode = "404", description = "No se encontró ningún pago asociado al pedido indicado")
    })
    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<Pago> obtenerPorPedido(@PathVariable Long idPedido) {
        return new ResponseEntity<>(pagoService.obtenerPorPedido(idPedido), HttpStatus.OK);
    }

    @Operation(summary = "Buscar pagos por estado", description = "Filtra el historial de pagos según su estado de transacción (ej. APROBADO, RECHAZADO, PENDIENTE)")
    @ApiResponse(responseCode = "200", description = "Búsqueda de pagos por estado realizada con éxito")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pago>> listarPorEstado(@PathVariable String estado) {
        return new ResponseEntity<>(pagoService.listarPorEstado(estado), HttpStatus.OK);
    }
}
