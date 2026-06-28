package com.example.ms_seguridad.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ms_seguridad.entity.UsuarioAuth;

@Repository
public interface UsuarioAuthRepository extends JpaRepository<UsuarioAuth, Long> {
    // Spring Boot hace la magia: con solo nombrar el método así, 
    // él hace el "SELECT * FROM credenciales_usuarios WHERE email = ?"
    Optional<UsuarioAuth> findByEmail(String email);

}
