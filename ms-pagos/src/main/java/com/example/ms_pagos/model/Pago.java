package com.example.ms_pagos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pago")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_pedido", nullable = false)
    @NotNull(message = "El id del pedido es obligatorio")
    private Long idPedido;

    @Column(nullable = false)
    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto del pago debe ser mayor a cero") // <-- EL CANDADO BANCARIO
    private BigDecimal monto;

    @NotNull(message = "El método de pago es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MetodoPago metodoPago; // <-- Mejorado a Enum

    // Quitamos @NotNull asumiendo que tu Service le pone PENDIENTE por defecto
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPago estado; // <-- Conectado al Enum

    @Column(nullable = false)
    private LocalDateTime fechaPago;

    public enum EstadoPago {
        PENDIENTE,
        APROBADO,
        RECHAZADO
    }

    // Creamos este Enum para reemplazar el Regex, es mucho más seguro
    public enum MetodoPago {
        TRANSFERENCIA,
        TARJETA,
        EFECTIVO
    }
}
