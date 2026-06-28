package com.example.ms_usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.ms_usuarios.modelo.Usuario;


import java.util.Optional;






@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    // 1. Busca el perfil específico asociado a las credenciales de ms-seguridad
    // Retorna Optional por si el usuario aún no completa su perfil
    Optional<Usuario> findByIdAuth(Long idAuth);

    // 2. Búsqueda exacta por correo electrónico
    Optional<Usuario> findByEmail(String email);

    // 3. Coincidencia parcial (como un LIKE %valor%)[cite: 16]. Ideal para un buscador en el Frontend
    java.util.List<Usuario> findByApellidoContaining(String parte);

    // 4. Verificación rápida (devuelve true/false)[cite: 84]. Útil para validar antes de crear
    boolean existsByEmail(String email);

}
