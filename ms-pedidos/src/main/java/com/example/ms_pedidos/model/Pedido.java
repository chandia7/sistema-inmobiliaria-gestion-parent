package com.example.ms_pedidos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero; // <-- Candado para el dinero
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedido")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_usuario", nullable = false)
    @NotNull(message = "El id del usuario es obligatorio")
    private Long idUsuario;

    @Column(name = "id_propiedad", nullable = false)
    @NotNull(message = "El id de la propiedad es obligatorio")
    private Long idPropiedad;

    private Long idPromocion; // No le ponemos NotNull porque un usuario podría no usar promoción

    @NotNull(message = "El monto final es obligatorio")
    @PositiveOrZero(message = "El monto final no puede ser negativo") // <-- Protección financiera
    @Column(nullable = false)
    private BigDecimal montoFinal;

    // Quitamos el @NotNull porque el Service asume la creación inicial
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPedido estado; // <-- Conectado al Enum

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    // Metimos tu Enum aquí adentro, súper limpio
    public enum EstadoPedido {
        CREADO,
        RESERVADO,
        PAGADO,
        EN_PROCESO,
        COMPLETADO,
        CANCELADO
    }
}