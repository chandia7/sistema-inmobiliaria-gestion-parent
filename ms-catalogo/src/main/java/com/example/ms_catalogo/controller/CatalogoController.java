package com.example.ms_catalogo.controller;

import com.example.ms_catalogo.model.Catalogo;
import com.example.ms_catalogo.service.CatalogoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid; // <-- IMPORTANTE: Importar esta librería

import java.util.List;

// IMPORTS DE SWAGGER / OPENAPI
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/catalogo")
@Tag(name = "Catálogo de Propiedades", description = "API para la gestión y búsqueda de propiedades inmobiliarias")
public class CatalogoController {

    private final CatalogoService catalogoService;

    public CatalogoController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    // Agregamos @Valid antes del @RequestBody. También cambiamos el retorno a ResponseEntity
    // para poder enviar un HttpStatus 201 (CREATED) explícito, lo cual es una muy buena práctica.
    @Operation(summary = "Registrar nueva propiedad", description = "Crea un nuevo registro en el catálogo de propiedades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Propiedad creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error en la validación de los datos enviados")
    })
    @PostMapping
    public ResponseEntity<Catalogo> crear(@Valid @RequestBody Catalogo catalogo) {
        return new ResponseEntity<>(catalogoService.crearPropiedad(catalogo), HttpStatus.CREATED);
    }

    @Operation(summary = "Listar todo el catálogo", description = "Obtiene una lista completa de todas las propiedades registradas")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Catalogo>> listar() {
        return new ResponseEntity<>(catalogoService.listarPropiedades(), HttpStatus.OK);
    }

    @Operation(summary = "Buscar propiedad por ID", description = "Obtiene los detalles de una propiedad específica mediante su identificador único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propiedad encontrada"),
            @ApiResponse(responseCode = "404", description = "Propiedad no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Catalogo> obtener(@PathVariable Long id) {
        return new ResponseEntity<>(catalogoService.obtenerPorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Buscar por tipo", description = "Filtra el catálogo según el tipo de propiedad (ej. Casa, Departamento)")
    @ApiResponse(responseCode = "200", description = "Búsqueda realizada con éxito")
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Catalogo>> buscarPorTipo(@PathVariable String tipo) {
        return new ResponseEntity<>(catalogoService.buscarPorTipo(tipo), HttpStatus.OK);
    }

    @Operation(summary = "Buscar por ubicación", description = "Filtra el catálogo para obtener propiedades en una ubicación específica")
    @ApiResponse(responseCode = "200", description = "Búsqueda realizada con éxito")
    @GetMapping("/ubicacion/{ubicacion}")
    public ResponseEntity<List<Catalogo>> buscarPorUbicacion(@PathVariable String ubicacion) {
        return new ResponseEntity<>(catalogoService.buscarPorUbicacion(ubicacion), HttpStatus.OK);
    }

    // Agregamos @Valid aquí también para cuando se actualicen los datos.
    @Operation(summary = "Actualizar propiedad", description = "Modifica los datos de una propiedad existente identificada por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Propiedad actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error en la validación de los datos enviados"),
            @ApiResponse(responseCode = "404", description = "Propiedad no encontrada para actualizar")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Catalogo> actualizar(@PathVariable Long id,
                                               @Valid @RequestBody Catalogo catalogo) {
        return new ResponseEntity<>(catalogoService.actualizarPropiedad(id, catalogo), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar propiedad", description = "Borra físicamente el registro de una propiedad en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Propiedad eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Propiedad no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        catalogoService.eliminarPropiedad(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content es el código estándar para DELETE
    }
}