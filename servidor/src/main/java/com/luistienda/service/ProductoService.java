package com.luistienda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luistienda.exception.ResourceNotFoundException;
import com.luistienda.model.Categoria;
import com.luistienda.model.Producto;
import com.luistienda.repository.CategoriaRepository;
import com.luistienda.repository.ProductoRepository;

/**
 * Servicio con la lógica de negocio para Productos.
 */
@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Obtener todos los productos
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    // Obtener un producto por ID
    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
    }

    // Obtener productos por categoría
    public List<Producto> obtenerPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoria_Id(categoriaId);
    }

    // Crear un nuevo producto
    public Producto crear(Producto producto) {
        // Validaciones básicas
        if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (producto.getPrecio() == null || producto.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio debe ser positivo");
        }
        if (producto.getStock() == null || producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock debe ser positivo");
        }
        if (producto.getCategoria() == null || producto.getCategoria().getId() == null) {
            throw new IllegalArgumentException("La categoría es obligatoria");
        }

        // Verificar que la categoría exista
        Categoria categoria = categoriaRepository.findById(producto.getCategoria().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoría no encontrada con ID: " + producto.getCategoria().getId()));
        
        producto.setCategoria(categoria);
        return productoRepository.save(producto);
    }

    // Actualizar un producto
    public Producto actualizar(Long id, Producto datos) {
        Producto producto = obtenerPorId(id);
        
        if (datos.getNombre() != null) {
            producto.setNombre(datos.getNombre());
        }
        if (datos.getPrecio() != null) {
            producto.setPrecio(datos.getPrecio());
        }
        if (datos.getStock() != null) {
            producto.setStock(datos.getStock());
        }
        if (datos.getCategoria() != null && datos.getCategoria().getId() != null) {
            Categoria categoria = categoriaRepository.findById(datos.getCategoria().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Categoría no encontrada con ID: " + datos.getCategoria().getId()));
            producto.setCategoria(categoria);
        }
        
        return productoRepository.save(producto);
    }

    // Eliminar un producto
    public void eliminar(Long id) {
        Producto producto = obtenerPorId(id);
        productoRepository.delete(producto);
    }
}
