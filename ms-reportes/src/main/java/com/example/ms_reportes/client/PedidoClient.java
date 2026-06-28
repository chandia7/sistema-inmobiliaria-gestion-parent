package com.example.ms_reportes.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.ms_reportes.DTOs.PedidoDTO;

import java.util.List;

@FeignClient(name = "ms-pedidos")
public interface PedidoClient {

    @GetMapping("/api/pedidos")
    List<PedidoDTO> obtenerPedidos();
}
