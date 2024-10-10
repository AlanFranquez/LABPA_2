<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page import="com.model.DtProducto" %>
 <%@page import="com.model.Usuario" %>
 <%@page import="java.util.Collections" %>
 <%@page import="java.util.List" %>
 <%@page import="com.model.DTCliente" %>
<!DOCTYPE html>
<html>
<head>

<% 
    DtProducto prod = (DtProducto) request.getAttribute("dtprod");
	Usuario usr = (Usuario) request.getAttribute("usuario");
	 List<String> imagenes = prod.getImagenes();
%>

<meta charset="UTF-8">
<title><%= prod.getNombre() %></title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
  
    <link href="media/styles/InfoProducto.css" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">
</head>
<body>


<nav class="navbar navbar-expand-lg navbar-dark" style="background-color: #2C2C2C;">
    <div class="container">
        <!-- Logo -->
        <a href="home" class="navbar-brand">ITSCODIGO</a>
        
        <!-- Botón para colapsar en dispositivos móviles -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav mx-auto align-items-center">
                <li class="nav-item w-100">
                    <form class="d-flex" role="search">
                        <input class="form-control me-2 barraBusqueda" type="search" placeholder="Buscar productos..." aria-label="Buscar">
                        <button class="btn botonBuscar" type="submit">BUSCAR</button>
                    </form>
                </li>
            </ul>

            <ul class="navbar-nav align-items-center">
                <!-- Perfil -->
                <li class="nav-item">
                    <% if (usr != null && usr.getTipo() == "proveedor") { %>
                        <a class="nav-link" href="perfilProveedor?nickname=<%= usr.getNick() %>">Perfil</a>
                    <% } else if(usr != null && usr.getTipo() == "cliente"){ %>
                    
						 <a class="nav-link" href="perfilCliente?nickname=<%= usr.getNick() %>">Perfil</a>                    
                 
                    <% } %>
                </li>
                
               <%
               	if(usr != null && usr.getTipo() == "cliente") {
               		
               %>
               	
               
                <li class="nav-item">
                    <a class="nav-link" href="Carrito.html">
                        <svg xmlns="http://www.w3.org/2000/svg" width="30px" height="30px" viewBox="0 0 24 24">
                            <path fill="white" d="M17 18c-1.11 0-2 .89-2 2a2 2 0 0 0 2 2a2 2 0 0 0 2-2a2 2 0 0 0-2-2M1 2v2h2l3.6 7.59l-1.36 2.45c-.15.28-.24.61-.24.96a2 2 0 0 0 2 2h12v-2H7.42a.25.25 0 0 1-.25-.25q0-.075.03-.12L8.1 13h7.45c.75 0 1.41-.42 1.75-1.03l3.58-6.47c.07-.16.12-.33.12-.5a1 1 0 0 0-1-1H5.21l-.94-2M7 18c-1.11 0-2 .89-2 2a2 2 0 0 0 2 2a2 2 0 0 0 2-2a2 2 0 0 0-2-2"/>
                        </svg>
                    </a>
                </li>
                
                <% }%>

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
    <div class="row justify-content-center align-items-center">
        <div class="col-md-6">
            <!-- Carrusel de imágenes -->
            <div id="productCarousel" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-inner">
                    <% 
                        if (imagenes != null && !imagenes.isEmpty()) {
                            // Si hay imágenes, las mostramos en el carrusel
                            for (int i = 0; i < imagenes.size(); i++) {
                                String imagen = imagenes.get(i);
                    %>
                                <div class="carousel-item <%= (i == 0) ? "active" : "" %>">
                                    <img src="media/<%= imagen %>" class="d-block w-100" alt="Imagen de producto" style="max-height: 400px; object-fit: cover;">
                                </div>
                    <% 
                            }
                        } else {
                            // Si no hay imágenes, mostramos una por defecto
                    %>
                            <div class="carousel-item active">
                                <img src="https://via.placeholder.com/400x300?text=Imagen+no+disponible" class="d-block w-100" alt="Imagen no disponible" style="max-height: 400px; object-fit: cover;">
                            </div>
                    <% } %>
                </div>
                
                <button class="carousel-control-prev" type="button" data-bs-target="#productCarousel" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#productCarousel" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
        </div>
        <div class="col-md-6">
            <h1 class="display-4"><%= prod != null ? prod.getNombre() : "Producto no encontrado" %></h1>
            <p class="lead"><%= prod != null ? prod.getDescripcion() : "Descripción no disponible." %></p>
            <p><strong>Precio:</strong> $<%= prod != null ? prod.getPrecio() : "N/A" %></p>
            <p><strong>Número de Referencia:</strong> <%= prod != null ? prod.getNumRef() : "N/A" %></p>                
            <p><strong>Categorías:</strong></p>
            <div class="mt-2">
                <%= prod != null ? prod.getCategorias() : "N/A" %>
            </div>
            <p><strong>Especificaciones:</strong> <%= prod != null ? prod.getEspecs() : "N/A" %></p>
            <p><strong>Proveedor:</strong> <%= prod != null ? prod.getNicknameProveedor() : "N/A" %></p>
            
            <% if (usr != null && usr.getTipo().equals("cliente")) { %>
                <button class="btn btn-secondary botonRegistro">Agregar al Carrito</button>
            <% } %>
        </div>
    </div>
</main>

<div class="container mt-5">
    <h2>Comentarios</h2>
    <div id="commentSection">
        <!-- Aquí se cargarán los comentarios -->
    </div>

    <% if (usr != null && usr.getTipo().equals("cliente")) { %>
        <div class="mt-4">
            <h3>Deja un comentario</h3>
            
            <form id="formulario" action="" >
            	<textarea id="commentText" class="form-control" rows="3" placeholder="Escribe tu comentario..."></textarea>
            <button class="btn btn-primary mt-3" type="submit">Enviar Comentario</button>
            	
            </form>
            
            
        </div>
    <% } %>
</div>



<!-- PARTE FINAL PAGINA -->


<div class="part-final d-flex justify-content-center align-items-center">
        <p class="text-center">Todos los derechos reservados, 2024. <br> Laboratorio PA.</p>
    </div>



<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous">

</script>

<script type="text/javascript">
document.getElementById("formulario").addEventListener("submit", function(event) {

    event.preventDefault();

    console.log("click")
});

</script>
</body>
</html>