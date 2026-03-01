package com.luistienda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luistienda.exception.ResourceNotFoundException;
import com.luistienda.model.Producto;
import com.luistienda.service.ProductoService;

/**
 * Controlador REST para gestionar Productos.
 * Endpoints: /api/productos
 */
@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // GET /api/productos - Obtener todos
    @GetMapping
    public List<Producto> obtenerTodos() {
        return productoService.obtenerTodos();
    }

    // GET /api/productos/{id} - Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        try {
            Producto producto = productoService.obtenerPorId(id);
            return ResponseEntity.ok(producto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/productos/categoria/{categoriaId} - Obtener por categoría
    @GetMapping("/categoria/{categoriaId}")
    public List<Producto> obtenerPorCategoria(@PathVariable Long categoriaId) {
        return productoService.obtenerPorCategoria(categoriaId);
    }

    // POST /api/productos - Crear nuevo
    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        try {
            Producto nuevo = productoService.crear(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (IllegalArgumentException | ResourceNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT /api/productos/{id} - Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto datos) {
        try {
            Producto actualizado = productoService.actualizar(id, datos);
            return ResponseEntity.ok(actualizado);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/productos/{id} - Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            productoService.eliminar(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
