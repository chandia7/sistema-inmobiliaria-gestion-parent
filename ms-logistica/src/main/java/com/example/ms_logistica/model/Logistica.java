package com.example.ms_logistica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "logistica")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Logistica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_pedido", nullable = false)
    @NotNull(message = "El id del pedido es obligatorio")
    private Long idPedido;

    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoLogistica estado;

    @NotBlank(message = "La notaría es obligatoria")
    @Column(nullable = false)
    private String notaria;

    @Column(nullable = false)
    private LocalDateTime fechaActualizacion;

    public enum EstadoLogistica {
    DOCUMENTACION_EN_REVISION,
    DOCUMENTACION_APROBADA,
    FIRMA_AGENDADA,
    ESCRITURA_EN_PROCESO,
    ENTREGA_FINALIZADA
}
}
