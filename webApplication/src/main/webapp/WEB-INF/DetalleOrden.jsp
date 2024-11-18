<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@page import="services.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>

 
<%
	PublicadorService p = new PublicadorService();
	Publicador port = p.getPublicadorPort();

	services.DtOrdenDeCompra orden = (DtOrdenDeCompra) request.getAttribute("ordencompra");
    List<services.Item> items = port.getItemsOrden(orden);
    List<services.DtEstado> estados = port.getHistorialEstado(orden);
    
    
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalles de la Orden</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<%
    DtCliente user = (DtCliente) request.getAttribute("usuarioOrdenEsp");
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
            <ul class="navbar-nav">
                <li class="nav-item">
                </li>
                <%
                if (user != null && port.getTipo(usr).equals("cliente")) {
                %>
                <li class="nav-item">
                	<a class="nav-link" href="Carrito">
                    <svg xmlns="http://www.w3.org/2000/svg" width="30px" height="30px" viewBox="0 0 24 24">
                        <path fill="white" d="M17 18c-1.11 0-2 .89-2 2a2 2 0 0 0 2 2a2 2 0 0 0 2-2a2 2 0 0 0-2-2M1 2v2h2l3.6 7.59l-1.36 2.45c-.15.28-.24.61-.24.96a2 2 0 0 0 2 2h12v-2H7.42a.25.25 0 0 1-.25-.25q0-.075.03-.12L8.1 13h7.45c.75 0 1.41-.42 1.75-1.03l3.58-6.47c.07-.16.12-.33.12-.5a1 1 0 0 0-1-1H5.21l-.94-2M7 18c-1.11 0-2 .89-2 2a2 2 0 0 0 2 2a2 2 0 0 0 2-2a2 2 0 0 0-2-2" />
                    </svg>
                </a>
                </li>
                <%
                }
                %>
                
                <li class="nav-item">
                    <button class="btn btn-danger">
                        <a class="nav-link" href="logout">Cerrar Sesion</a>
                    <% if (user != null && port.getTipo(usr).equals("proveedor")) { %>
                        <a class="nav-link" href="perfilProveedor?nickname=<%= port.getNombreUsuario(usr) %>">Perfil</a>
                    <% } else if (user != null && port.getTipo(usr).equals("cliente")) { %>
                        <a class="nav-link" href="perfilCliente?nickname=<%= port.getNombreUsuario(usr) %>">Perfil</a>
                    <% } %>
                </li>
            </ul>
        </div>
    </div>
</nav>

   

<div class="container mt-5">
    <h1>Detalles de la Orden Nro <%= port.getNumRefOrden(orden) %></h1>
    
    <div style="
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    max-width: 600px;
    margin: 20px auto;
    padding: 20px;
    text-align: center;">
    
    <h3>Seguimiento de la Orden</h3>
    <ul style="list-style-type: none; margin: 0; padding: 0; position: relative; text-align: left;">
        <% for (DtEstado estado : estados) { %>
            <li style="padding: 10px 0; position: relative; padding-left: 30px;">
                <span style="
                    font-weight: bold; 
                    color: <%= port.getEstado(estado).equalsIgnoreCase("Entregada") ? "#00b200" : "#FFA500" %>;">
                    Estado:
                </span> 
                <%= port.getEstado(estado) %> - <%= port.getFechaEstado(estado) %> <br>
                <span style="color: #666; font-weight: bold; ">Comentarios:</span> <%= port.getComEstado(estado) %>
                
                <!-- Punto de estado con color condicional -->
                <span style="
                    position: absolute;
                    left: 10px;
                    top: 5px;
                    width: 10px;
                    height: 10px;
                    background-color: <%= port.getEstado(estado).equalsIgnoreCase("Entregada") ? "#00b200" : "#FFA500" %>;
                    border-radius: 50%;
                    border: 2px solid white;"></span>
                
                <!-- Línea vertical con color condicional -->
                <% if (!estado.equals(estados.get(estados.size() - 1))) { %>
                    <span style="
                        position: absolute;
                        left: 14px;
                        top: 20px;
                        bottom: 0;
                        width: 2px;
                        background-color: <%= port.getEstado(estado).equalsIgnoreCase("Entregada") ? "#00b200" : "#FFA500" %>;">
                    </span>
                <% } %>
            </li>
        <% } %>
    </ul>
</div>


    <% 
    if(port.getEstadoOrden(orden).equalsIgnoreCase("En camino")) { 
        request.setAttribute("agregarEstado", "si");
    }
	%>


    <% if(port.getEstadoOrden(orden).equalsIgnoreCase("En camino")) { %>
        <form action="perfilOrden" method="post">
            <input type="hidden" name="numeroOrden" value="<%= port.getNumRefOrden(orden) %>">
            <input type="hidden" name="accion" value="confirmar">
            <button type="submit" class="btn btn-success">Confirmar</button>
        </form>
    <% } %>

    <div class="card mt-3">
        <div class="card-body">
            <p><strong>Precio Total: </strong><%= port.getPrecioTotalOrden(orden) %> USD</p>
            <p><strong>Fecha de Compra: </strong><%= port.getFechaOrden(orden) %></p>

            

            <h4>Detalles de Productos:</h4>
            <hr>
            <ul>
            <% 
    		for (services.Item item : items) { 
        		DtItem dtit = port.crearDTItem(item);
        		services.Producto prod = port.getProductoItem(dtit);
            	services.DtProducto dtprod = port.crearDTProd(prod);
			%>
        		
        		<a class="link" href="perfilProducto?producto=<%= port.getNumRefOrden(orden)%>">Ver Producto</a>
        		<li>
            		
            		<p><strong>Nombre: </strong> <%= port.getNombreProd(dtprod) %></p> 
            		<p><strong>Precio Unitario: </strong> $<%= port.getPrecioProd(dtprod) %> USD</p>
            		<p><strong>Cantidad:</strong> <%= port.getCantProdItem(dtit) %></p>
            		<p><strong>Subtotal: </strong> $<%= port.getSubTotaItem(dtit) %></p>
        		</li>
			<% 
    		} 
			%>

            </ul>
            <p><strong>Total de la orden:</strong> $<%= port.getPrecioTotalOrden(orden) %></p>
        </div>
    </div>

    <a href="perfilCliente?nickname=<%= request.getParameter("nickname") %>" class="btn btn-secondary mt-3">Volver al Perfil</a>
</div>

<div class="part-final d-flex justify-content-center align-items-center" style="background-color: #2C2C2C;
				width: 100%;
				height: 200px; 
				margin: 50px 0px 0px 0px;">
    			<div class="bg-gray-800 w-full h-48 mt-12 flex justify-center items-center">
    <p class="text-center text-white">Todos los derechos reservados, 2024. <br> Laboratorio PA.</p>
</div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
