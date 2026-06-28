package com.example.ms_reportes.service;

import com.example.ms_reportes.client.PagoClient;
import com.example.ms_reportes.client.PedidoClient;
import com.example.ms_reportes.DTOs.PagoDTO;
import com.example.ms_reportes.DTOs.PedidoDTO;
import com.example.ms_reportes.model.Reporte;
import com.example.ms_reportes.repository.ReporteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReporteService {

    private final ReporteRepository reporteRepository;
    private final PagoClient pagoClient;
    private final PedidoClient pedidoClient;

    public ReporteService(ReporteRepository reporteRepository, PagoClient pagoClient, PedidoClient pedidoClient) {
        this.reporteRepository = reporteRepository;
        this.pagoClient = pagoClient;
        this.pedidoClient = pedidoClient;
    }

    // 🔥 Lógica maestra para generar el reporte
    public Reporte generarReporte() {
        Reporte reporte = new Reporte();

        // 1. Vamos a buscar los datos a los otros microservicios
        List<PedidoDTO> pedidos = pedidoClient.obtenerPedidos();
        List<PagoDTO> pagos = pagoClient.obtenerPagos();

        // 2. Calculamos el Total de Pedidos (el tamaño de la lista)
        int totalPedidos = pedidos.size();

        // 3. Calculamos los Pedidos Completados (filtramos los que dicen "COMPLETADO")
        long pedidosCompletados = pedidos.stream()
                .filter(p -> p.getEstado() != null && p.getEstado().equalsIgnoreCase("COMPLETADO"))
                .count();

        // 4. Calculamos el Total de Ventas (sumamos el monto solo de los pagos "APROBADO")
        BigDecimal totalVentas = pagos.stream()
                .filter(p -> p.getEstado() != null && p.getEstado().equalsIgnoreCase("APROBADO"))
                .map(PagoDTO::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Esto suma todos los BigDecimals

        // 5. Armamos el reporte con los cálculos
        reporte.setTotalPedidos(totalPedidos);
        reporte.setPedidosCompletados(pedidosCompletados);
        reporte.setTotalVentas(totalVentas);
        reporte.setFechaGeneracion(LocalDateTime.now());

        // 6. Guardamos y devolvemos
        return reporteRepository.save(reporte);
    }

    // Listar todos los reportes
    public List<Reporte> listarReportes() {
        return reporteRepository.findAll();
    }

    // Buscar reporte por ID
    public Reporte obtenerReporte(Long id) {
        return reporteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reporte no encontrado con el ID: " + id));
    }
}
