package com.aprendec.model;

import java.sql.Timestamp;

/**
 * Clase que representa un producto en un sistema.
 * Contiene información sobre el producto como su ID, nombre, cantidad,
 * precio y las fechas de creación y actualización.
 */
public class Producto {

    /** ID único del producto */
    private int id;

    /** Nombre del producto */
    private String nombre;

    /** Cantidad disponible del producto */
    private double cantidad;

    /** Precio del producto */
    private double precio;

    /** Fecha y hora en que se creó el producto */
    private Timestamp fechaCrear;

    /** Fecha y hora en que se actualizó el producto por última vez */
    private Timestamp fechaActualizar;

    /**
     * Constructor que inicializa un nuevo producto con los parámetros especificados.
     *
     * @param id ID del producto
     * @param nombre Nombre del producto
     * @param cantidad Cantidad disponible del producto
     * @param precio Precio del producto
     * @param fechaCrear Fecha y hora de creación del producto
     * @param fechaActualizar Fecha y hora de la última actualización del producto
     */
    public Producto(int id, String nombre, double cantidad, double precio, Timestamp fechaCrear, Timestamp fechaActualizar) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.fechaCrear = fechaCrear;
        this.fechaActualizar = fechaActualizar;
    }

    /**
     * Constructor por defecto que inicializa un nuevo producto sin valores.
     */
    public Producto() {
        // Constructor por defecto
    }

    /**
     * Obtiene el ID del producto.
     *
     * @return ID del producto
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID del producto.
     *
     * @param id ID del producto
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return Nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param nombre Nombre del producto
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la cantidad del producto.
     *
     * @return Cantidad del producto
     */
    public double getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad del producto.
     *
     * @param cantidad Cantidad del producto
     */
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el precio del producto.
     *
     * @return Precio del producto
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del producto.
     *
     * @param precio Precio del producto
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene la fecha de creación del producto.
     *
     * @return Fecha de creación del producto
     */
    public Timestamp getFechaCrear() {
        return fechaCrear;
    }

    /**
     * Establece la fecha de creación del producto.
     *
     * @param fechaCrear Fecha de creación del producto
     */
    public void setFechaCrear(Timestamp fechaCrear) {
        this.fechaCrear = fechaCrear;
    }

    /**
     * Obtiene la fecha de actualización del producto.
     *
     * @return Fecha de actualización del producto
     */
    public Timestamp getFechaActualizar() {
        return fechaActualizar;
    }

    /**
     * Establece la fecha de actualización del producto.
     *
     * @param fechaActualizar Fecha de actualización del producto
     */
    public void setFechaActualizar(Timestamp fechaActualizar) {
        this.fechaActualizar = fechaActualizar;
    }

    /**
     * Devuelve una representación en forma de cadena del producto.
     *
     * @return Cadena que representa el producto
     */
    @Override
    public String toString() {
        return "Producto [id=" + id + ", nombre=" + nombre + ", cantidad=" + cantidad + ", precio=" + precio
                + ", fechaCrear=" + fechaCrear + ", fechaActualizar=" + fechaActualizar + "]";
    }
}
