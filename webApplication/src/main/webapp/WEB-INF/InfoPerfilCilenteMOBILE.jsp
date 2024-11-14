<%@page import="java.util.HashMap"%>
<%@page import="com.market.svcentral.DTCliente"%>
<%@page import="com.market.svcentral.DTOrdenDeCompra"%>
<%@page import="com.market.svcentral.DTFecha" %>
<%@page import="com.market.svcentral.Usuario" %>
<%@page import="com.market.svcentral.Cliente" %>
<%@page import="java.util.Collection"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="com.market.svcentral.OrdenDeCompra"%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Perfil Cliente</title>
    <link href="media/styles/IniciarSesion.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;700&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>

	<%
    	DTCliente user = (DTCliente) request.getAttribute("usuario");
    
    	Usuario usr = (Usuario) request.getAttribute("usuarioLogueado");
    %>

<%-- NAVBAR --%>
<nav class="navbar navbar-expand-lg navbar-dark" style="background-color: #2C2C2C;">
    <div class="container">
        <a href="home" class="navbar-brand">
    			ITSCODIGO <span class="texto-pequeno">MOBILE</span>
			</a>

	<style>
    	.texto-pequeno {
        font-size: 0.6em; /* Ajusta el tamaño según lo necesites */
    	}
	</style>
        
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">


            <ul class="navbar-nav align-items-center">
                <li class="nav-item">
                	<a class="nav-link" href="perfilClienteMOBILE?nickname=<%= usr.getNick() %>">Perfil</a>
                </li>
                <li class="nav-item">
                    <button class="btn btn-danger">
                        <a class="nav-link" href="logout">Cerrar Sesión</a>
                    </button>
                </li>
            </ul>
        </div>
    </div>
</nav>


<%-- PERFIL CLIENTE --%>
<main class="container my-5">
    <section class="row justify-content-center align-items-center text-center text-md-start">
        <div class="col-md-4 col-12 mb-4">
            <img class="img-fluid rounded-circle" style="width: 200px; height: 200px; object-fit: cover;" src="media/<%= user.getImagenes() %>" alt="Imagen de cliente" />
        </div>
        <div class="col-md-6 col-12">
            <p>Tipo de Usuario: <b>Cliente</b></p>
            <p>Nickname: <b><%= user.getNick() %></b></p>
            <p>Nombre: <b><%= user.getNombre() %></b></p>
            <p>Apellido: <b><%= user.getApellido() %></b></p>
            <p>Fecha de Nacimiento: <br><b><%= user.getNacimientoFormateado() %></b></p>

            <!-- Formulario para actualizar notificaciones -->
            <form action="actualizarNotificaciones" method="get" class="mt-4">
                <input type="hidden" name="userId" value="<%= user.getNick() %>">
                <input type="hidden" name="nickname" value="<%= user.getNick() %>">
                <div class="form-check">
                    <input 
                        class="form-check-input" 
                        type="checkbox" 
                        id="notificacionesCheckbox" 
                        name="activar" 
                        <% if (user.isNotificaciones()) { %> checked <% } %>>
                    <label class="form-check-label" for="notificacionesCheckbox">
                        Notificaciones Activas
                    </label>
                </div>
                <button type="submit" class="btn btn-primary mt-3">Actualizar</button>
            </form>
        </div>
    </section>
</main>


<%-- SECCIÓN COMPRAS REALIZADAS --%>
<section class="container">
    <h2 class="text-center mt-5">Compras Realizadas</h2>
    <% if(user.getOrdenes().isEmpty()) { %>
        <p class="text-center mt-4">No ha realizado compras :(</p>
    <% } else { 
        Map<Integer, OrdenDeCompra> ordenesCliente = user.getOrdenes();
        List<DTOrdenDeCompra> listaOrden = new ArrayList<>();
        for (OrdenDeCompra orden : ordenesCliente.values()) {
            listaOrden.add(orden.crearDT());
        }
        for(DTOrdenDeCompra dt : listaOrden) { %>
            <div class="container my-3">
                <div class="card">
                    <div class="row g-0">
                        <div class="col-md-8">
                            <div class="card-body">
                                <h3 class="card-title"><%= dt.getNumero() %></h3>
                                <span class="badge mb-2" style="background-color: <%= dt.getEstado().equals("Entregado") ? "green" : "yellow" %>; color: <%= dt.getEstado().equals("Entregado") ? "white" : "black" %>;"><%= dt.getEstado() %></span>
                                <p class="card-text"><b>Precio total:</b> <%= dt.getPrecioTotal() %></p>
                                <p class="card-text"><b>Fecha de compra:</b> <%= dt.getFechaString() %></p>
                                <a href="perfilOrdenMOBILE?nickname=<%= user.getNick() %>&orden=<%= dt.getNumero() %>" class="btn btn-dark">Ver detalles</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        <% } 
    } %>
</section>

<%-- PIE DE PÁGINA --%>
<footer class="d-flex justify-content-center align-items-center bg-dark text-white py-4">
    <p class="text-center mb-0">Todos los derechos reservados, 2024. Laboratorio PA.</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
