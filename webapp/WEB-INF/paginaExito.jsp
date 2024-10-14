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
<body class="d-flex flex-column align-items-center justify-content-center" style="min-height: 100vh;">

	<%
	Cliente cl = (Cliente) session.getAttribute("usuarioLogueado");

	String msj = (String) session.getAttribute("mensajeExito");
	String precioTotal = (String) session.getAttribute("precioTotal");
	%>

	<main class="container text-center">
		
		<%
		String mensajeExito = (String) request.getSession().getAttribute("mensajeExito");
		if (mensajeExito != null) {
			request.getSession().removeAttribute("mensajeExito");
		%>
		
		<div class="row d-flex align-items-center">
			<div class="col-md-6">
				<img alt="imagen" src="media/images/exito.svg" class="img-fluid" />
			</div>
		
			<div class="col-md-6">
				<h2>Felicidades, <%=cl.crearDt().getNick()%></h2>
			
				<div class="alert alert-success">
					<%=mensajeExito%>
				</div>
				
				<p><b>Total de la compra: </b> $<%= precioTotal %></p>
			</div>
		</div>

		<br>

		<a class="btn btn-dark" href="perfilCliente?nickname=<%= cl.getNick() %>">Volver al perfil</a>
		<%
		} else {
			// Redirigir a la página de inicio si no hay mensaje
			response.sendRedirect("home.jsp");
		}
		%>
	</main>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
