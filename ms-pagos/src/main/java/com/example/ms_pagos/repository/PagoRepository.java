package com.example.ms_pagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ms_pagos.model.Pago;
import com.example.ms_pagos.model.Pago.EstadoPago; // <-- Importamos el Enum

import java.util.List;
import java.util.Optional;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    Optional<Pago> findByIdPedido(Long idPedido);

    List<Pago> findByEstado(EstadoPago estado); // <-- Cambiamos String por EstadoPago
}
