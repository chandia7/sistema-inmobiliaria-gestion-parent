package com.example.ms_pedidos.service;

import com.example.ms_pedidos.model.Pedido;
import com.example.ms_pedidos.model.Pedido.EstadoPedido; // <-- Import corregido apuntando adentro de Pedido
import com.example.ms_pedidos.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException; // <-- Import para nuestro Escudo 404
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido crearPedido(Pedido pedido) {
        // CORRECCIÓN 1: Usamos el Enum directamente sin el .name()
        pedido.setEstado(EstadoPedido.CREADO);
        pedido.setFechaCreacion(LocalDateTime.now());
        
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido obtenerPorId(Long id) {
        return pedidoRepository.findById(id)
                // CORRECCIÓN 2: Excepción específica para el GlobalExceptionHandler
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con el ID: " + id));
    }

    public List<Pedido> obtenerPorUsuario(Long idUsuario) {
        return pedidoRepository.findByIdUsuario(idUsuario);
    }

    public Pedido cancelarPedido(Long id) {
        Pedido pedido = obtenerPorId(id);
        
        // CORRECCIÓN 3: Enum directo
        pedido.setEstado(EstadoPedido.CANCELADO);
        
        return pedidoRepository.save(pedido);
    }

    public Pedido completarPedido(Long id) {
        Pedido pedido = obtenerPorId(id);
        
        // CORRECCIÓN 4: Enum directo
        pedido.setEstado(EstadoPedido.COMPLETADO);
        
        return pedidoRepository.save(pedido);
    }
}