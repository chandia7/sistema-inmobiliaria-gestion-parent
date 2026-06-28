package com.example.ms_seguridad.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_seguridad.dto.RegistroRequestDto;
import com.example.ms_seguridad.service.UsuarioAuthService;

import jakarta.validation.Valid;

// IMPORTS DE SWAGGER / OPENAPI
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación y Seguridad", description = "API para el registro seguro y gestión de credenciales de acceso de los usuarios")
public class UsuarioAuthController {

    private final UsuarioAuthService service;

    // Inyección por constructor
    public UsuarioAuthController(UsuarioAuthService service) {
        this.service = service;
    }

    @Operation(summary = "Registrar credenciales de usuario", description = "Recibe los datos de seguridad (como correo y contraseña) para registrar un nuevo acceso en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Credenciales registradas y usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error en la validación de los datos (ej. correo duplicado o contraseña débil)")
    })
    @PostMapping("/registrar")
    public ResponseEntity<String> registrar(@Valid @RequestBody RegistroRequestDto request) {
        return new ResponseEntity<>(service.registrarUsuario(request), HttpStatus.CREATED);
    }
}
