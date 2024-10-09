package com.aprendec.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aprendec.dao.ProductoDAO;
import com.aprendec.model.Producto;

/**
 * Servlet implementation class ProductoController
 * <p>
 * Este servlet se encarga de administrar las peticiones relacionadas con la tabla de productos.
 * Permite realizar operaciones como crear, listar, editar y eliminar productos.
 * </p>
 */
@WebServlet(description = "administra peticiones para la tabla productos", urlPatterns = { "/productos" })
public class ProductoController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor por defecto para ProductoController.
     */
    public ProductoController() {
        super();
    }

    /**
     * Método que maneja las peticiones HTTP GET.
     * <p>
     * Dependiendo del parámetro "opcion", este método puede crear, listar, editar o eliminar productos.
     * </p>
     *
     * @param request  la solicitud HTTP
     * @param response la respuesta HTTP
     * @throws ServletException si ocurre un error en la servlet
     * @throws IOException      si ocurre un error de entrada/salida
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String opcion = request.getParameter("opcion");

        if (opcion.equals("crear")) {
            System.out.println("Usted ha presionado la opción crear");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/crear.jsp");
            requestDispatcher.forward(request, response);
        } else if (opcion.equals("listar")) {

            ProductoDAO productoDAO = new ProductoDAO();
            List<Producto> lista = new ArrayList<>();
            try {
                lista = productoDAO.obtenerProductos();
                for (Producto producto : lista) {
                    System.out.println(producto);
                }

                request.setAttribute("lista", lista);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/listar.jsp");
                requestDispatcher.forward(request, response);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            System.out.println("Usted ha presionado la opción listar");
        } else if (opcion.equals("meditar")) {
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("Editar id: " + id);
            ProductoDAO productoDAO = new ProductoDAO();
            Producto p = new Producto();
            try {
                p = productoDAO.obtenerProducto(id);
                System.out.println(p);
                request.setAttribute("producto", p);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/editar.jsp");
                requestDispatcher.forward(request, response);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if (opcion.equals("eliminar")) {
            ProductoDAO productoDAO = new ProductoDAO();
            int id = Integer.parseInt(request.getParameter("id"));
            List<Producto> lista = new ArrayList<>();
            try {
                productoDAO.eliminar(id);
                lista = productoDAO.obtenerProductos();
                for (Producto producto : lista) {
                    System.out.println(producto);
                }
                request.setAttribute("lista", lista);
                System.out.println("Registro eliminado satisfactoriamente...");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/listar.jsp");
                requestDispatcher.forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Método que maneja las peticiones HTTP POST.
     * <p>
     * Dependiendo del parámetro "opcion", este método puede guardar o editar un producto.
     * También realiza la validación de entrada para los campos de nombre, cantidad y precio.
     * </p>
     *
     * @param request  la solicitud HTTP
     * @param response la respuesta HTTP
     * @throws ServletException si ocurre un error en la servlet
     * @throws IOException      si ocurre un error de entrada/salida
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String opcion = request.getParameter("opcion");
        Date fechaActual = new Date();

        if (opcion.equals("guardar")) {
            ProductoDAO productoDAO = new ProductoDAO();
            String nombre = request.getParameter("nombre");
            String cantidadStr = request.getParameter("cantidad");
            String precioStr = request.getParameter("precio");

            // Validación de entrada
            String errorMessage = null;

            // Verificar si 'nombre' está vacío
            if (nombre == null || nombre.trim().isEmpty()) {
                errorMessage = "El nombre del producto es obligatorio.";
            }

            // Validar 'cantidad' para asegurar que es un número válido
            double cantidad = 0;
            try {
                cantidad = Double.parseDouble(cantidadStr);
                if (cantidad <= 0) {
                    errorMessage = "La cantidad debe ser un número mayor que cero.";
                }
            } catch (NumberFormatException e) {
                if (errorMessage == null) { // Solo establecer error si no se ha establecido ya
                    errorMessage = "La cantidad debe ser un número válido.";
                }
            }

            // Validar 'precio' para asegurar que es un número válido
            double precio = 0;
            try {
                precio = Double.parseDouble(precioStr);
                if (precio <= 0) {
                    errorMessage = "El precio debe ser un número mayor que cero.";
                }
            } catch (NumberFormatException e) {
                if (errorMessage == null) { // Solo establecer error si no se ha establecido ya
                    errorMessage = "El precio debe ser un número válido.";
                }
            }

            try {
                // Si hay errores de validación, mostrar un mensaje de error
                if (errorMessage != null) {
                    List<Producto> lista = new ArrayList<>();
                    try {
                        lista = productoDAO.obtenerProductos();
                        for (Producto producto : lista) {
                            System.out.println(producto);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    String sourcePage = request.getParameter("sourcePage");
                    request.setAttribute("lista", lista);
                    request.setAttribute("message", errorMessage);
                    request.setAttribute("messageType", "error");
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/" + sourcePage);
                    requestDispatcher.forward(request, response);

                    return; // Detener la ejecución si la validación falla
                }

                // Verificar si ya existe un producto con el mismo nombre
                Producto existingProduct = productoDAO.obtenerProductoPorNombre(nombre);
                if (existingProduct != null) {
                    request.setAttribute("message", "El producto ya existe.");
                    request.setAttribute("messageType", "error");
                } else {
                    // Crear un nuevo producto
                    Producto producto = new Producto();
                    producto.setNombre(nombre);
                    producto.setCantidad(cantidad);
                    producto.setPrecio(precio);
                    producto.setFechaCrear(new java.sql.Timestamp(fechaActual.getTime()));
                    producto.setFechaActualizar(null); // Inicialmente, no hay fecha de actualización

                    boolean isSaved = productoDAO.guardar(producto);

                    if (isSaved) {
                        request.setAttribute("message", "Producto guardado satisfactoriamente.");
                        request.setAttribute("messageType", "success");
                    } else {
                        request.setAttribute("message", "Error al guardar el producto.");
                        request.setAttribute("messageType", "error");
                    }
                }

                List<Producto> lista = new ArrayList<>();
                try {
                    lista = productoDAO.obtenerProductos();
                    for (Producto producto : lista) {
                        System.out.println(producto);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                String sourcePage = request.getParameter("sourcePage");
                request.setAttribute("lista", lista);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/" + sourcePage);
                requestDispatcher.forward(request, response);

            } catch (SQLException e) {
                request.setAttribute("message", "Error en la base de datos.");
                request.setAttribute("messageType", "error");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
                requestDispatcher.forward(request, response);
                e.printStackTrace();
            }
        } else if (opcion.equals("editar")) {
            ProductoDAO productoDAO = new ProductoDAO();
            int id = Integer.parseInt(request.getParameter("id"));
            String nombre = request.getParameter("nombre");
            String cantidadStr = request.getParameter("cantidad");
            String precioStr = request.getParameter("precio");

            // Validación de entrada
            String errorMessage = null;

            // Verificar si 'nombre' está vacío
            if (nombre == null || nombre.trim().isEmpty()) {
                errorMessage = "El nombre del producto es obligatorio.";
            }

            // Validar 'cantidad' para asegurar que es un número válido
            double cantidad = 0;
            try {
                cantidad = Double.parseDouble(cantidadStr);
                if (cantidad <= 0) {
                    errorMessage = "La cantidad debe ser un número mayor que cero.";
                }
            } catch (NumberFormatException e) {
                if (errorMessage == null) {  // Solo establecer error si no se ha establecido ya
                    errorMessage = "La cantidad debe ser un número válido.";
                }
            }

            // Validar 'precio' para asegurar que es un número válido
            double precio = 0;
            try {
                precio = Double.parseDouble(precioStr);
                if (precio <= 0) {
                    errorMessage = "El precio debe ser un número mayor que cero.";
                }
            } catch (NumberFormatException e) {
                if (errorMessage == null) {  // Solo establecer error si no se ha establecido ya
                    errorMessage = "El precio debe ser un número válido.";
                }
            }

            try {
                // Si hay errores de validación, mostrar un mensaje de error
                if (errorMessage != null) {
                    request.setAttribute("message", errorMessage);
                    request.setAttribute("messageType", "error");
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/editar.jsp");
                    requestDispatcher.forward(request, response);
                    return;  // Detener la ejecución si la validación falla
                }

                // Proceder a actualizar el producto
                Producto producto = productoDAO.obtenerProducto(id);
                if (producto != null) {
                    producto.setNombre(nombre);
                    producto.setCantidad(cantidad);
                    producto.setPrecio(precio);
                    producto.setFechaActualizar(new java.sql.Timestamp(fechaActual.getTime()));

                    boolean isUpdated = productoDAO.editar(producto);

                    if (isUpdated) {
                        request.setAttribute("message", "Producto editado satisfactoriamente.");
                        request.setAttribute("messageType", "success");
                    } else {
                        request.setAttribute("message", "Error al editar el producto.");
                        request.setAttribute("messageType", "error");
                    }
                } else {
                    request.setAttribute("message", "Producto no encontrado.");
                    request.setAttribute("messageType", "error");
                }

                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/editar.jsp");
                requestDispatcher.forward(request, response);

            } catch (SQLException e) {
                request.setAttribute("message", "Error en la base de datos.");
                request.setAttribute("messageType", "error");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/editar.jsp");
                requestDispatcher.forward(request, response);
                e.printStackTrace();
            }
        }
    }
}
