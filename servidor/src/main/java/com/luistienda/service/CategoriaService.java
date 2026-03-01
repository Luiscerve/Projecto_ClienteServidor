package com.luistienda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luistienda.exception.ResourceNotFoundException;
import com.luistienda.model.Categoria;
import com.luistienda.repository.CategoriaRepository;

/**
 * Servicio con la lógica de negocio para Categorías.
 */
@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Obtener todas las categorías
    public List<Categoria> obtenerTodas() {
        return categoriaRepository.findAll();
    }

    // Obtener una categoría por ID
    public Categoria obtenerPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + id));
    }

    // Crear una nueva categoría
    public Categoria crear(Categoria categoria) {
        if (categoria.getNombre() == null || categoria.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        return categoriaRepository.save(categoria);
    }

    // Actualizar una categoría
    public Categoria actualizar(Long id, Categoria datos) {
        Categoria categoria = obtenerPorId(id);
        
        if (datos.getNombre() != null) {
            categoria.setNombre(datos.getNombre());
        }
        if (datos.getDescripcion() != null) {
            categoria.setDescripcion(datos.getDescripcion());
        }
        
        return categoriaRepository.save(categoria);
    }

    // Eliminar una categoría
    public void eliminar(Long id) {
        Categoria categoria = obtenerPorId(id);
        categoriaRepository.delete(categoria);
    }
}
