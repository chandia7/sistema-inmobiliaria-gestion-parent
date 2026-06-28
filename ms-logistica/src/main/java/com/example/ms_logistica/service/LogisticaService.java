package com.example.ms_logistica.service;

import com.example.ms_logistica.model.Logistica;
import com.example.ms_logistica.model.Logistica.EstadoLogistica;
import com.example.ms_logistica.repository.LogisticaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogisticaService {

    private final LogisticaRepository logisticaRepository;

    public LogisticaService(LogisticaRepository logisticaRepository) {
        this.logisticaRepository = logisticaRepository;
    }

    // Crear proceso logístico
    public Logistica crearProceso(Logistica logistica) {
        
        // CORRECCIÓN 1: Le pasamos el Enum directamente, borramos el .name()
        logistica.setEstado(EstadoLogistica.DOCUMENTACION_EN_REVISION);
        logistica.setFechaActualizacion(LocalDateTime.now());
        
        return logisticaRepository.save(logistica);
    }

    // Actualizar estado
    public Logistica actualizarEstado(Long idPedido, String nuevoEstado) {
        Logistica logistica = logisticaRepository.findByIdPedido(idPedido)
                // Usamos EntityNotFoundException para nuestro Escudo Global
                .orElseThrow(() -> new EntityNotFoundException("Proceso logístico no encontrado para el pedido: " + idPedido));

        // CORRECCIÓN 2: Convertimos el texto (String) a Enum usando valueOf()
        // Le agregamos toUpperCase() por si el usuario lo manda en minúsculas por error
        logistica.setEstado(EstadoLogistica.valueOf(nuevoEstado.toUpperCase()));
        logistica.setFechaActualizacion(LocalDateTime.now());

        return logisticaRepository.save(logistica);
    }

    // Obtener por pedido
    public Logistica obtenerPorPedido(Long idPedido) {
        return logisticaRepository.findByIdPedido(idPedido)
                .orElseThrow(() -> new EntityNotFoundException("Proceso logístico no encontrado para el pedido: " + idPedido));
    }

    // Listar todos
    public List<Logistica> listarProcesos() {
        return logisticaRepository.findAll();
    }
}
