package com.example.ms_catalogo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "catalogo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Catalogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @NotBlank(message = "La ubicación es obligatoria")
    @Column(nullable = false)
    private String ubicacion;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    @Column(nullable = false)
    private BigDecimal precio;

    @NotBlank(message = "El tipo de propiedad es obligatorio")
    @Column(nullable = false)
    private String tipo;

    @Positive(message = "Los metros cuadrados deben ser un valor positivo")
    private Integer metrosCuadrados;

    @Min(value = 0, message = "El número de habitaciones no puede ser negativo")
    private Integer habitaciones;

    private String imagenUrl;
}
