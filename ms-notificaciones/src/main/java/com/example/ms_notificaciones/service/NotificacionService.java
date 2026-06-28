package com.example.ms_notificaciones.service;

import com.example.ms_notificaciones.model.Notificacion;
import com.example.ms_notificaciones.model.Notificacion.EstadoNotificacion; // <-- Importamos el Enum
import com.example.ms_notificaciones.repository.NotificacionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacionService { // <-- Nombre limpio sin la "s" extra

    private final NotificacionRepository notificacionRepository;

    public NotificacionService(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    // Enviar notificación (simulación)
    public Notificacion enviarNotificacion(Notificacion notificacion) {
        
        try {
            // Aquí después integras JavaMailSender o API externa
            System.out.println("Enviando correo a: " + notificacion.getDestinatario());
            System.out.println("Asunto: " + notificacion.getAsunto());

            // CORRECCIÓN 1: Usamos el Enum directamente
            notificacion.setEstado(EstadoNotificacion.ENVIADO);
            
        } catch (Exception e) {
            // CORRECCIÓN 2: Usamos el Enum directamente
            notificacion.setEstado(EstadoNotificacion.ERROR);
        }

        notificacion.setFechaEnvio(LocalDateTime.now());
        return notificacionRepository.save(notificacion);
    }

    // Listar todas
    public List<Notificacion> listarNotificaciones() {
        return notificacionRepository.findAll();
    }

    // Buscar por destinatario
    public List<Notificacion> obtenerPorDestinatario(String email) {
        return notificacionRepository.findByDestinatario(email);
    }

    // Buscar por estado
    public List<Notificacion> obtenerPorEstado(String estado) {
        // CORRECCIÓN 3: Convertimos el texto de la URL a nuestro Enum seguro
        EstadoNotificacion estadoEnum = EstadoNotificacion.valueOf(estado.toUpperCase());
        return notificacionRepository.findByEstado(estadoEnum);
    }
}