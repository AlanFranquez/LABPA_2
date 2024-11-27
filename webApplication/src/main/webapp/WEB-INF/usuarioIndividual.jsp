<%@page import="webservices.Cliente"%>
<%@page import="webservices.PublicadorService" %>
<%@page import="webservices.Publicador" %>
<%@page import="java.util.Collection"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=1440, initial-scale=1.0">
    <title>Perfil</title>
    <link href="../media/styles/InfoPerfil.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body style="Font-Family: 'Poppins', sans-serif">
    <%
    PublicadorService p = new PublicadorService();
	Publicador port = p.getPublicadorPort();
	%>
    <div id="barra-nav"></div>
    
    <h1 class="text-center uppercase">Perfil del Cliente</h1>
    
    <main class="container mt-5 d-flex"> 
        <section class="row justify-content-center align-items-center">
            <div class="col-md-6 col-12">
                <img class="" src="https://mdbcdn.b-cdn.net/img/Photos/Avatars/img%20(32).webp"/>
            </div>
            <div class="col-md-6 col-12">
 
			    <% Object usuarioEncontrado = request.getAttribute("user");
			   		Cliente dt = (Cliente) usuarioEncontrado;
			    %>
			   
			   <p><%= dt.getNick() %></p>
			   <p><%= dt.getNombre() %></p>
			   <p><%= dt.getCorreo() %></p>
			   <p><%= port.getFechaNacDTClienteString(dt.getNick()) %></p>
			
            
            </div>   
        </section>

    </main>
    <section>
        <h2 class="comprasTitle">Compras Realizadas / Productos en Venta</h2>
        <div class="container align-items-center justify-content-center">
            <div class="card">
                <div class="row g-0">
                    <div class="col-md-2">
                        <img class="rounded-start" src="public/t3.jpg" />
                    </div>
                    <div class="col-md-8">
                        <div class="card-body">
                            <h3 class="card-title">Title</h3>
                            <p class="card-text">Description</p>
                        </div>
                        <button class="btn btn-primary"><a href="InfoProducto.html">Ver más</a></button>
                    </div>
                </div>
            </div>
                <div class="card">
                    <div class="row g-0">
                        <div class="col-md-2">
                            <img class="rounded-start" src="public/t3.jpg" />
                        </div>
                        <div class="col-md-8">
                            <div class="card-body">
                                <h3 class="card-title">Title</h3>
                                <p class="card-text">Description</p>
                            </div>
                        <button class="btn btn-primary"><a href="InfoProducto.html">Ver más</a></button>
                    </div>
                </div>
            </div>
                <div class="card">
                    <div class="row g-0">
                        <div class="col-md-2">
                            <img class="rounded-start" src="public/t3.jpg" />    
                        </div>
                        <div class="col-md-8">
                            <div class="card-body">
                                <h3 class="card-title">Title</h3>
                                <p class="card-text">Description</p>
                            </div>
                        <button class="btn btn-primary"><a href="InfoProducto.html">Ver más</a></button>
                    </div>
                </div>
            </div>
        </div>
    </section>

    

</body>
</html>