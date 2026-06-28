package com.example.ms_notificaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ms_notificaciones.model.Notificacion;
import com.example.ms_notificaciones.model.Notificacion.EstadoNotificacion;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    List<Notificacion> findByDestinatario(String destinatario);

    List<Notificacion> findByEstado(EstadoNotificacion estado);
}
