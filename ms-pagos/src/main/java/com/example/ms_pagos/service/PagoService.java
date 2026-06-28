package com.example.ms_pagos.service;

import com.example.ms_pagos.model.Pago;
import com.example.ms_pagos.model.Pago.EstadoPago;
import com.example.ms_pagos.repository.PagoRepository;
import jakarta.persistence.EntityNotFoundException; // <-- Para el Error 404 limpio
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;

    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    // Procesar pago
    public Pago procesarPago(Pago pago) {

        pago.setFechaPago(LocalDateTime.now());

        // Simulación de validación financiera
        if (pago.getMonto().doubleValue() > 0) {
            pago.setEstado(EstadoPago.APROBADO); // <-- Sin el .name()
        } else {
            pago.setEstado(EstadoPago.RECHAZADO); // <-- Sin el .name()
        }

        return pagoRepository.save(pago);
    }

    // Buscar por ID
    public Pago obtenerPorId(Long id) {
        return pagoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pago no encontrado con el ID: " + id));
    }

    // Buscar por pedido
    public Pago obtenerPorPedido(Long idPedido) {
        return pagoRepository.findByIdPedido(idPedido)
                .orElseThrow(() -> new EntityNotFoundException("Pago no encontrado para el pedido: " + idPedido));
    }

    // Listar todos
    public List<Pago> listarPagos() {
        return pagoRepository.findAll();
    }

    // Filtrar por estado
    public List<Pago> listarPorEstado(String estado) {
        // Convertimos el texto de la URL al Enum
        EstadoPago estadoEnum = EstadoPago.valueOf(estado.toUpperCase());
        return pagoRepository.findByEstado(estadoEnum);
    }
}