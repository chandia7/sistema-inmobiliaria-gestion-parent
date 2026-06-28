package com.example.ms_inventario.service;

import com.example.ms_inventario.model.Inventario;
import com.example.ms_inventario.model.Inventario.EstadoInventario;
import com.example.ms_inventario.repository.InventarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioService {

    private final InventarioRepository inventarioRepository;

    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    // Crear stock
    public Inventario crearInventario(Inventario inventario) {
        if (inventarioRepository.existsByIdPropiedad(inventario.getIdPropiedad())) {
            throw new RuntimeException("La propiedad ya existe en inventario");
        }
        
        // CORRECCIÓN: Le pasamos el Enum directamente, sin el .name()
        inventario.setEstado(EstadoInventario.DISPONIBLE);
        
        return inventarioRepository.save(inventario);
    }

    // Obtener todos
    public List<Inventario> listarInventario() {
        return inventarioRepository.findAll();
    }

    // Buscar por propiedad
    public Inventario obtenerPorPropiedad(Long idPropiedad) {
        return inventarioRepository.findByIdPropiedad(idPropiedad)
                // CORRECCIÓN: Usamos EntityNotFoundException para que actúe tu GlobalExceptionHandler
                .orElseThrow(() -> new EntityNotFoundException("Inventario no encontrado para la propiedad: " + idPropiedad));
    }

    // Reservar
    public Inventario reservar(Long idPropiedad) {
        Inventario inventario = obtenerPorPropiedad(idPropiedad);

        // CORRECCIÓN: Comparamos el Enum directamente con !=
        if (inventario.getEstado() != EstadoInventario.DISPONIBLE) {
            throw new RuntimeException("El inventario no está disponible para reserva");
        }

        inventario.setEstado(EstadoInventario.RESERVADO);
        return inventarioRepository.save(inventario);
    }

    // Confirmar venta
    public Inventario vender(Long idPropiedad) {
        Inventario inventario = obtenerPorPropiedad(idPropiedad);

        if (inventario.getEstado() != EstadoInventario.RESERVADO) {
            throw new RuntimeException("La propiedad debe estar reservada para poder venderse");
        }

        inventario.setEstado(EstadoInventario.VENDIDO);
        inventario.setStock(0);
        return inventarioRepository.save(inventario);
    }

    // Liberar reserva
    public Inventario liberar(Long idPropiedad) {
        Inventario inventario = obtenerPorPropiedad(idPropiedad);

        if (inventario.getEstado() == EstadoInventario.RESERVADO) {
            inventario.setEstado(EstadoInventario.DISPONIBLE);
        }

        return inventarioRepository.save(inventario);
    }

    // Bloquear
    public Inventario bloquear(Long idPropiedad) {
        Inventario inventario = obtenerPorPropiedad(idPropiedad);
        inventario.setEstado(EstadoInventario.BLOQUEADO);
        return inventarioRepository.save(inventario);
    }
}