<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.model.Cliente"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Compra Exitosa</title>
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900&display=swap"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">

</head>
<body>

	<%
	Cliente cl = (Cliente) session.getAttribute("usuarioLogueado");

	String msj = (String) session.getAttribute("mensajeExito");
	String precioTotal = (String) session.getAttribute("precioTotal");
	%>

	<main>
		<div>
			<h2>
				Felicidades,
				<%=cl.crearDt().getNick()%></h2>
			<%
			String mensajeExito = (String) request.getSession().getAttribute("mensajeExito");
			if (mensajeExito != null) {
				request.getSession().removeAttribute("mensajeExito"); // Eliminar el mensaje de la sesión
			%>
			<div class="alert alert-success" role="alert"
				style="position: fixed; top: 20px; left: 50%; transform: translateX(-50%); z-index: 999;">
				<%=mensajeExito%>
			</div>
			
			<p><b>Total de la compra: </b> $<%= precioTotal %></p>
			<%
			} else {
			// Redirigir a la página de inicio si no hay mensaje
			response.sendRedirect("home.jsp");
			}
			%>

			<br>

			
			<a class="btn btn-primary" href="perfilCliente?nickname=<%= cl.getNick() %>">Volver al perfil</a>
		</div>
	</main>


	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>