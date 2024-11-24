<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="webservices.*"%>
<%@page import="webservices.Publicador"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>

<%
    PublicadorService p = new PublicadorService();
    Publicador port = p.getPublicadorPort();
    webservices.OrdenDeCompra orden = (webservices.OrdenDeCompra) request.getAttribute("ordencompra");
    List<webservices.Item> items = (List<webservices.Item>) request.getAttribute("items");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalles de la Orden</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    
</head>
<body>

<%
    Usuario usr = (Usuario) request.getAttribute("usuario");
%>

<!-- NAVBAR -->
<nav class="navbar navbar-expand-lg navbar-dark" style="background-color: #2C2C2C;">
    <div class="container">
        <a href="home" class="navbar-brand">
            ITSCODIGO <span class="texto-pequeno">MOBILE</span>
        </a>
        
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav align-items-center">
                <li class="nav-item">
                    <% if (usr != null && usr instanceof Proveedor) { %>
                        <a class="nav-link" href="perfilProveedorMOBILE?nickname=<%= usr.getNick() %>">Perfil</a>
                    <% } else if(usr != null && usr instanceof Cliente){ %>
                        <a class="nav-link" href="perfilClienteMOBILE?nickname=<%= usr.getNick() %>">Perfil</a>
                    <% } %>
                </li>
                <li class="nav-item">
                    <button class="btn btn-danger">
                        <a class="nav-link" href="logoutMOBILE">Cerrar Sesión</a>
                    </button>
                </li>
            </ul>
        </div>
    </div>
</nav>

<%
    if(port.imprimirEstadoOrden(usr.getNick(), orden.getNumero()).equalsIgnoreCase("En camino")) { 
        request.setAttribute("agregarEstado", "si");
    }
%>

<%
    if(port.getEstadoOrden(orden.getNumero(), usr.getNick()).equalsIgnoreCase("En camino")) { 
%>
    <div class="text-center">
        <div class="alert alert-primary" role="alert">
            El pedido actualmente se encuentra EN CAMINO
        </div>
        <form action="perfilOrden" method="post">
            <input type="hidden" name="numeroOrden" value="<%= port.imprimirNumRefOrden(usr.getNick(), orden.getNumero()) %>">
            <input type="hidden" name="accion" value="confirmar">
            <button type="submit" class="btn btn-success">Confirmar</button>
        </form>
    </div>
<% 
    } else if(port.getEstadoOrden(orden.getNumero(), usr.getNick()).equalsIgnoreCase("Entregada")) {
%>
    <div class="text-center">
        <div class="alert alert-success" role="alert">
            Se ha ENTREGADO el pedido
        </div>
    </div>
<% 
    } else { 
%>
    <div class="text-center">
        <div class="alert alert-warning" role="alert">
            Orden en estado: <%= port.getEstadoOrden(orden.getNumero(), usr.getNick()) %>
        </div>
    </div>
<% } %>

<div class="container mt-3">
    <div class="card">
        <div class="card-body">
            <p><strong>Precio Total: </strong><%= port.imprimirPrecioTotal(usr.getNick(), orden.getNumero()) %> USD</p>
            <p><strong>Fecha de Compra: </strong><%= port.imprimirFechaOrden(usr.getNick(), orden.getNumero()) %></p>

            <h4>Detalles de Productos:</h4>
            <hr>
            <ul>
                <% 
                    for (webservices.Item item : items) { 
                %>
                <h1><%= port.imprimirNumRef(item.getProducto().getNumRef()) %></h1>
                <a class="link" href="perfilProductoMOBILE?producto=<%= port.imprimirNumRef(item.getProducto().getNumRef())%>">Ver Producto</a>
                <li>
                    <p><strong>Nombre: </strong> <%= port.imprimirNumRef(item.getProducto().getNumRef())%></p>
                    <p><strong>Precio Unitario: </strong> $<%= port.imprimirPrecioProd(item.getProducto().getNumRef()) %> USD</p>
                    <p><strong>Cantidad:</strong> <%= port.imprimirCantidad(orden.getNumero(), usr.getNick(), item.getProducto().getNumRef()) %></p>
                    <p><strong>Subtotal: </strong> $<%= port.imprimirSubTotal(orden.getNumero(), usr.getNick(), item.getProducto().getNumRef()) %></p>
                </li>
                <% 
                    } 
                %>
            </ul>
            <p><strong>Total de la orden:</strong> $<%= port.imprimirPrecioTotal(usr.getNick(), orden.getNumero()) %></p>
        </div>
    </div>
    <div class="text-center">
        <a href="perfilClienteMOBILE?nickname=<%= request.getParameter("nickname") %>" class="btn btn-secondary mt-3">Volver al Perfil</a>
    </div>
</div>

<!-- Footer -->
<div class="part-final d-flex justify-content-center align-items-center" style="background-color: #2C2C2C;width: 100%;height: 200px;margin: 50px 0px 0px 0px;">
    <div class="bg-gray-800 w-full h-48 mt-12 flex justify-center items-center">
        <p class="text-center text-white">Todos los derechos reservados, 2024. <br> Laboratorio PA.</p>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>