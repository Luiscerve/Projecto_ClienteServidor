package com.luistienda.service;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.luistienda.model.Categoria;
import com.luistienda.model.Producto;
import com.luistienda.repository.CategoriaRepository;
import com.luistienda.repository.ProductoRepository;

/**
 * Tests de integracion para ProductoService.
 * Conecta a la base de datos MySQL real.
 * 
 * IMPORTANTE: MySQL debe estar activo para ejecutar estos tests.
 */
@SpringBootTest
public class ProductoServiceTest {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Variables para guardar IDs de datos de prueba
    private Long productoTestId;
    private Long categoriaTestId;

    /**
     * Crea una categoria de prueba antes de cada test.
     */
    @BeforeEach
    void crearCategoriaBase() {
        Categoria cat = new Categoria("CategoriaTest", "Para tests de productos");
        Categoria creada = categoriaService.crear(cat);
        categoriaTestId = creada.getId();
    }

    /**
     * Limpia los datos de prueba despues de cada test.
     */
    @AfterEach
    void limpiarDatos() {
        // Primero eliminar productos (tienen FK a categoria)
        if (productoTestId != null) {
            try {
                productoRepository.deleteById(productoTestId);
            } catch (Exception e) {
                // Ignorar
            }
            productoTestId = null;
        }
        // Luego eliminar categoria
        if (categoriaTestId != null) {
            try {
                categoriaRepository.deleteById(categoriaTestId);
            } catch (Exception e) {
                // Ignorar
            }
            categoriaTestId = null;
        }
    }

    /**
     * Test: Crear un producto en MySQL.
     */
    @Test
    void testCrearProducto() {
        // Obtener la categoria de prueba
        Categoria cat = categoriaService.obtenerPorId(categoriaTestId);
        
        // Crear producto
        Producto producto = new Producto("TestProducto", 99.99, 10, cat);
        Producto creado = productoService.crear(producto);
        
        // Guardar ID para limpieza
        productoTestId = creado.getId();
        
        // Verificar
        assertNotNull(creado.getId());
        assertEquals("TestProducto", creado.getNombre());
        assertEquals(99.99, creado.getPrecio());
    }

    /**
     * Test: Obtener todos los productos de MySQL.
     */
    @Test
    void testObtenerTodos() {
        // Crear un producto de prueba
        Categoria cat = categoriaService.obtenerPorId(categoriaTestId);
        Producto producto = new Producto("TestListar", 50.0, 5, cat);
        Producto creado = productoService.crear(producto);
        productoTestId = creado.getId();
        
        // Obtener todos
        List<Producto> lista = productoService.obtenerTodos();
        
        // Verificar que hay al menos uno
        assertFalse(lista.isEmpty());
    }

    /**
     * Test: Obtener un producto por ID de MySQL.
     */
    @Test
    void testObtenerPorId() {
        // Crear producto
        Categoria cat = categoriaService.obtenerPorId(categoriaTestId);
        Producto producto = new Producto("TestBuscar", 75.0, 3, cat);
        Producto creado = productoService.crear(producto);
        productoTestId = creado.getId();
        
        // Buscar por ID
        Producto encontrado = productoService.obtenerPorId(productoTestId);
        
        // Verificar
        assertEquals("TestBuscar", encontrado.getNombre());
    }

    /**
     * Test: Obtener productos por categoria de MySQL.
     */
    @Test
    void testObtenerPorCategoria() {
        // Crear producto
        Categoria cat = categoriaService.obtenerPorId(categoriaTestId);
        Producto producto = new Producto("TestPorCategoria", 100.0, 8, cat);
        Producto creado = productoService.crear(producto);
        productoTestId = creado.getId();
        
        // Buscar por categoria
        List<Producto> lista = productoService.obtenerPorCategoria(categoriaTestId);
        
        // Verificar que hay al menos uno
        assertFalse(lista.isEmpty());
    }

    /**
     * Test: Actualizar un producto en MySQL.
     */
    @Test
    void testActualizarProducto() {
        // Crear producto
        Categoria cat = categoriaService.obtenerPorId(categoriaTestId);
        Producto producto = new Producto("TestActualizar", 100.0, 10, cat);
        Producto creado = productoService.crear(producto);
        productoTestId = creado.getId();
        
        // Modificar
        creado.setPrecio(150.0);
        creado.setStock(20);
        Producto actualizado = productoService.actualizar(productoTestId, creado);
        
        // Verificar
        assertEquals(150.0, actualizado.getPrecio());
        assertEquals(20, actualizado.getStock());
    }

    /**
     * Test: Eliminar un producto de MySQL.
     */
    @Test
    void testEliminarProducto() {
        // Crear producto
        Categoria cat = categoriaService.obtenerPorId(categoriaTestId);
        Producto producto = new Producto("TestEliminar", 50.0, 5, cat);
        Producto creado = productoService.crear(producto);
        Long id = creado.getId();
        
        // Eliminar
        productoService.eliminar(id);
        
        // Verificar que ya no existe
        assertFalse(productoRepository.existsById(id));
        
        // No necesita limpieza porque ya se elimino
        productoTestId = null;
    }
}
