package com.luistienda;

import java.util.List;
import java.util.Scanner;

import com.luistienda.api.ApiClient;
import com.luistienda.model.Categoria;
import com.luistienda.model.Producto;

/**
 * Aplicación cliente de consola para gestionar la tienda.
 * Se conecta al servidor REST en el puerto 12345.
 */
public class ClienteApp {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println();
        System.out.println("   CLIENTE TIENDA LUIS   ");
        System.out.println("Conectando a servidor en puerto 12345...");
        System.out.println();

        boolean salir = false;
        while (!salir) {
            mostrarMenuPrincipal();
            int opcion = leerEntero();

            switch (opcion) {
                case 1:
                    menuCategorias();
                    break;
                case 2:
                    menuProductos();
                    break;
                case 0:
                    salir = true;
                    System.out.println("\n¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
        scanner.close();
    }

    // MENÚS 

    private static void mostrarMenuPrincipal() {
        System.out.println("MENÚ PRINCIPAL-TIENDA LUIS");
        System.out.println("1. Gestionar Categorías");
        System.out.println("2. Gestionar Productos");
        System.out.println("0. Salir");
        System.out.print("Elige opción: ");
    }

    private static void menuCategorias() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n CATEGORÍAS ");
            System.out.println("1. Ver todas");
            System.out.println("2. Crear nueva");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Actualizar");
            System.out.println("5. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Elige opción: ");

            int opcion = leerEntero();
            try {
                switch (opcion) {
                    case 1: listarCategorias(); break;
                    case 2: crearCategoria(); break;
                    case 3: buscarCategoria(); break;
                    case 4: actualizarCategoria(); break;
                    case 5: eliminarCategoria(); break;
                    case 0: volver = true; break;
                    default: System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void menuProductos() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n PRODUCTOS");
            System.out.println("1. Ver todos");
            System.out.println("2. Crear nuevo");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Buscar por categoría");
            System.out.println("5. Actualizar");
            System.out.println("6. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Elige opción: ");

            int opcion = leerEntero();
            try {
                switch (opcion) {
                    case 1: listarProductos(); break;
                    case 2: crearProducto(); break;
                    case 3: buscarProducto(); break;
                    case 4: buscarPorCategoria(); break;
                    case 5: actualizarProducto(); break;
                    case 6: eliminarProducto(); break;
                    case 0: volver = true; break;
                    default: System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // OPERACIONES CATEGORÍAS 

    private static void listarCategorias() throws Exception {
        System.out.println("\n>> LISTA DE CATEGORÍAS:");
        List<Categoria> lista = ApiClient.obtenerCategorias();
        if (lista.isEmpty()) {
            System.out.println("No hay categorías.");
        } else {
            for (Categoria c : lista) {
                System.out.println(c);
            }
        }
    }

    private static void crearCategoria() throws Exception {
        System.out.println("\n>> CREAR CATEGORÍA");
        System.out.print("Nombre: ");
        String nombre = leerTexto();
        System.out.print("Descripción: ");
        String descripcion = leerTexto();

        Categoria nueva = new Categoria(nombre, descripcion);
        Categoria creada = ApiClient.crearCategoria(nueva);
        System.out.println("Categoría creada con ID: " + creada.getId());
    }

    private static void buscarCategoria() throws Exception {
        System.out.print("ID de la categoría: ");
        Long id = leerLong();
        Categoria c = ApiClient.obtenerCategoriaById(id);
        System.out.println("Encontrada: " + c);
    }

    private static void actualizarCategoria() throws Exception {
        System.out.print("ID de la categoría a actualizar: ");
        Long id = leerLong();
        System.out.print("Nuevo nombre: ");
        String nombre = leerTexto();
        System.out.print("Nueva descripción: ");
        String descripcion = leerTexto();

        Categoria c = new Categoria(nombre, descripcion);
        Categoria actualizada = ApiClient.actualizarCategoria(id, c);
        System.out.println("Actualizada: " + actualizada);
    }

    private static void eliminarCategoria() throws Exception {
        System.out.print("ID de la categoría a eliminar: ");
        Long id = leerLong();
        ApiClient.eliminarCategoria(id);
        System.out.println("Categoría eliminada correctamente.");
    }

    //  OPERACIONES PRODUCTOS 

    private static void listarProductos() throws Exception {
        System.out.println("\n>> LISTA DE PRODUCTOS:");
        List<Producto> lista = ApiClient.obtenerProductos();
        if (lista.isEmpty()) {
            System.out.println("No hay productos.");
        } else {
            for (Producto p : lista) {
                System.out.println(p);
            }
        }
    }

    private static void crearProducto() throws Exception {
        System.out.println("\n>> CREAR PRODUCTO");
        System.out.print("Nombre: ");
        String nombre = leerTexto();
        System.out.print("Precio: ");
        Double precio = leerDouble();
        System.out.print("Stock: ");
        Integer stock = leerEntero();
        System.out.print("ID de categoría: ");
        Long catId = leerLong();

        Categoria cat = new Categoria();
        cat.setId(catId);
        Producto nuevo = new Producto(nombre, precio, stock, cat);
        Producto creado = ApiClient.crearProducto(nuevo);
        System.out.println("Producto creado con ID: " + creado.getId());
    }

    private static void buscarProducto() throws Exception {
        System.out.print("ID del producto: ");
        Long id = leerLong();
        Producto p = ApiClient.obtenerProductoById(id);
        System.out.println("Encontrado: " + p);
    }

    private static void buscarPorCategoria() throws Exception {
        System.out.print("ID de la categoría: ");
        Long catId = leerLong();
        List<Producto> lista = ApiClient.obtenerProductosPorCategoria(catId);
        if (lista.isEmpty()) {
            System.out.println("No hay productos en esta categoría.");
        } else {
            for (Producto p : lista) {
                System.out.println(p);
            }
        }
    }

    private static void actualizarProducto() throws Exception {
        System.out.print("ID del producto a actualizar: ");
        Long id = leerLong();
        System.out.print("Nuevo nombre: ");
        String nombre = leerTexto();
        System.out.print("Nuevo precio: ");
        Double precio = leerDouble();
        System.out.print("Nuevo stock: ");
        Integer stock = leerEntero();
        System.out.print("ID de categoría: ");
        Long catId = leerLong();

        Categoria cat = new Categoria();
        cat.setId(catId);
        Producto p = new Producto(nombre, precio, stock, cat);
        Producto actualizado = ApiClient.actualizarProducto(id, p);
        System.out.println("Actualizado: " + actualizado);
    }

    private static void eliminarProducto() throws Exception {
        System.out.print("ID del producto a eliminar: ");
        Long id = leerLong();
        ApiClient.eliminarProducto(id);
        System.out.println("Producto eliminado correctamente.");
    }

    //  MÉTODOS AUXILIARES PARA LEER DATOS 

    private static String leerTexto() {
        return scanner.nextLine();
    }

    private static int leerEntero() {
        try {
            int valor = Integer.parseInt(scanner.nextLine());
            return valor;
        } catch (Exception e) {
            return -1;
        }
    }

    private static Long leerLong() {
        try {
            return Long.parseLong(scanner.nextLine());
        } catch (Exception e) {
            return -1L;
        }
    }

    private static Double leerDouble() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (Exception e) {
            return 0.0;
        }
    }
}
