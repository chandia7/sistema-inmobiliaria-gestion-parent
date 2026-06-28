package com.example.ms_reportes.controller;

import com.example.ms_reportes.model.Reporte;
import com.example.ms_reportes.service.ReporteService; // <-- Corregido a singular, sin la "s"
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// IMPORTS DE SWAGGER / OPENAPI
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/reportes")
@Tag(name = "Generación de Reportes", description = "API para la creación y consulta de informes de rendimiento, ventas y métricas de la inmobiliaria")
public class ReporteController {

    private final ReporteService reporteService; // <-- Corregido a singular

    public ReporteController(ReporteService reporteService) { // <-- Corregido a singular
        this.reporteService = reporteService;
    }

    // 🔥 Generar reporte en tiempo real
    @Operation(summary = "Generar nuevo reporte", description = "Recopila los datos actuales del sistema y genera un reporte consolidado en tiempo real")
    @ApiResponse(responseCode = "201", description = "Reporte generado y guardado exitosamente")
    @PostMapping("/generar")
    public ResponseEntity<Reporte> generarReporte() {
        return new ResponseEntity<>(reporteService.generarReporte(), HttpStatus.CREATED);
    }

    // 📊 Historial
    @Operation(summary = "Listar historial de reportes", description = "Obtiene un listado completo de todos los reportes generados previamente")
    @ApiResponse(responseCode = "200", description = "Lista de reportes obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Reporte>> listar() {
        return new ResponseEntity<>(reporteService.listarReportes(), HttpStatus.OK);
    }

    // 🔍 Detalle
    @Operation(summary = "Buscar reporte por ID", description = "Obtiene el detalle de un reporte específico mediante su identificador único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte encontrado"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Reporte> obtener(@PathVariable Long id) {
        return new ResponseEntity<>(reporteService.obtenerReporte(id), HttpStatus.OK);
    }
}
