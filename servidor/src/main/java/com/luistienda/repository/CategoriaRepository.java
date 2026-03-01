package com.luistienda.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luistienda.model.Categoria;

/**
 * Repositorio para acceder a datos de Categoria.
 * Spring Data JPA implementa automáticamente los métodos CRUD.
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    // Método personalizado para buscar por nombre
    Optional<Categoria> findByNombre(String nombre);
}
