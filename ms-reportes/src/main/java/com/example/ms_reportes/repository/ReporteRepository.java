package com.example.ms_reportes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ms_reportes.model.Reporte;

import java.util.List;

public interface ReporteRepository extends JpaRepository<Reporte, Long> {

    List<Reporte> findAllByOrderByFechaGeneracionDesc();
}