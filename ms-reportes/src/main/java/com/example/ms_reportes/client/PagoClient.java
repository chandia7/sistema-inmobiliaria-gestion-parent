package com.example.ms_reportes.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.ms_reportes.DTOs.PagoDTO;

import java.util.List;

@FeignClient(name = "ms-pagos")
public interface PagoClient {

    @GetMapping("/api/pagos")
    List<PagoDTO> obtenerPagos();
}
