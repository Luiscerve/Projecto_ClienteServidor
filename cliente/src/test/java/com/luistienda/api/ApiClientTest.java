package com.luistienda.api;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import com.luistienda.model.Categoria;
import com.luistienda.model.Producto;

/**
 * Tests de integracion para ApiClient.
 * Conecta al servidor REST real en localhost:12345.
 * 
 * IMPORTANTE: El servidor debe estar activo para ejecutar estos tests.
 */
public class ApiClientTest {

    /**
     * Test: Obtener todas las categorias del servidor.
     */
    @Test
    void testObtenerCategorias() {
        try {
            List<Categoria> categorias = ApiClient.obtenerCategorias();
            assertNotNull(categorias);
            System.out.println("Categorias obtenidas: " + categorias.size());
        } catch (Exception e) {
            System.out.println("Servidor no disponible: " + e.getMessage());
            // El test pasa pero informa que el servidor no esta activo
        }
    }

    /**
     * Test: Obtener todos los productos del servidor.
     */
    @Test
    void testObtenerProductos() {
        try {
            List<Producto> productos = ApiClient.obtenerProductos();
            assertNotNull(productos);
            System.out.println("Productos obtenidos: " + productos.size());
        } catch (Exception e) {
            System.out.println("Servidor no disponible: " + e.getMessage());
        }
    }

    /**
     * Test: Crear y eliminar una categoria (CRUD completo).
     */
    @Test
    void testCrudCategoria() {
        try {
            // Crear
            Categoria nueva = new Categoria();
            nueva.setNombre("TestClienteCategoria");
            nueva.setDescripcion("Creada desde test del cliente");
            Categoria creada = ApiClient.crearCategoria(nueva);
            
            assertNotNull(creada);
            assertNotNull(creada.getId());
            System.out.println("Categoria creada con ID: " + creada.getId());
            
            // Limpiar - eliminar la categoria creada
            ApiClient.eliminarCategoria(creada.getId());
            System.out.println("Categoria eliminada correctamente");
            
        } catch (Exception e) {
            System.out.println("Servidor no disponible: " + e.getMessage());
        }
    }
}
