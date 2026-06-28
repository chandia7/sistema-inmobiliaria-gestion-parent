package com.example.ms_seguimiento.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ms_seguimiento.model.Seguimiento;

import java.util.List;

public interface SeguimientoRepository extends JpaRepository<Seguimiento, Long> {

    List<Seguimiento> findByIdPedidoOrderByFechaRegistroAsc(Long idPedido);
}