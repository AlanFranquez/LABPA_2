
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="webservices.Usuario"%>
<%@page import="webservices.Proveedor"%>
<%@page import="webservices.Producto"%>
<%@page import="webservices.Carrito" %>
<%@page import="webservices.Proveedor" %>
<%@page import="webservices.Cliente" %>
<%@page import="webservices.Publicador" %>
<%@page import="webservices.PublicadorService" %>
<%@ page import="java.util.List" %>
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
	String estadoUser = (String) session.getAttribute("estado");
    webservices.Usuario usr = (Usuario) request.getAttribute("usuario");
    
    PublicadorService pub = new PublicadorService();
	Publicador port = pub.getPublicadorPort();
    
    List<Producto> prods = (List<Producto>) request.getAttribute("prods");
    Cliente cl = null;
    if (usr != null && usr instanceof Cliente) {
        cl = (Cliente) usr; 
    }

    Carrito carr = null;
    if (cl != null) {
        carr = cl.getCarrito();
    } else {
        System.out.println("El cliente no tiene un carrito asignado.");
    }
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
} else if (usr != null && usr instanceof Cliente) { 
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


<div style="position: relative; background-image: url('media/images/fondo1 (2).jpg'); background-size: cover; background-position: center center;">
    <div style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0, 0, 0, 0.8); z-index: 1;"></div>
    <main class="container d-flex justify-content-center align-items-center vh-90" style="position: relative; z-index: 2;">
        <div class="row w-100">
            <div class="col-md-6 d-flex justify-content-center align-items-center">
                <img src="media/images/icono.svg" alt="Icono.png" style="width: 200px; height: 200px">
            </div>

            <div class="col-md-6 d-flex flex-column justify-content-center align-items-start">
                <h2 style="color: white"> Hola,   
                    <%
                        if ("logueado".equals(estadoUser) && usr != null) {
                            String strUser = usr.getNombre();
                    %>
                            <%= strUser %>
                    <%
                        } else {
                            String strinvAcio = "Invitado"; 
                    %>
                            <%= strinvAcio %> 
                    <%
                        }
                    %>!
                </h2>

                <p class="sub" style="color:white">Estamos para ayudarte!</p>
                <p class="textoPrincipal text-white">En ITSCODIGO nos apasiona ofrecerte una experiencia de compra única y emocionante. Aquí encontrarás una cuidada selección de productos de alta calidad, diseñados para satisfacer tus necesidades y gustos.</p>
                
                <div class="w-100 d-flex m-2 gap-1">
                    <button class="botonIndex mr-2">
                        <a href="buscarproductos" style="text-decoration: none;">
                           Empezar
                        </a>
                    </button>
                    
                    <button class="btn" style="border: 1px solid white;">
                        <a href="#sobrenosotros" style="text-decoration: none; color: white">
                            Sobre Nosotros
                        </a>
                    </button>
                </div>
            </div>
        </div>
    </main>
</div>

	

 <section style="background-color: #eee;" class="mt-5">
    <div class="container py-5">
        <div class="row justify-content-center">
        <% if(prods != null) {%>
        <%
        for(Producto p : prods) {
        %>
            <div class="col-md-8 col-lg-6 col-xl-4 d-flex">
                <div class="card mb-5 flex-fill" style="border-radius: 15px; min-height: 400px;">
                
                    <div class="overflow-hidden" style="border-top-left-radius: 15px; border-top-right-radius: 15px;">
                        <% if(port.obtenerImagenesProducto(p.getNumRef()) != null && !port.obtenerImagenesProducto(p.getNumRef()).isEmpty())  {%>
                        
                          <img src="mostrarImgProducto?productoId=<%= p.getNumRef() %>"
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
                            <p><a href="#!" class="text-dark"><%= port.imprimirNombreProd(p.getNumRef()) %></a></p>
                            <p class="text-dark">#<%= port.imprimirNumRef(p.getNumRef()) %></p>
                        </div>
                            <p style="color: gray"><%= port.imprimirDescripcion(p.getNumRef()) %></p>
                        
                       
                        <div class="">
                            <p class="text-dark">$<%= p.getPrecio() %></p>
                      
                            <p class="alert alert-danger">Cantidad disponible: <%= port.imprimirStock(p.getNumRef()) %></p>
                        </div>
                        <div class="d-flex justify-content-center align-items-center">
                           <form action="agregarAlCarrito" method="post" style="display: inline-block;" onsubmit="return validarCantidad(this)">
                                    <input type="hidden" name="numRef" value="<%= p != null ? p.getNumRef() : "" %>">
                                    <input class="text-center" type="number" name="cantidad" min="1" max="<%= p.getStock() %>" value="1" style="width: 60px;" onchange="validarCantidad(this.form)">
                                    <br>
                                    <div class="row mt-2">
                                        <a href="perfilProducto?producto=<%= p != null ? p.getNumRef() : "" %>" class="btn" style="color: #0000EE; cursor: pointer">Ver Detalles</a>
                                        <% if(usr instanceof Cliente && carr != null && !port.comprobarSiProductoExisteCarrito(cl.getNick(), p.getNumRef())) { 
                                        	
                                       
                                        %>
                                        <button type="submit" class="btn" style="background-color: black; color:white;" id="addToCartButton">Agregar al Carrito</button>
                                        
                                        <% } %>
                                        
                                    </div>
                                </form>
                        </div>
                    </div>
                </div>
            </div>
        <% } %>
        <%} %>
        </div>
    </div>
</section>

<div class="container mt-5 mb-5" id="sobrenosotros">
    <div class="row align-items-center">
        <div class="col-md-6">
            <img alt="Imagen sobre nosotros" class="img-fluid shadow" src="https://mrwallpaper.com/images/hd/hanging-photography-camera-against-mountain-e67sm3xq5ggp1k08.jpg" style="max-width: 100%; height: auto; border: 1px solid black">
        </div>

        <div class="col-md-6">
            <p class="lead">Somos una nueva página web dedicada a la compra y venta de productos. Aquí, cualquier persona puede vender sus productos. Nuestra logística de envío es coordinada por un equipo profesional, asegurando que cada transacción sea fácil y segura.</p>
          	<p class="textoPrincipal">Nuestro objetivo es brindarte no solo los mejores productos, sino también un servicio excepcional. Nos esforzamos por hacer que cada compra sea fácil y agradable, con un proceso de pago seguro y un envío rápido.</p>
        </div>
    </div>
</div>




    <div class="part-final d-flex justify-content-center align-items-center">
        <p class="text-center">Todos los derechos reservados, 2024. <br> Laboratorio PA.</p>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
<script src="../media/js/iniciarSesion.js"></script>

<script>
function validarCantidad(form) {
    const cantidadInput = form.cantidad;
    const stock = parseInt(cantidadInput.max);
    const cantidad = parseInt(cantidadInput.value);
    
    const button = form.querySelector('button[type="submit"]');

    if (cantidad > stock) {
        button.disabled = true; 
    } else {
        button.disabled = false;
    }
}
</script>
</html>