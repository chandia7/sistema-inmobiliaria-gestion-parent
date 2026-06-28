package com.example.ms_seguimiento.service;

import com.example.ms_seguimiento.model.Seguimiento;
import com.example.ms_seguimiento.repository.SeguimientoRepository;
import jakarta.persistence.EntityNotFoundException; // <-- Agregamos el import del escudo
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SeguimientoService {

    private final SeguimientoRepository seguimientoRepository;

    public SeguimientoService(SeguimientoRepository seguimientoRepository) {
        this.seguimientoRepository = seguimientoRepository;
    }

    // Registrar nuevo evento
    public Seguimiento registrarSeguimiento(Seguimiento seguimiento) {
        seguimiento.setFechaRegistro(LocalDateTime.now());
        return seguimientoRepository.save(seguimiento);
    }

    // Obtener historial de un pedido
    public List<Seguimiento> obtenerHistorial(Long idPedido) {
        return seguimientoRepository.findByIdPedidoOrderByFechaRegistroAsc(idPedido);
    }

    // Obtener todos
    public List<Seguimiento> listarSeguimientos() {
        return seguimientoRepository.findAll();
    }

    // Obtener uno por ID
    public Seguimiento obtenerPorId(Long id) {
        return seguimientoRepository.findById(id)
                // CORRECCIÓN: Excepción específica para el GlobalExceptionHandler
                .orElseThrow(() -> new EntityNotFoundException("Seguimiento no encontrado con el ID: " + id));
    }
}
