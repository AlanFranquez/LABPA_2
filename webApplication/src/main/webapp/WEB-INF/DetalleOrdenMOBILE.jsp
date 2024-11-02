<%@page import="com.market.svcentral.DTEstado"%>
<%@page import="java.util.List"%>
<%@page import="com.market.svcentral.DTItem"%>
<%@page import="com.market.svcentral.Usuario"%>
<%@page import="com.market.svcentral.DTOrdenDeCompra"%>
<%@page import="com.market.svcentral.DTCliente"%>
<%@page import="java.util.Map"%>
<%@page import="com.market.svcentral.Item"%>

<%
    DTOrdenDeCompra orden = (DTOrdenDeCompra) request.getAttribute("ordencompra");
    Map<Integer, Item> items = orden.getItems();
    List<DTEstado> estados = orden.getHistorialEstado();
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
    DTCliente user = (DTCliente) request.getAttribute("usuario");
	Usuario usr = (Usuario) request.getAttribute("usuarioLogueado");
%>
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
                    <% if (usr != null && usr.getTipo() == "proveedor") { %>
                        <a class="nav-link" href="perfilProveedorMOBILE?nickname=<%= usr.getNick() %>">Perfil</a>
                    <% } else if(usr != null && usr.getTipo() == "cliente"){ %>
                        <a class="nav-link" href="perfilClienteMOBILE?nickname=<%= usr.getNick() %>">Perfil</a>
                    <% } %>
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

<div class="container mt-5">
    <h1 class="text-center">Detalles de la Orden Nº <%= orden.getNumero() %></h1>
    
    <div class="card my-4">
        <div class="card-body">
            <h3>Seguimiento de la Orden</h3>
            <ul class="list-unstyled">
                <% for (DTEstado estado : estados) { %>
                    <li class="d-flex align-items-center mb-3">
                        <span class="position-relative me-2" style="width: 10px; height: 10px; border-radius: 50%; background-color: <%= estado.getEstado().equalsIgnoreCase("Entregado") ? "#00b200" : "#FFA500" %>;"></span>
                        <div>
                            <strong style="color: <%= estado.getEstado().equalsIgnoreCase("Entregado") ? "#00b200" : "#FFA500" %>;">Estado:</strong> <%= estado.getEstado() %> - <%= estado.getFecha() %><br>
                            <small class="text-muted">Comentarios: <%= estado.getComentarios() %></small>
                        </div>
                    </li>
                <% } %>
            </ul>
        </div>
    </div>

    <% if(orden.getEstado().equalsIgnoreCase("Enviado")) { %>
        <form action="perfilOrden" method="post" class="text-center mb-4">
            <input type="hidden" name="numeroOrden" value="<%= orden.getNumero() %>">
            <input type="hidden" name="accion" value="confirmar">
            <button type="submit" class="btn btn-success">Confirmar</button>
        </form>
    <% } %>

    <div class="card">
        <div class="card-body">
            <p><strong>Precio Total: </strong><%= orden.getPrecioTotal() %> USD</p>
            <p><strong>Fecha de Compra: </strong><%= orden.getFechaString() %></p>

            <h4>Detalles de Productos:</h4>
            <hr>
            <ul class="list-unstyled">
                <% for (Map.Entry<Integer, Item> entry : items.entrySet()) { 
                    DTItem dtit = entry.getValue().crearDT();
                %>
                    <li class="mb-3">
                        <a class="link" href="perfilProductoMOBILE?producto=<%=dtit.getProducto().crearDT().getNumRef()%>">Ver Producto</a>
                        <p><strong>Nombre: </strong> <%= dtit.getProducto().crearDT().getNombre() %></p>
                        <p><strong>Precio Unitario: </strong> $<%= dtit.getProducto().crearDT().getPrecio() %> USD</p>
                        <p><strong>Cantidad:</strong> <%= dtit.getCant() %></p>
                        <p><strong>Subtotal: </strong> $<%= dtit.getSubTotal() %></p>
                    </li>
                <% } %>
            </ul>
            <p><strong>Total de la orden:</strong> $<%= orden.getPrecioTotal() %></p>
        </div>
    </div>

    <div class="text-center my-4">
        <a href="perfilClienteMOBILE?nickname=<%= request.getParameter("nickname") %>" class="btn btn-secondary">Volver al Perfil</a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
