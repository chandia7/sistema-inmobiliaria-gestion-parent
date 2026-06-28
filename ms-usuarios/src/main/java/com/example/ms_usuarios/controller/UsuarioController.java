package com.example.ms_usuarios.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_usuarios.modelo.Usuario;
import com.example.ms_usuarios.service.UsuarioService; // <-- Corregido a SINGULAR

import jakarta.validation.Valid;

// IMPORTS DE SWAGGER / OPENAPI
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Gestión de Usuarios", description = "API para la administración de perfiles y datos personales de los clientes")
public class UsuarioController {

    private final UsuarioService service; // <-- Corregido a SINGULAR

    // Inyección por constructor
    public UsuarioController(UsuarioService service) { // <-- Corregido a SINGULAR
        this.service = service;
    }

    // 1. CREAR USUARIO
    @Operation(summary = "Crear nuevo usuario", description = "Registra un nuevo perfil en el sistema. Valida internamente que el correo electrónico no esté duplicado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado y registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación (ej. correo ya registrado o datos inválidos)")
    })
    @PostMapping
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody Usuario usuario) {
        // La validación extra del correo la dejamos, pero devolvemos un JSON para mantener el estándar
        if(service.existeCorreo(usuario.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(java.util.Map.of("error", "El correo " + usuario.getEmail() + " ya está registrado."));
        }
        
        return new ResponseEntity<>(service.guardarUsuario(usuario), HttpStatus.CREATED);
    }

    // 2. BUSCAR POR ID INTERNO
    @Operation(summary = "Buscar usuario por ID interno", description = "Obtiene los detalles del perfil de un usuario mediante su identificador interno único de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        // Confiamos en que el Service lance la excepción EntityNotFoundException si no lo encuentra
        return new ResponseEntity<>(service.buscarPorId(id), HttpStatus.OK);
    }

    // 3. BUSCAR POR ID DE SEGURIDAD (idAuth)
    @Operation(summary = "Buscar usuario por ID de Autenticación", description = "Obtiene los detalles del usuario utilizando el identificador generado por el microservicio de seguridad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado para el ID de seguridad indicado")
    })
    @GetMapping("/auth/{idAuth}")
    public ResponseEntity<Usuario> buscarPorIdAuth(@PathVariable Long idAuth) {
        return new ResponseEntity<>(service.buscarPorIdAuth(idAuth), HttpStatus.OK);
    }

    // 4. LISTAR TODOS
    @Operation(summary = "Listar todos los usuarios", description = "Obtiene un registro completo de todos los usuarios registrados en la plataforma")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return new ResponseEntity<>(service.listarTodos(), HttpStatus.OK);
    }

    // 5. BUSCADOR PARCIAL POR APELLIDO
    @Operation(summary = "Buscar usuarios por apellido", description = "Filtra el listado de usuarios que coincidan total o parcialmente con el apellido proporcionado")
    @ApiResponse(responseCode = "200", description = "Búsqueda realizada con éxito")
    @GetMapping("/buscar")
    public ResponseEntity<List<Usuario>> buscarPorApellido(@RequestParam String apellido) {
        return new ResponseEntity<>(service.buscarPorApellido(apellido), HttpStatus.OK);
    }
}

