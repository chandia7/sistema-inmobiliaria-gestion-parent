package com.example.ms_catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ms_catalogo.model.Catalogo;

import java.util.List;

public interface CatalogoRepository extends JpaRepository<Catalogo, Long> {

    List<Catalogo> findByTipo(String tipo);

    List<Catalogo> findByUbicacionContainingIgnoreCase(String ubicacion);
}
