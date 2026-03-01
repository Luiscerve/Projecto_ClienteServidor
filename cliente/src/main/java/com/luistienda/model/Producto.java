package com.luistienda.model;

/**
 * Clase que representa un producto de la tienda.
 * Se usa para enviar/recibir datos del servidor.
 */
public class Producto {

    private Long id;
    private String nombre;
    private Double precio;
    private Integer stock;
    private Categoria categoria;

    // Constructor vacío (necesario para GSON)
    public Producto() {
    }

    // Constructor sin ID
    public Producto(String nombre, Double precio, Integer stock, Categoria categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    // Constructor completo
    public Producto(Long id, String nombre, Double precio, Integer stock, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        String nombreCategoria = (categoria != null) ? categoria.getNombre() : "Sin categoría";
        return "ID: " + id + " | " + nombre + " | Precio: " + precio + "€ | Stock: " + stock + " | Categoría: " + nombreCategoria;
    }
}
