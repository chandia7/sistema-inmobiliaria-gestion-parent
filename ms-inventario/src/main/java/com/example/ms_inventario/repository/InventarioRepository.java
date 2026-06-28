package com.example.ms_inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ms_inventario.model.Inventario;

import java.util.List;
import java.util.Optional;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    Optional<Inventario> findByIdPropiedad(Long idPropiedad);

    List<Inventario> findByEstado(String estado);

    boolean existsByIdPropiedad(Long idPropiedad);
}