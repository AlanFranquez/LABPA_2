<%@page import="com.market.svcentral.Sistema"%>
<%@page import="com.market.svcentral.Usuario"%>
<%@page import="com.market.svcentral.Producto"%>
<%@page import="com.market.svcentral.DTCliente" %>
<%@page import="com.market.svcentral.DtProducto" %>
<%@page import="com.market.svcentral.Carrito" %>
<%@page import="com.market.svcentral.Cliente" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	String estadoUser = (String) request.getAttribute("estado");
    Usuario usr = (Usuario) request.getAttribute("usuario");

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
                    <% if (usr != null && usr.getTipo().equals("proveedor")) { %>
                        <a class="nav-link" href="perfilProveedorMOBILE?nickname=<%= usr.getNick() %>">Perfil</a>
                    <% } else if(usr != null && usr.getTipo().equals("cliente")){ %>
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



	
    <% if(usr != null)  {%>
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


<% } %>

<div class="container mt-5 mb-5" id="sobrenosotros">
    <div class="row align-items-center">
        <div class="col-md-6">
            <img alt="Imagen sobre nosotros" class="img-fluid shadow" src="media/images/nosotros.jpg" style="max-width: 100%; height: auto; border: 1px solid black">
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
