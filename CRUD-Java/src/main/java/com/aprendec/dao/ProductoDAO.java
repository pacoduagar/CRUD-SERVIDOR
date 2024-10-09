package com.aprendec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aprendec.conexion.Conexion;
import com.aprendec.model.Producto;

/**
 * Clase DAO para manejar las operaciones relacionadas con el producto en la base de datos.
 */
public class ProductoDAO {
    private Connection connection;
    private PreparedStatement statement;
    private boolean estadoOperacion;

    /**
     * Guarda un producto en la base de datos.
     *
     * @param producto el objeto Producto que se desea guardar.
     * @return true si el producto se guarda correctamente; false en caso contrario.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public boolean guardar(Producto producto) throws SQLException {
        String sql = null;
        estadoOperacion = false;
        connection = obtenerConexion();

        try {
            connection.setAutoCommit(false);
            sql = "INSERT INTO productos (id, nombre, cantidad, precio, fecha_crear, fecha_actualizar) VALUES(?,?,?,?,?,?)";
            statement = connection.prepareStatement(sql);

            statement.setString(1, null);
            statement.setString(2, producto.getNombre());
            statement.setDouble(3, producto.getCantidad());
            statement.setDouble(4, producto.getPrecio());
            statement.setTimestamp(5, producto.getFechaCrear());
            statement.setTimestamp(6, producto.getFechaActualizar());

            estadoOperacion = statement.executeUpdate() > 0;

            connection.commit();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }

        return estadoOperacion;
    }

    /**
     * Obtiene un producto por su nombre.
     *
     * @param nombre el nombre del producto a buscar.
     * @return el objeto Producto si se encuentra; null en caso contrario.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public Producto obtenerProductoPorNombre(String nombre) throws SQLException {
        ResultSet resultSet = null;
        Producto producto = null;

        String sql = "SELECT * FROM productos WHERE nombre = ?";
        connection = obtenerConexion();

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, nombre);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                producto = new Producto();
                producto.setId(resultSet.getInt("id"));
                producto.setNombre(resultSet.getString("nombre"));
                producto.setCantidad(resultSet.getDouble("cantidad"));
                producto.setPrecio(resultSet.getDouble("precio"));
                producto.setFechaCrear(resultSet.getTimestamp("fecha_crear"));
                producto.setFechaActualizar(resultSet.getTimestamp("fecha_actualizar"));
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return producto;
    }

    /**
     * Edita un producto existente en la base de datos.
     *
     * @param producto el objeto Producto que contiene la información actualizada.
     * @return true si el producto se edita correctamente; false en caso contrario.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public boolean editar(Producto producto) throws SQLException {
        String sql = null;
        estadoOperacion = false;
        connection = obtenerConexion();
        try {
            connection.setAutoCommit(false);
            sql = "UPDATE productos SET nombre=?, cantidad=?, precio=?, fecha_actualizar=? WHERE id=?";
            statement = connection.prepareStatement(sql);

            statement.setString(1, producto.getNombre());
            statement.setDouble(2, producto.getCantidad());
            statement.setDouble(3, producto.getPrecio());
            statement.setTimestamp(4, producto.getFechaActualizar());
            statement.setInt(5, producto.getId());

            estadoOperacion = statement.executeUpdate() > 0;
            connection.commit();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }

        return estadoOperacion;
    }

    /**
     * Elimina un producto de la base de datos por su ID.
     *
     * @param idProducto el ID del producto a eliminar.
     * @return true si el producto se elimina correctamente; false en caso contrario.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public boolean eliminar(int idProducto) throws SQLException {
        String sql = null;
        estadoOperacion = false;
        connection = obtenerConexion();
        try {
            connection.setAutoCommit(false);
            sql = "DELETE FROM productos WHERE id=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idProducto);

            estadoOperacion = statement.executeUpdate() > 0;
            connection.commit();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }

        return estadoOperacion;
    }

    /**
     * Obtiene una lista de todos los productos en la base de datos.
     *
     * @return una lista de objetos Producto.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public List<Producto> obtenerProductos() throws SQLException {
        ResultSet resultSet = null;
        List<Producto> listaProductos = new ArrayList<>();

        String sql = null;
        estadoOperacion = false;
        connection = obtenerConexion();

        try {
            sql = "SELECT * FROM productos";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Producto p = new Producto();
                p.setId(resultSet.getInt(1));
                p.setNombre(resultSet.getString(2));
                p.setCantidad(resultSet.getDouble(3));
                p.setPrecio(resultSet.getDouble(4));
                p.setFechaCrear(resultSet.getTimestamp(5));
                p.setFechaActualizar(resultSet.getTimestamp(6));
                listaProductos.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaProductos;
    }

    /**
     * Obtiene un producto de la base de datos por su ID.
     *
     * @param idProducto el ID del producto a buscar.
     * @return el objeto Producto si se encuentra; un objeto Producto vacío en caso contrario.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public Producto obtenerProducto(int idProducto) throws SQLException {
        ResultSet resultSet = null;
        Producto p = new Producto();

        String sql = null;
        estadoOperacion = false;
        connection = obtenerConexion();

        try {
            sql = "SELECT * FROM productos WHERE id =?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idProducto);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                p.setId(resultSet.getInt(1));
                p.setNombre(resultSet.getString(2));
                p.setCantidad(resultSet.getDouble(3));
                p.setPrecio(resultSet.getDouble(4));
                p.setFechaCrear(resultSet.getTimestamp(5));
                p.setFechaActualizar(resultSet.getTimestamp(6));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return p;
    }

    /**
     * Obtiene una conexión a la base de datos.
     *
     * @return la conexión a la base de datos.
     * @throws SQLException si ocurre un error al obtener la conexión.
     */
    private Connection obtenerConexion() throws SQLException {
        return Conexion.getConnection();
    }

}

