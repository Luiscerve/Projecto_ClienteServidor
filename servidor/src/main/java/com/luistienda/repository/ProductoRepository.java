package com.luistienda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luistienda.model.Producto;

/**
 * Repositorio para acceder a datos de Producto.
 * Spring Data JPA implementa automáticamente los métodos CRUD.
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    // Método personalizado para buscar productos por ID de categoría
    List<Producto> findByCategoria_Id(Long categoriaId);
}
