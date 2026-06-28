package com.example.ms_promociones.service;

import com.example.ms_promociones.entity.Promocion;
import com.example.ms_promociones.repository.PromocionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromocionService {

    private final PromocionRepository repository;

    // Inyección por constructor (El estándar de oro para pruebas con Mockito)
    public PromocionService(PromocionRepository repository) {
        this.repository = repository;
    }

    public Promocion crearPromocion(Promocion promocion) {
        return repository.save(promocion);
    }

    public List<Promocion> obtenerTodas() {
        return repository.findAll();
    }
}