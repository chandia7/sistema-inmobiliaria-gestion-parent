package com.example.ms_seguridad.service;

import com.example.ms_seguridad.dto.RegistroRequestDto;
import com.example.ms_seguridad.dto.UsuarioDto;
import com.example.ms_seguridad.entity.UsuarioAuth;
import com.example.ms_seguridad.feign.UsuarioFeignClient;
import com.example.ms_seguridad.repository.UsuarioAuthRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioAuthService {

    private final UsuarioAuthRepository repository;
    private final UsuarioFeignClient feignClient;
    private final PasswordEncoder passwordEncoder;

    // Inyección por constructor (El estándar de oro)
    public UsuarioAuthService(UsuarioAuthRepository repository, UsuarioFeignClient feignClient, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.feignClient = feignClient;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public String registrarUsuario(RegistroRequestDto request) {

        // 1. Guardar en ms-seguridad (con contraseña codificada)
        UsuarioAuth auth = new UsuarioAuth();
        auth.setEmail(request.getEmail());
        auth.setPassword(passwordEncoder.encode(request.getPassword()));
        auth.setRol(request.getRol());

        UsuarioAuth authGuardado = repository.save(auth);

        // 2. Preparar el DTO para ms-usuarios
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setNombre(request.getNombre());
        usuarioDto.setApellido(request.getApellido());
        usuarioDto.setTelefono(request.getTelefono());
        usuarioDto.setEmail(request.getEmail());
        usuarioDto.setIdAuth(authGuardado.getId()); // Usamos el ID generado por seguridad

        // 3. Llamada Feign
        feignClient.crearUsuario(usuarioDto);

        return "Usuario y credenciales creados exitosamente";
    }
}