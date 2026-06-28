package com.example.ms_pedidos.controller;

import com.example.ms_pedidos.model.Pedido;
import com.example.ms_pedidos.service.PedidoService;
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
@RequestMapping("/api/pedidos")
@Tag(name = "Gestión de Pedidos", description = "API para la creación, seguimiento y administración de pedidos de propiedades")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // Activamos @Valid y devolvemos 201 CREATED
    @Operation(summary = "Crear nuevo pedido", description = "Registra la intención de compra o arriendo de una propiedad, iniciando el flujo del pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error en la validación de los datos del pedido")
    })
    @PostMapping
    public ResponseEntity<Pedido> crear(@Valid @RequestBody Pedido pedido) {
        return new ResponseEntity<>(pedidoService.crearPedido(pedido), HttpStatus.CREATED);
    }

    // A partir de aquí, 200 OK para todas las consultas y acciones
    @Operation(summary = "Listar todos los pedidos", description = "Obtiene un historial completo de todos los pedidos registrados en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Pedido>> listar() {
        return new ResponseEntity<>(pedidoService.listarPedidos(), HttpStatus.OK);
    }

    @Operation(summary = "Buscar pedido por ID", description = "Obtiene los detalles de un pedido específico mediante su identificador único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtener(@PathVariable Long id) {
        return new ResponseEntity<>(pedidoService.obtenerPorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Buscar pedidos por Usuario", description = "Obtiene todos los pedidos asociados a un identificador de usuario específico")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos del usuario obtenida exitosamente")
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Pedido>> obtenerPorUsuario(@PathVariable Long idUsuario) {
        return new ResponseEntity<>(pedidoService.obtenerPorUsuario(idUsuario), HttpStatus.OK);
    }

    // Usamos PutMapping para actualizar el estado a cancelado
    @Operation(summary = "Cancelar pedido", description = "Modifica el estado de un pedido específico a CANCELADO, deteniendo su flujo comercial")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido cancelado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado para cancelar")
    })
    @PutMapping("/cancelar/{id}")
    public ResponseEntity<Pedido> cancelar(@PathVariable Long id) {
        return new ResponseEntity<>(pedidoService.cancelarPedido(id), HttpStatus.OK);
    }

    // Usamos PutMapping para actualizar el estado a completado
    @Operation(summary = "Completar pedido", description = "Modifica el estado de un pedido específico a COMPLETADO, marcando el cierre exitoso de la transacción")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido completado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado para completar")
    })
    @PutMapping("/completar/{id}")
    public ResponseEntity<Pedido> completar(@PathVariable Long id) {
        return new ResponseEntity<>(pedidoService.completarPedido(id), HttpStatus.OK);
    }
}