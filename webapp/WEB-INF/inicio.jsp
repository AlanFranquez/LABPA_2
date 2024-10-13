<%@page import="com.model.Sistema"%>
<%@page import="com.model.Usuario"%>
<%@page import="com.model.DTCliente" %>
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
                        <a class="nav-link" href="logout">Cerrar Sesión</a>
                    </button>
                </li>
            </ul>
        </div>
    </div>
</nav>
	
    
    <main class="container d-flex justify-content-center align-items-center vh-90">
        <div class="row w-100">
            <div class="col-md-6 d-flex justify-content-center align-items-center">
                <img src="media/images/icono.svg" alt="Icono.png" style="width: 200px; height: 200px">
            </div>

            <div class="col-md-6 d-flex flex-column justify-content-center align-items-start">
                <h2> Hola,   
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

                <p class="sub">Estamos para ayudarte!</p>
                <p class="textoPrincipal">Lorem ipsum dolor sit amet consectetur adipisicing elit. Nam consectetur sequi enim atque quaerat sed laudantium repudiandae id, asperiores doloribus molestias totam necessitatibus! Quam perspiciatis officia alias hic repellendus dolorem.</p>
                <button class="botonIndex">
                    <a href="home">
                        Empezar
                    </a>
                </button>
            </div>
        </div>
    </main>

    <section style="background-color: #eee;" class="mt-5">
        <div class="container py-5">
            <div class="row justify-content-center">
                <!-- Repetir este bloque para más productos -->
                <div class="col-md-8 col-lg-6 col-xl-4">
                    <div class="card" style="border-radius: 15px;">
                        <div class="bg-image hover-overlay ripple ripple-surface ripple-surface-light"
                             data-mdb-ripple-color="light">
                            <img src="https://mdbcdn.b-cdn.net/img/Photos/Horizontal/E-commerce/Products/12.webp"
                                 style="border-top-left-radius: 15px; border-top-right-radius: 15px;" class="img-fluid"
                                 alt="Laptop" />
                            <a href="#!">
                                <div class="mask"></div>
                            </a>
                        </div>
                        <div class="card-body pb-0">
                            <div class="d-flex justify-content-between">
                                <div>
                                    <p><a href="#!" class="text-dark">Dell Xtreme 270</a></p>
                                    <p class="small text-muted">Laptops</p>
                                </div>
                                <div>
                                    <div class="d-flex flex-row justify-content-end mt-1 mb-4 text-danger">
                                        <i class="fas fa-star"></i>
                                        <i class="fas fa-star"></i>
                                        <i class="fas fa-star"></i>
                                        <i class="fas fa-star"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <hr class="my-0" />
                        <div class="card-body pb-0">
                            <div class="d-flex justify-content-between">
                                <p><a href="#!" class="text-dark">$3,999</a></p>
                                <p class="text-dark">#### 8787</p>
                            </div>
                            <p class="small text-muted">VISA Platinum</p>
                        </div>
                        <hr class="my-0" />
                        <div class="card-body">
                            <a href="InfoProducto.html" class="d-flex justify-content-between align-items-center pb-2 mb-1">
                                <button type="button" class="btn btn-primary">Añadir al Carrito</button>
                            </a>
                        </div>
                    </div>
                </div>
                <!-- Fin del bloque de productos -->

                <!-- Puedes agregar más productos aquí -->
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
