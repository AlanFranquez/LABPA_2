<%@page import="java.util.HashMap"%>
<%@page import="webservices.*"%>

<%@page import="java.util.Collection"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>


<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=1440, initial-scale=1.0">
<title>Realizar Reclamo</title>
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
</head>
<body>

	<%
	PublicadorService p = new PublicadorService();
	Publicador port = p.getPublicadorPort();
	
	webservices.Usuario usr = (webservices.Usuario) request.getAttribute("usuarioLogueado");

	webservices.Producto prodAreclamar = (webservices.Producto) request.getAttribute("productoReclamo");
	%>


	

<nav class="navbar navbar-expand-lg navbar-dark" style="background-color: #2C2C2C;">
    <div class="container">
        <a href="home" class="navbar-brand">ITSCODIGO</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav mx-auto align-items-center">
                <li class="nav-item">
                    <form action="buscarproductos" method="POST" class="d-flex">
                        <input type="text" name="query" placeholder="Buscar productos..." class="form-control me-2" aria-label="Buscar">
                        <button type="submit" class="btn btn-outline-light">Buscar</button>
                    </form>
                </li>
            </ul>
            <ul class="navbar-nav align-items-center">
                <li class="nav-item">
                   <% 
if (usr != null && usr instanceof Proveedor) { 
%> 
    <a class="nav-link" href="perfilProveedor?nickname=<%=usr.getNick()%>">Perfil</a> 
<% 
} else if (usr != null && usr instanceof Proveedor) { 
%> 
    <a class="nav-link" href="perfilCliente?nickname=<%=usr.getNick()%>">Perfil</a>
<% 
}
%>
                </li>
                <%
                if (usr != null && usr instanceof Cliente) {
                %>
                <li class="nav-item"><a class="nav-link" href="Carrito">
                    <svg xmlns="http://www.w3.org/2000/svg" width="30px" height="30px" viewBox="0 0 24 24">
                        <path fill="white" d="M17 18c-1.11 0-2 .89-2 2a2 2 0 0 0 2 2a2 2 0 0 0 2-2a2 2 0 0 0-2-2M1 2v2h2l3.6 7.59l-1.36 2.45c-.15.28-.24.61-.24.96a2 2 0 0 0 2 2h12v-2H7.42a.25.25 0 0 1-.25-.25q0-.075.03-.12L8.1 13h7.45c.75 0 1.41-.42 1.75-1.03l3.58-6.47c.07-.16.12-.33.12-.5a1 1 0 0 0-1-1H5.21l-.94-2M7 18c-1.11 0-2 .89-2 2a2 2 0 0 0 2 2a2 2 0 0 0 2-2a2 2 0 0 0-2-2" />
                    </svg>
                </a></li>
                <%
                }
                %>
                <li class="nav-item">
                    <button class="btn btn-danger">
                        <a class="nav-link" href="logout">Cerrar Sesión</a>
                    </button>
                </li>
            </ul>
        </div>
    </div>
</nav>


	<main class="container mt-5">

		<h3 class="text-center">Producto</h3>

		<%
		
		
		if(port.obtenerImagenesProducto(prodAreclamar.getNumRef()) == null || port.obtenerImagenesProducto(prodAreclamar.getNumRef()).isEmpty()) {%>
		<img class="card-img-top img-fluid"
			src="https://upload.wikimedia.org/wikipedia/commons/a/a3/Image-not-found.png"
			alt="<%=port.imprimirNombreProd(prodAreclamar.getNumRef())%>"
			style="height: 100px; object-fit: cover; width: 100px; border-radius: 5px; border: 1px solid black;">
		
		<% } else { %>
			
			<img class="card-img-top img-fluid"
			src="media/<%=port.obtenerPrimeraImagenProducto(prodAreclamar.getNumRef()) %>"
			alt="<%=port.imprimirNombreProd(prodAreclamar.getNumRef())%>"
			style="height: 100px; object-fit: cover; width: 100px; border-radius: 5px;">
		<%}%>
		<p class="lead"><%=port.imprimirNombreProd(prodAreclamar.getNumRef())%></p>
		<p>
			<b>Numero de Referencia: </b><%=port.imprimirNumRef(prodAreclamar.getNumRef())%></p>
		<p>
			$<%=port.imprimirPrecioProd(prodAreclamar.getNumRef())%></p>
		<p>
			<b>Proveedor: </b><%=prodAreclamar.getProveedor().getNombre()%></p>
		<hr>
		<h3 class="mt-2">Realizar Reclamo</h3>
		<p style="color: dark-grey">Realiza tu reclamo, el mismo podra ser
			visto unicamente por el proveedor</p>
		<form action="RealizarReclamo" method="POST">

			<input type="hidden" name="prodReclamo"
				value="<%=prodAreclamar.getNumRef()%>">
			<textarea name="mensaje" required style="width: 90%; height: 400px;"
				placeholder="Tuve un problema con el producto..."></textarea>
			<br>
			<button class="mt-1 text-center btn btn-primary">Enviar</button>
		</form>

	</main>

	<div
		class="part-final d-flex justify-content-center align-items-center"
		style="background-color: #2C2C2C; width: 100%; height: 200px; margin: 50px 0px 0px 0px;">
		<p class="text-center text-white">
			Todos los derechos reservados, 2024. <br> Laboratorio PA.
		</p>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>