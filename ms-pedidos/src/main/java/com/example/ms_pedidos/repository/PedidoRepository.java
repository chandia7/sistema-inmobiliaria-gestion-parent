package com.example.ms_pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ms_pedidos.model.Pedido;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByIdUsuario(Long idUsuario);

    List<Pedido> findByEstado(String estado);
}