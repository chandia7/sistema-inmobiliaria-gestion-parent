package com.example.ms_promociones.repository;

import com.example.ms_promociones.entity.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {
    // Si más adelante queremos buscar por código, Spring lo hace solo:
    Promocion findByCodigo(String codigo);
}
