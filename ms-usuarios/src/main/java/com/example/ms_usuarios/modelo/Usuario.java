package com.example.ms_usuarios.modelo;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Column(nullable = false)
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    private String telefono;

    @Column(nullable = false)
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del correo no es válido") // <-- Candado de formato
    private String email;

    @Column(nullable = false)
    private LocalDate fechaRegistro;

    @Column(name = "id_auth", unique = true, nullable = false)
    @NotNull(message = "El ID de autenticación es obligatorio") // <-- Candado de seguridad
    private Long idAuth;
}