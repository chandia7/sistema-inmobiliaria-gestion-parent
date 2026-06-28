package com.example.ms_seguimiento.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "seguimiento")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seguimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_pedido", nullable = false)
    @NotNull(message = "El id del pedido es obligatorio")
    private Long idPedido;

    // Cambiamos String por el Enum y usamos @NotNull
    @NotNull(message = "El estado es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoSeguimiento estado;

    @Column(nullable = false)
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    // Aquí está el Enum en su lugar correcto (Nivel Arquitectura)
    public enum EstadoSeguimiento {
        PEDIDO_CREADO,
        RESERVA_CONFIRMADA,
        PAGO_APROBADO,
        DOCUMENTACION_EN_REVISION,
        FIRMA_NOTARIAL_AGENDADA,
        ESCRITURA_COMPLETADA,
        ENTREGA_FINALIZADA
    }
}
