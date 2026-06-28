package com.example.ms_logistica.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ms_logistica.model.Logistica;

import java.util.Optional;

public interface LogisticaRepository extends JpaRepository<Logistica, Long> {

    Optional<Logistica> findByIdPedido(Long idPedido);
}
