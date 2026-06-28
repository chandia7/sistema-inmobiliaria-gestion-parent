package com.example.ms_seguridad.dto;

import lombok.Data;

@Data
public class UsuarioDto {
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private Long idAuth; // <-- El cambio está aquí. De Integer a Long.
}
