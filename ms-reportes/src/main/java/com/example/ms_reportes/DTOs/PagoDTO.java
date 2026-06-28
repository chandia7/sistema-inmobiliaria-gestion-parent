package com.example.ms_reportes.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoDTO {
    private Long id;
    private BigDecimal monto;
    private String estado;
}
