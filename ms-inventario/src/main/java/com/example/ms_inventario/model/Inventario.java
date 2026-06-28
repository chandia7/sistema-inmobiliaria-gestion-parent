package com.example.ms_inventario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_propiedad", nullable = false, unique = true)
    @NotNull(message = "El id de la propiedad es obligatorio")
    private Long idPropiedad;

    // Agregamos la validación clave: ¡El stock nunca puede ser menor a 0!
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(nullable = false)
    private Integer stock;

    // Cambiamos 'String' por 'EstadoInventario' y usamos @Enumerated
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoInventario estado;

    @Version
    private Integer version;

    public enum EstadoInventario {
        DISPONIBLE,
        RESERVADO,
        VENDIDO,
        BLOQUEADO
    }
}