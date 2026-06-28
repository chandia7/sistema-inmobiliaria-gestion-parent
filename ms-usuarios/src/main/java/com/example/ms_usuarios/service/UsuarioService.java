package com.example.ms_usuarios.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_usuarios.modelo.Usuario;
import com.example.ms_usuarios.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException; // <-- Importamos nuestro escudo

@Service
public class UsuarioService {

    private final UsuarioRepository repo;

    // Inyección por constructor (El estándar de oro)
    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    // Crear o guardar un nuevo usuario
    public Usuario guardarUsuario(Usuario usuario){
        if (usuario.getFechaRegistro() == null){
            usuario.setFechaRegistro(LocalDate.now());
        }
        return repo.save(usuario);
    }

    // Buscar por ID interno (Lanzando excepción si no existe)
    public Usuario buscarPorId(Long id){
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con el ID: " + id));
    }

    // Buscar por el ID de seguridad (Lanzando excepción si no existe)
    public Usuario buscarPorIdAuth(Long idAuth) {
        return repo.findByIdAuth(idAuth)
                .orElseThrow(() -> new EntityNotFoundException("No hay perfil asociado a la cuenta de seguridad: " + idAuth));
    }

    // Listar todos los perfiles
    public List<Usuario> listarTodos() {
        return repo.findAll();
    }

    // Buscar por apellido
    public List<Usuario> buscarPorApellido(String texto){
        return repo.findByApellidoContaining(texto);
    }

    // Eliminar un usuario (También le agregamos el escudo por si intentan borrar uno que no existe)
    public void eliminarUsuario(Long id){
        if(repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new EntityNotFoundException("No se puede eliminar. Usuario no encontrado con ID: " + id);
        }
    }

    // Validar si el correo ya existe
    public boolean existeCorreo(String email) {
        return repo.existsByEmail(email);
    }
}

