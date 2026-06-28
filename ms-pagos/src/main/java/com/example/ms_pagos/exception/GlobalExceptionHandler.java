package com.example.ms_pagos.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Atrapa errores de validación de los DTOs (@NotBlank, @Email, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidaciones(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(campo, mensaje);
        });
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

    // 2. Atrapa errores de la Base de Datos (ej. el error monto_final nulo que tuvimos antes)
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> manejarErroresBaseDatos(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Conflicto de integridad en la base de datos. Verifica que los datos obligatorios o únicos estén correctos.");
        return new ResponseEntity<>(error, HttpStatus.CONFLICT); // Devuelve un Error 409
    }

// ... código de los otros ExceptionHandler ...

    // 3. Atrapa errores cuando mandan un dato que no coincide con el tipo esperado (Ej. Enum o texto en vez de número)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> manejarErrorDeFormato(HttpMessageNotReadableException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Error en el formato de los datos enviados. Verifica que los valores de las listas (como métodos de pago) sean exactos y estén en mayúsculas.");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); // Devuelve Error 400
    }

    // 4. Atrapa cualquier otro error no controlado para que no se rompa el servidor
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> manejarErroresGenerales(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Ocurrió un error interno en el servidor: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}