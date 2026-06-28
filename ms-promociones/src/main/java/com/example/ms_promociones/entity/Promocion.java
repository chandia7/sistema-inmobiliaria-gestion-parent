package com.example.ms_promociones.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "promociones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Promocion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El código de la promoción es obligatorio")
    @Column(unique = true, nullable = false)
    private String codigo; // Ej: "VERANO2026"

    @NotNull(message = "El porcentaje de descuento es obligatorio")
    @Min(value = 1, message = "El descuento debe ser de al menos 1%")
    @Max(value = 100, message = "El descuento no puede superar el 100%") // <-- Candado financiero
    @Column(nullable = false)
    private Integer porcentajeDescuento; // Ej: 20 (para 20%)

    @Column(nullable = false)
    private Boolean activa = true;
}
