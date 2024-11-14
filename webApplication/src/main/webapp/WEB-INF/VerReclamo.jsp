<%@page import="java.util.HashMap"%>
<%@page import="com.market.svcentral.Usuario" %>
<%@page import="com.market.svcentral.Reclamo" %>
<%@page import="com.market.svcentral.Proveedor" %>
<%@page import="java.util.Collection"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=1440, initial-scale=1.0">
    <link href="media/styles/VerReclamo.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<title>Reclamos</title>
</head>
<body>
 <% 
Proveedor proveedor = (Proveedor) request.getAttribute("proveedor"); 
Usuario usr = (Usuario) request.getAttribute("usuarioLogueado");
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
                    <% if (usr != null && usr.getTipo() == "proveedor") { %>
                        <a class="nav-link" href="perfilProveedor?nickname=<%= usr.getNick() %>">Perfil</a>
                    <% } else if(usr != null && usr.getTipo() == "cliente"){ %>
                        <a class="nav-link" href="perfilCliente?nickname=<%= usr.getNick() %>">Perfil</a>
                    <% } %>
                </li>
                
                <%
                if (usr != null && usr.getTipo() == "cliente") {
                %>
                <li class="nav-item">
                    <a class="nav-link" href="Carrito">
                        <svg xmlns="http://www.w3.org/2000/svg" width="30px" height="30px" viewBox="0 0 24 24">
                            <path fill="white" d="M17 18c-1.11 0-2 .89-2 2a2 2 0 0 0 2 2a2 2 0 0 0 2-2a2 2 0 0 0-2-2M1 2v2h2l3.6 7.59l-1.36 2.45c-.15.28-.24.61-.24.96a2 2 0 0 0 2 2h12v-2H7.42a.25.25 0 0 1-.25-.25q0-.075.03-.12L8.1 13h7.45c.75 0 1.41-.42 1.75-1.03l3.58-6.47c.07-.16.12-.33.12-.5a1 1 0 0 0-1-1H5.21l-.94-2M7 18c-1.11 0-2 .89-2 2a2 2 0 0 0 2 2a2 2 0 0 0 2-2a2 2 0 0 0-2-2"/>
                        </svg>
                    </a>
                </li>
                <% } %>

                <li class="nav-item">
                    <button class="btn btn-danger">
                        <a class="nav-link" href="logout">Cerrar Sesión</a>
                    </button>
                </li>
            </ul>
        </div>
    </div>
</nav>


 <section>
        <h2 class="comprasTitle text-center mt-5">Reclamos</h2>
        
       <% 
            // Obtener la lista de reclamos desde el request
            List<Reclamo> reclamos = (List<Reclamo>) request.getAttribute("reclamos");
            if (reclamos != null && !reclamos.isEmpty()) {
                for (Reclamo reclamo : reclamos) {
        %>
            <div class="card mb-3">
                <div class="card-body">
                    <h5 class="card-title">Reclamo para el producto: <%= reclamo.getProducto().getNombre() %></h5>
                    <p><b>Cliente:</b> <%= reclamo.getAutor().getNombre() %> (<%= reclamo.getAutor().getCorreo() %>)</p>
                    <p><b>Texto del Reclamo:</b> <%= reclamo.getTexto() %></p>
                    <p><b>Fecha:</b> <%= reclamo.getFechaFormat() %></p>
                </div>
            </div>
        <% 
                }
            } else {
        %>
            <pclass="text-center font-weight-bold">No hay reclamos disponibles.</p>
        <% 
            }
        %>
<div class="part-final d-flex justify-content-center align-items-center" style="background-color: #2C2C2C;
	width: 100%;
	height: 200px; 
	margin: 50px 0px 0px 0px;">
    <p class="text-center text-white">Todos los derechos reservados, 2024. <br> Laboratorio PA.</p>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </section>
</body>
</html>