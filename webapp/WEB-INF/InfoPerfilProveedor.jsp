<%@page import="java.util.HashMap"%>
<%@page import="com.model.Producto"%>
<%@page import="com.model.DTCliente"%>
<%@page import="com.model.DTOrdenDeCompra"%>
<%@page import="com.model.DTFecha" %>
<%@page import="com.model.DTProveedor" %>
<%@page import="com.model.DtProducto" %>
<%@page import="com.model.Usuario" %>
<%@page import="java.util.Collection"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="com.model.OrdenDeCompra"%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=1440, initial-scale=1.0">
    <title>Perfil Proveedor</title>
    <link href="media/styles/IniciarSesion.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
    
    <%
    	DTProveedor user = (DTProveedor) request.getAttribute("usuario");
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
                    <a class="nav-link" href="Carrito.html">
                        <svg xmlns="http://www.w3.org/2000/svg" width="30px" height="30px" viewBox="0 0 24 24">
                            <path fill="white" d="M17 18c-1.11 0-2 .89-2 2a2 2 0 0 0 2 2a2 2 0 0 0 2-2a2 2 0 0 0-2-2M1 2v2h2l3.6 7.59l-1.36 2.45c-.15.28-.24.61-.24.96a2 2 0 0 0 2 2h12v-2H7.42a.25.25 0 0 1-.25-.25q0-.075.03-.12L8.1 13h7.45c.75 0 1.41-.42 1.75-1.03l3.58-6.47c.07-.16.12-.33.12-.5a1 1 0 0 0-1-1H5.21l-.94-2M7 18c-1.11 0-2 .89-2 2a2 2 0 0 0 2 2a2 2 0 0 0 2-2a2 2 0 0 0-2-2"/>
                        </svg>
                    </a>
                </li>
                <% } %>

                <li class="nav-item">
                    <button class="btn btn-danger">
                        <a class="nav-link" href="logout">Cerrar Sesi�n</a>
                    </button>
                </li>
            </ul>
        </div>
    </div>
</nav>
    
    <main class="container mt-5 d-flex"> 
    <section class="row justify-content-center align-items-center">
        <div class="col-md-6 col-12 text-center">
            <% if (user.getImagen() == null) { %>
                <p>No Hay imagen disponible :/</p>
            <% } else { %>
                <img class="img-fluid rounded-circle" style="width: 200px; height: 200px; object-fit: cover;" src="media<%= user.getImagen() %>" alt="Imagen de proveedor" />
            <% } %>            	
        </div>
        <div class="col-md-6 col-12">
            <p>Tipo de Usuario: <b>Proveedor</b></p>
            <p>Nickname: <b><%= user.getNick() %></b></p>
            <p>Nombre: <b><%= user.getNombre() %></b></p>
            <p>Apellido: <b><%= user.getApellido() %></b></p>
            <p>Fecha de Nacimiento: <b><%= user.getNacimientoFormateado() %></b></p>                 
            <p>Sitio WEB: <a href="<%= user.getLink() %>"><%= user.getLink() %></a></p>
            <p>Compa��a: <b><%= user.getCompania() %></b></p>
        </div>
    </section>
</main>

    <section>
    <h2 class="comprasTitle text-center mt-5">Productos Asignados</h2>
    
    <% if (user.getListaProductos() == null) { %>
        <p class="text-center mt-4">No ha asignado productos :/</p>
    <% } else { 
        Map<Integer, Producto> listaProductos = user.getListaProductos();
        List<DtProducto> listaDTProductos = new ArrayList<>();
        
        for (Producto p : listaProductos.values()) {
            DtProducto dtp = p.crearDT();
            listaDTProductos.add(dtp);
        }
    %>
    
    <div class="row justify-content-center">
        <% for (DtProducto dt : listaDTProductos) { %>
            <div class="col-md-4 col-sm-6 mb-4">
                <div class="card h-100 text-center">
                    <div class="card-body">
                        <% if (dt != null && dt.getImagenes() != null && !dt.getImagenes().isEmpty() && dt.getImagenes().getFirst() != null) { %>
    <img class="card-img-top" src="media/<%= dt.getImagenes().getFirst() %>" alt="<%= dt.getNombre() %>" style="max-height: 200px; object-fit: cover;">
<% } else { %>
    <img class="card-img-top" src="https://upload.wikimedia.org/wikipedia/commons/a/a3/Image-not-found.png" alt="<%= dt.getNombre() %>" style="max-height: 200px; object-fit: cover;">
<% } %>
                        <h5 class="card-title mt-2"><%= dt != null ? dt.getNombre() : "Producto no encontrado" %></h5>
                        <p class="card-text"><strong>Precio:</strong> $<%= dt != null ? dt.getPrecio() : "N/A" %></p>
                        <p class="card-text"><strong>N�mero de Referencia:</strong> <%= dt != null ? dt.getNumRef() : "N/A" %></p>
                        <a href="perfilProducto?producto=<%= user.obtenerProd(dt.getNumRef()).getNumRef() %>" class="btn btn-primary">Ver Detalles</a>
                    </div>
                </div>
            </div>
        <% } %>
    </div>
    
    <% } %>
</section>

<br>

<div class="text-center">


	<a href="registrarproducto" style="text-decoration: none; color: white;">
<button class="btn btn-primary bg-black" style="border:none; color: white;">
	AGREGAR PRODUCTO	
</button>
	</a>
</div>

<div class="part-final d-flex justify-content-center align-items-center" style="background-color: #2C2C2C;
	width: 100%;
	height: 200px; 
	margin: 50px 0px 0px 0px;">
    <p class="text-center text-white">Todos los derechos reservados, 2024. <br> Laboratorio PA.</p>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>