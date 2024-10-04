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
 */
@WebServlet(description = "administra peticiones para la tabla productos", urlPatterns = { "/productos" })
public class ProductoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductoController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String opcion = request.getParameter("opcion");

		if (opcion.equals("crear")) {
			System.out.println("Usted a presionado la opcion crear");
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("Usted a presionado la opcion listar");
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (opcion.equals("eliminar")) {
			ProductoDAO productoDAO = new ProductoDAO();
			int id = Integer.parseInt(request.getParameter("id"));
			try {
				productoDAO.eliminar(id);
				System.out.println("Registro eliminado satisfactoriamente...");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
				requestDispatcher.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
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

	            // Input validation
	            String errorMessage = null;

	            // Check if 'nombre' is empty
	            if (nombre == null || nombre.trim().isEmpty()) {
	                errorMessage = "El nombre del producto es obligatorio.";
	            }

	            // Validate 'cantidad' to ensure it's a valid number
	            double cantidad = 0;
	            try {
	                cantidad = Double.parseDouble(cantidadStr);
	                if (cantidad <= 0) {
	                    errorMessage = "La cantidad debe ser un número mayor que cero.";
	                }
	            } catch (NumberFormatException e) {
	                if (errorMessage == null) {  // Only set error if it's not set already
	                    errorMessage = "La cantidad debe ser un número válido.";
	                }
	            }

	            // Validate 'precio' to ensure it's a valid number
	            double precio = 0;
	            try {
	                precio = Double.parseDouble(precioStr);
	                if (precio <= 0) {
	                    errorMessage = "El precio debe ser un número mayor que cero.";
	                }
	            } catch (NumberFormatException e) {
	                if (errorMessage == null) {  // Only set error if it's not set already
	                    errorMessage = "El precio debe ser un número válido.";
	                }
	            }

	            try {
	                // If there are validation errors, show an error message
	                if (errorMessage != null) {
	                    request.setAttribute("message", errorMessage);
	                    request.setAttribute("messageType", "error");
	                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/crear.jsp");
	                    requestDispatcher.forward(request, response);
	                    return;  // Stop further execution if validation fails
	                }

	                // Check if product with the same name already exists
	                Producto existingProduct = productoDAO.obtenerProductoPorNombre(nombre);
	                if (existingProduct != null) {
	                    request.setAttribute("message", "El producto ya existe.");
	                    request.setAttribute("messageType", "error");
	                } else {
	                    // Create new product
	                    Producto producto = new Producto();
	                    producto.setNombre(nombre);
	                    producto.setCantidad(cantidad);
	                    producto.setPrecio(precio);
	                    producto.setFechaCrear(new java.sql.Date(fechaActual.getTime()));
	                    producto.setFechaActualizar(null);  // Initially, no update date

	                    boolean isSaved = productoDAO.guardar(producto);

	                    if (isSaved) {
	                        request.setAttribute("message", "Producto guardado satisfactoriamente.");
	                        request.setAttribute("messageType", "success");
	                    } else {
	                        request.setAttribute("message", "Error al guardar el producto.");
	                        request.setAttribute("messageType", "error");
	                    }
	                }

	                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/crear.jsp");
	                requestDispatcher.forward(request, response);

	            } catch (SQLException e) {
	                request.setAttribute("message", "Error en la base de datos.");
	                request.setAttribute("messageType", "error");
	                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/crear.jsp");
	                requestDispatcher.forward(request, response);
	                e.printStackTrace();
	            }
	        }
	    }
	}
	// doGet(request, response);
