package com.luistienda.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luistienda.model.Categoria;
import com.luistienda.model.Producto;

/**
 * Cliente HTTP para consumir la API REST del servidor.
 * Usa HttpURLConnection de Java estándar para las peticiones HTTP.
 */
public class ApiClient {

    // URL base del servidor
    private static final String BASE_URL = "http://localhost:12345/api";
    
    // Objeto Gson para convertir entre Java y JSON
    private static final Gson gson = new Gson();

    //  MÉTODOS PARA CATEGORÍAS

    /**
     * Obtiene todas las categorías del servidor.
     */
    public static List<Categoria> obtenerCategorias() throws Exception {
        String respuesta = hacerPeticionGET(BASE_URL + "/categorias");
        return gson.fromJson(respuesta, new TypeToken<List<Categoria>>(){}.getType());
    }

    /**
     * Obtiene una categoría por su ID.
     */
    public static Categoria obtenerCategoriaById(Long id) throws Exception {
        String respuesta = hacerPeticionGET(BASE_URL + "/categorias/" + id);
        return gson.fromJson(respuesta, Categoria.class);
    }

    /**
     * Crea una nueva categoría.
     */
    public static Categoria crearCategoria(Categoria categoria) throws Exception {
        String json = gson.toJson(categoria);
        String respuesta = hacerPeticionPOST(BASE_URL + "/categorias", json);
        return gson.fromJson(respuesta, Categoria.class);
    }

    /**
     * Actualiza una categoría existente.
     */
    public static Categoria actualizarCategoria(Long id, Categoria categoria) throws Exception {
        String json = gson.toJson(categoria);
        String respuesta = hacerPeticionPUT(BASE_URL + "/categorias/" + id, json);
        return gson.fromJson(respuesta, Categoria.class);
    }

    /**
     * Elimina una categoría por su ID.
     */
    public static void eliminarCategoria(Long id) throws Exception {
        hacerPeticionDELETE(BASE_URL + "/categorias/" + id);
    }

    //  MÉTODOS PARA PRODUCTOS

    /**
     * Obtiene todos los productos del servidor.
     */
    public static List<Producto> obtenerProductos() throws Exception {
        String respuesta = hacerPeticionGET(BASE_URL + "/productos");
        return gson.fromJson(respuesta, new TypeToken<List<Producto>>(){}.getType());
    }

    /**
     * Obtiene un producto por su ID.
     */
    public static Producto obtenerProductoById(Long id) throws Exception {
        String respuesta = hacerPeticionGET(BASE_URL + "/productos/" + id);
        return gson.fromJson(respuesta, Producto.class);
    }

    /**
     * Obtiene los productos de una categoría específica.
     */
    public static List<Producto> obtenerProductosPorCategoria(Long categoriaId) throws Exception {
        String respuesta = hacerPeticionGET(BASE_URL + "/productos/categoria/" + categoriaId);
        return gson.fromJson(respuesta, new TypeToken<List<Producto>>(){}.getType());
    }

    /**
     * Crea un nuevo producto.
     */
    public static Producto crearProducto(Producto producto) throws Exception {
        String json = gson.toJson(producto);
        String respuesta = hacerPeticionPOST(BASE_URL + "/productos", json);
        return gson.fromJson(respuesta, Producto.class);
    }

    /**
     * Actualiza un producto existente.
     */
    public static Producto actualizarProducto(Long id, Producto producto) throws Exception {
        String json = gson.toJson(producto);
        String respuesta = hacerPeticionPUT(BASE_URL + "/productos/" + id, json);
        return gson.fromJson(respuesta, Producto.class);
    }

    /**
     * Elimina un producto por su ID.
     */
    public static void eliminarProducto(Long id) throws Exception {
        hacerPeticionDELETE(BASE_URL + "/productos/" + id);
    }

    //  MÉTODOS HTTP BÁSICOS

    /**
     * Realiza una petición GET y devuelve la respuesta como String.
     */
    private static String hacerPeticionGET(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestMethod("GET");
        conexion.setRequestProperty("Content-Type", "application/json");
        
        return leerRespuesta(conexion);
    }

    /**
     * Realiza una petición POST con un cuerpo JSON.
     */
    private static String hacerPeticionPOST(String urlString, String jsonBody) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestMethod("POST");
        conexion.setRequestProperty("Content-Type", "application/json");
        conexion.setDoOutput(true);
        
        // Enviar el cuerpo JSON
        try (OutputStream os = conexion.getOutputStream()) {
            os.write(jsonBody.getBytes("UTF-8"));
        }
        
        return leerRespuesta(conexion);
    }

    /**
     * Realiza una petición PUT con un cuerpo JSON.
     */
    private static String hacerPeticionPUT(String urlString, String jsonBody) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestMethod("PUT");
        conexion.setRequestProperty("Content-Type", "application/json");
        conexion.setDoOutput(true);
        
        // Enviar el cuerpo JSON
        try (OutputStream os = conexion.getOutputStream()) {
            os.write(jsonBody.getBytes("UTF-8"));
        }
        
        return leerRespuesta(conexion);
    }

    /**
     * Realiza una petición DELETE.
     */
    private static void hacerPeticionDELETE(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestMethod("DELETE");
        conexion.setRequestProperty("Content-Type", "application/json");
        
        int codigoRespuesta = conexion.getResponseCode();
        if (codigoRespuesta < 200 || codigoRespuesta >= 300) {
            throw new Exception("Error en DELETE. Código: " + codigoRespuesta);
        }
        conexion.disconnect();
    }

    /**
     * Lee la respuesta de una conexión HTTP.
     */
    private static String leerRespuesta(HttpURLConnection conexion) throws Exception {
        int codigoRespuesta = conexion.getResponseCode();
        
        if (codigoRespuesta >= 200 && codigoRespuesta < 300) {
            // Leer respuesta exitosa
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(conexion.getInputStream(), "UTF-8"));
            StringBuilder respuesta = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null) {
                respuesta.append(linea);
            }
            reader.close();
            conexion.disconnect();
            return respuesta.toString();
        } else {
            conexion.disconnect();
            throw new Exception("Error en la petición. Código: " + codigoRespuesta);
        }
    }
}
