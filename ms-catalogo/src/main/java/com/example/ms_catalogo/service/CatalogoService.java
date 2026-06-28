package com.example.ms_catalogo.service;

import org.springframework.stereotype.Service;

import com.example.ms_catalogo.model.Catalogo;
import com.example.ms_catalogo.repository.CatalogoRepository;

import java.util.List;

@Service
public class CatalogoService {

    private final CatalogoRepository catalogoRepository;

    public CatalogoService(CatalogoRepository catalogoRepository) {
        this.catalogoRepository = catalogoRepository;
    }

    // Crear propiedad
    public Catalogo crearPropiedad(Catalogo catalogo) {
        return catalogoRepository.save(catalogo);
    }

    // Obtener todas
    public List<Catalogo> listarPropiedades() {
        return catalogoRepository.findAll();
    }

    // Buscar por ID
    public Catalogo obtenerPorId(Long id) {
        return catalogoRepository.findById(id).orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Propiedad no encontrada con el ID: " + id));
    }

    // Buscar por tipo
    public List<Catalogo> buscarPorTipo(String tipo) {
        return catalogoRepository.findByTipo(tipo);
    }

    // Buscar por ubicación
    public List<Catalogo> buscarPorUbicacion(String ubicacion) {
        return catalogoRepository.findByUbicacionContainingIgnoreCase(ubicacion);
    }

    // Actualizar
    public Catalogo actualizarPropiedad(Long id, Catalogo catalogoActualizado) {

        Catalogo catalogo = obtenerPorId(id);

        catalogo.setNombre(catalogoActualizado.getNombre());
        catalogo.setDescripcion(catalogoActualizado.getDescripcion());
        catalogo.setUbicacion(catalogoActualizado.getUbicacion());
        catalogo.setPrecio(catalogoActualizado.getPrecio());
        catalogo.setTipo(catalogoActualizado.getTipo());
        catalogo.setMetrosCuadrados(catalogoActualizado.getMetrosCuadrados());
        catalogo.setHabitaciones(catalogoActualizado.getHabitaciones());
        catalogo.setImagenUrl(catalogoActualizado.getImagenUrl());

        return catalogoRepository.save(catalogo);
    }

    // Eliminar
    public void eliminarPropiedad(Long id) {
        catalogoRepository.deleteById(id);
    }
}