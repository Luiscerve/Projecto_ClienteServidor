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
import com.luistienda.model.Categoria;
import com.luistienda.service.CategoriaService;

/**
 * Controlador REST para gestionar Categorías.
 * Endpoints: /api/categorias
 */
@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    // GET /api/categorias - Obtener todas
    @GetMapping
    public List<Categoria> obtenerTodas() {
        return categoriaService.obtenerTodas();
    }

    // GET /api/categorias/{id} - Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerPorId(@PathVariable Long id) {
        try {
            Categoria categoria = categoriaService.obtenerPorId(id);
            return ResponseEntity.ok(categoria);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/categorias - Crear nueva
    @PostMapping
    public ResponseEntity<Categoria> crear(@RequestBody Categoria categoria) {
        try {
            Categoria nueva = categoriaService.crear(categoria);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT /api/categorias/{id} - Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(@PathVariable Long id, @RequestBody Categoria datos) {
        try {
            Categoria actualizada = categoriaService.actualizar(id, datos);
            return ResponseEntity.ok(actualizada);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/categorias/{id} - Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            categoriaService.eliminar(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
