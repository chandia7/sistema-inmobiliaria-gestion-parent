package com.example.ms_notificaciones.controller;

import com.example.ms_notificaciones.model.Notificacion;
import com.example.ms_notificaciones.service.NotificacionService; // <-- Corregido aquí
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
@RequestMapping("/api/notificaciones")
@Tag(name = "Sistema de Notificaciones", description = "API para el envío, gestión y seguimiento de notificaciones a usuarios")
public class NotificacionController {

    private final NotificacionService notificacionService; // <-- Corregido aquí

    public NotificacionController(NotificacionService notificacionService) { // <-- Corregido aquí
        this.notificacionService = notificacionService;
    }

    // Activamos @Valid y aseguramos el retorno 201 CREATED
    @Operation(summary = "Enviar nueva notificación", description = "Registra y procesa el envío de una nueva notificación a un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notificación enviada y registrada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error en la validación de los datos de la notificación")
    })
    @PostMapping
    public ResponseEntity<Notificacion> enviar(@Valid @RequestBody Notificacion notificacion) {
        return new ResponseEntity<>(notificacionService.enviarNotificacion(notificacion), HttpStatus.CREATED);
    }

    // Aseguramos el retorno 200 OK para las consultas
    @Operation(summary = "Listar todas las notificaciones", description = "Obtiene un historial completo de todas las notificaciones registradas en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de notificaciones obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Notificacion>> listar() {
        return new ResponseEntity<>(notificacionService.listarNotificaciones(), HttpStatus.OK);
    }

    @Operation(summary = "Buscar notificaciones por destinatario", description = "Filtra el historial de notificaciones enviadas a un correo electrónico específico")
    @ApiResponse(responseCode = "200", description = "Búsqueda de notificaciones por destinatario realizada con éxito")
    @GetMapping("/destinatario/{email}")
    public ResponseEntity<List<Notificacion>> porDestinatario(@PathVariable String email) {
        return new ResponseEntity<>(notificacionService.obtenerPorDestinatario(email), HttpStatus.OK);
    }

    @Operation(summary = "Buscar notificaciones por estado", description = "Filtra el historial de notificaciones según su estado (ej. ENVIADA, PENDIENTE, ERROR)")
    @ApiResponse(responseCode = "200", description = "Búsqueda de notificaciones por estado realizada con éxito")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Notificacion>> porEstado(@PathVariable String estado) {
        return new ResponseEntity<>(notificacionService.obtenerPorEstado(estado), HttpStatus.OK);
    }
}