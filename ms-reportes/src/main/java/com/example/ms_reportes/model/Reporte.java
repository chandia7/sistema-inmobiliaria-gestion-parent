package com.example.ms_reportes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "reporte")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El total de ventas no puede ser nulo")
    @PositiveOrZero(message = "El total de ventas no puede ser negativo")
    @Column(nullable = false)
    private BigDecimal totalVentas;

    @NotNull(message = "El total de pedidos no puede ser nulo")
    @PositiveOrZero(message = "El total de pedidos no puede ser negativo")
    @Column(nullable = false)
    private Integer totalPedidos;

    @NotNull(message = "Los pedidos completados no pueden ser nulos")
    @PositiveOrZero(message = "Los pedidos completados no pueden ser negativos")
    @Column(nullable = false)
    private Long pedidosCompletados;

    @Column(nullable = false)
    private LocalDateTime fechaGeneracion;
}
