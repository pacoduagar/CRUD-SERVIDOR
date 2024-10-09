<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Listar Productos</title>
<link rel="stylesheet" href="style/styles.css">
</head>
<body>
	<h1>Listar Productos</h1>
	<h2>
		<a href="index.jsp">Volver al Index</a>
	</h2>
	<table border="1">
		<tr>
			<td>Accion 1</td>
			<td>Nombre</td>
			<td>Cantidad</td>
			<td>Precio</td>
			<td>Fecha Creacion</td>
			<td>Fecha Actualizacion</td>
			<td>Accion 2</td>
		</tr>
		<c:forEach var="producto" items="${lista}">
			<tr>
				<td><button><a href="productos?opcion=meditar&id=<c:out value="${ producto.id}"></c:out>">Editar</a></button></td>
				<td><c:out value="${ producto.nombre}"></c:out></td>
				<td><c:out value="${ producto.cantidad}"></c:out></td>
				<td><c:out value="${ producto.precio}"></c:out></td>
				<td><fmt:formatDate value="${producto.fechaCrear}"
                           pattern="EEEE, dd MMMM yyyy HH:mm:ss" /></td>
                   <td><fmt:formatDate value="${producto.fechaActualizar}"
                           pattern="EEEE, dd MMMM yyyy HH:mm:ss" /></td>
				<td><button><a href="productos?opcion=eliminar&id=<c:out value="${ producto.id}"></c:out>">Eliminar</a></button></td>
			</tr>
		</c:forEach>
	</table>
	<h1>Crear Producto</h1>
<h2 style="color: <%= request.getAttribute("messageType") != null && request.getAttribute("messageType").equals("success") ? "green" : "red" %>;">
    <%
    String mensaje = (String) request.getAttribute("message");
    %>
    <%
    if (mensaje != null) {
    %>
        <%= mensaje %>
    <%
    }
    %>
</h2>
	<form action="productos" method="post">
		  <input type="hidden" name="opcion" value="guardar">    
		<input type="hidden" name="sourcePage" value="listar.jsp">
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