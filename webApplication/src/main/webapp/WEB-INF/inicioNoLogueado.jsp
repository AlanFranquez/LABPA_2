<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="com.market.svcentral.Usuario"%>
<%@page import="com.market.svcentral.Producto"%>
<%@page import="com.market.svcentral.DtProducto"%>
<%@page import="com.market.svcentral.DTCliente" %>
<%@ page import="java.util.List" %>
<%@page import="com.market.svcentral.Sistema"%>
<%@page import="com.market.svcentral.DTCliente" %>
<%@page import="com.market.svcentral.Carrito" %>
<%@page import="com.market.svcentral.Cliente" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <link rel="stylesheet" href="media/styles/index.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>

<% 


List<Producto> prods = (List<Producto>) request.getAttribute("prods");

 %>

    <nav class="navbar navbar-expand-lg navbar-dark" style="background-color: #2C2C2C;">
        <div class="container">
            <a href="home" class="navbar-brand">
                ITSCODIGO
            </a>
            
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav mx-auto align-items-center">
                   
                </ul>

                <ul class="navbar-nav align-items-center">
                    <li class="nav-item">
                        <a class="nav-link" href="registrarusuario1">
                            Registrar
                        </a>
                    </li>
                    
                    <li class="nav-item">
                        <a class="nav-link" href="formlogin">
                            Login
                        </a>
                    </li>
                    
                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            Ayuda
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
	

    	<main  class="container d-flex justify-content-center align-items-center vh-90">
        <div class="row w-100">
            <div class="col-md-6 d-flex justify-content-center align-items-center">
                <img src="media/images/icono.svg" alt="Icono.png" style="width: 200px; height: 200px">
            </div>

            <div class="col-md-6 d-flex flex-column justify-content-center align-items-start">
                <h2>Hola</h2>

                <p class="sub">Recuerda iniciar sesión para más opciones y productos!</p>
                <p class="textoPrincipal">Somos un pagina web que se dedica a la compra y venta de productos. Diferentes proveedores pueden colocar sus productos a la venta y clientes de todas partes del mundo pueden ver y comprar.
                Inicia sesión o registrate para más beneficios
                </p>
                <button class="botonIndex">
                    <a href="formlogin">
                        Crear una cuenta
                    </a>
                </button>
            </div>
        </div>
    </main>
    
      <section style="background-color: #eee;" class="mt-5">
    <div class="container py-5">
        <div class="row justify-content-center">
        
        <%
        for(Producto p : prods) {
            DtProducto dtp = p.crearDT();
        %>
            <div class="col-md-8 col-lg-6 col-xl-4 d-flex">
                <div class="card flex-fill" style="border-radius: 15px; min-height: 400px;">
                    <div class="overflow-hidden" style="border-top-left-radius: 15px; border-top-right-radius: 15px;">
                        <% if(dtp.getImagenes() != null && !dtp.getImagenes().isEmpty())  {%>
                        
                          <img src="media/<%= dtp.getImagenes().getFirst() %>"
                             class="img-fluid" alt="Producto" 
                             style="width: 100%; height: 200px; object-fit: cover;" />
                        
                        <% } else { %>
                        	<img src="https://thumbs.dreamstime.com/b/image-not-available-icon-set-default-missing-photo-stock-vector-symbol-black-filled-outlined-style-no-found-white-332183016.jpg"
                             class="img-fluid" alt="Producto" 
                             style="width: 100%; height: 200px; object-fit: cover;" />
                        
                        <% } %>
                        
                      
                    </div>
                    <div class="card-body d-flex flex-column">
                        <div class="d-flex justify-content-between align-items-start">
                            <p><a href="#!" class="text-dark"><%= dtp.getNombre() %></a></p>
                            <p class="text-dark">#<%= dtp.getNumRef() %></p>
                        </div>
                            <p style="color: gray"><%= dtp.getDescripcion() %></p>
                        
                       
                        <div class="">
                            <p class="text-dark">$<%= dtp.getPrecio() %></p>
                      
                            <p class="alert alert-danger">Cantidad disponible: <%= dtp.getStock() %></p>
                        </div>
                        <div class="d-flex justify-content-center align-items-center">
                       
                        </div>
                    </div>
                </div>
            </div>
        <% } %>
        
        </div>
    </div>
</section>

    	


    <div class="part-final d-flex justify-content-center align-items-center">
        <p class="text-center">Todos los derechos reservados, 2024. <br> Laboratorio PA.</p>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
<script src="../media/js/iniciarSesion.js"></script>
</html>
