package com.example.ms_notificaciones.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificacion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El destinatario es obligatorio") // <-- Agregamos el NotBlank
    @Email(message = "Formato de email inválido")
    @Column(nullable = false)
    private String destinatario;

    @NotBlank(message = "El asunto es obligatorio")
    @Column(nullable = false)
    private String asunto;

    @NotBlank(message = "El mensaje es obligatorio")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    // Estos no llevan @NotNull porque se asume que los asigna tu NotificacionService
    @Column(nullable = false)
    private LocalDateTime fechaEnvio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoNotificacion estado; // <-- Ahora usa el Enum

    // Creamos el Enum con los estados que propusiste
    public enum EstadoNotificacion {
        PENDIENTE,
        ENVIADO,
        ERROR
    }
}