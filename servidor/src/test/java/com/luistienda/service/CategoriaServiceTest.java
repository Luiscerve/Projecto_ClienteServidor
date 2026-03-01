package com.luistienda.service;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.luistienda.model.Categoria;
import com.luistienda.repository.CategoriaRepository;

/**
 * Tests de integracion para CategoriaService.
 * Conecta a la base de datos MySQL real.
 * 
 * IMPORTANTE: MySQL debe estar activo para ejecutar estos tests.
 */
@SpringBootTest
public class CategoriaServiceTest {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Variable para guardar el ID de la categoria creada en los tests
    private Long categoriaTestId;

    /**
     * Limpia los datos de prueba despues de cada test.
     */
    @AfterEach
    void limpiarDatos() {
        if (categoriaTestId != null) {
            try {
                categoriaRepository.deleteById(categoriaTestId);
            } catch (Exception e) {
                // Ignorar si ya fue eliminada
            }
            categoriaTestId = null;
        }
    }

    /**
     * Test: Crear una categoria en MySQL.
     */
    @Test
    void testCrearCategoria() {
        // Crear categoria
        Categoria categoria = new Categoria("TestCategoria", "Descripcion de prueba");
        Categoria creada = categoriaService.crear(categoria);
        
        // Guardar ID para limpieza
        categoriaTestId = creada.getId();
        
        // Verificar que se creo correctamente
        assertNotNull(creada.getId());
        assertEquals("TestCategoria", creada.getNombre());
    }

    /**
     * Test: Obtener todas las categorias de MySQL.
     */
    @Test
    void testObtenerTodas() {
        // Crear una categoria de prueba
        Categoria categoria = new Categoria("TestListar", "Para listar");
        Categoria creada = categoriaService.crear(categoria);
        categoriaTestId = creada.getId();
        
        // Obtener todas
        List<Categoria> lista = categoriaService.obtenerTodas();
        
        // Verificar que hay al menos una
        assertFalse(lista.isEmpty());
    }

    /**
     * Test: Obtener una categoria por ID de MySQL.
     */
    @Test
    void testObtenerPorId() {
        // Crear categoria
        Categoria categoria = new Categoria("TestBuscar", "Para buscar por ID");
        Categoria creada = categoriaService.crear(categoria);
        categoriaTestId = creada.getId();
        
        // Buscar por ID
        Categoria encontrada = categoriaService.obtenerPorId(categoriaTestId);
        
        // Verificar
        assertEquals("TestBuscar", encontrada.getNombre());
    }

    /**
     * Test: Actualizar una categoria en MySQL.
     */
    @Test
    void testActualizarCategoria() {
        // Crear categoria
        Categoria categoria = new Categoria("TestActualizar", "Descripcion original");
        Categoria creada = categoriaService.crear(categoria);
        categoriaTestId = creada.getId();
        
        // Modificar
        creada.setDescripcion("Descripcion modificada");
        Categoria actualizada = categoriaService.actualizar(categoriaTestId, creada);
        
        // Verificar
        assertEquals("Descripcion modificada", actualizada.getDescripcion());
    }

    /**
     * Test: Eliminar una categoria de MySQL.
     */
    @Test
    void testEliminarCategoria() {
        // Crear categoria
        Categoria categoria = new Categoria("TestEliminar", "Para eliminar");
        Categoria creada = categoriaService.crear(categoria);
        Long id = creada.getId();
        
        // Eliminar
        categoriaService.eliminar(id);
        
        // Verificar que ya no existe
        assertFalse(categoriaRepository.existsById(id));
        
        // No necesita limpieza porque ya se elimino
        categoriaTestId = null;
    }
}
