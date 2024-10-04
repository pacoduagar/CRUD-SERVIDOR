<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Listar Productos</title>
<script>
        // Function to display pop-up messages
        function showAlert(message, type) {
            if (message) {
                alert(message);
            }
        }

        // Call the showAlert function on page load if there's a message
        window.onload = function() {
            const message = '<%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %>';
            const messageType = '<%= request.getAttribute("messageType") != null ? request.getAttribute("messageType") : "" %>';
            if (message) {
                showAlert(message, messageType);
            }
        }
    </script>
</head>
<body>
	<h1>Listar Productos</h1>
	<h2><a href="index.jsp">Volver al Index</a></h2>
	<table border="1">
		<tr>
			<td>Id</td>
			<td>Nombre</td>
			<td>Cantidad</td>
			<td>Precio</td>
			<td>Fecha Creacion</td>
			<td>Fecha Actualizacion</td>
			<td>Accion</td>
		</tr>
		<c:forEach var="producto" items="${lista}">
			<tr>
				<td><a
					href="productos?opcion=meditar&id=<c:out value="${ producto.id}"></c:out>">
						<c:out value="${ producto.id}"></c:out>
				</a></td>
				<td><c:out value="${ producto.nombre}"></c:out></td>
				<td><c:out value="${ producto.cantidad}"></c:out></td>
				<td><c:out value="${ producto.precio}"></c:out></td>
				<td><c:out value="${ producto.fechaCrear}"></c:out></td>
				<td><c:out value="${ producto.fechaActualizar}"></c:out></td>
				<td><a
					href="productos?opcion=eliminar&id=<c:out value="${ producto.id}"></c:out>">
						Eliminar </a></td>
			</tr>
		</c:forEach>
	</table>
		<h1>Crear Producto</h1>
	<h2><a href="index.jsp">Volver al Index</a></h2>
	<form action="productos" method="post">
		  <input type="hidden" name="opcion" value="guardar">   
		<table border="1">
			<tr>
				<td>Nombre:</td>
				<td><input type="text" name="nombre" size="50"></td>
			</tr>
			<tr>
				<td>Cantidad:</td>
				<td><input type="text" name="cantidad" size="50"></td>
			</tr>
			<tr>
				<td>Precio:</td>
				<td><input type="text" name="precio" size="50"></td>
			</tr>
		</table>
		  <input type="submit" value="Guardar">  
	</form>
</body>
</html>