<%@page import="webservices.PublicadorService"%>
<%@page import="webservices.Publicador"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="com.market.svcentral.OrdenDeCompra"%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Perfil Cliente</title>
    <link href="media/styles/IniciarSesion.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;700&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>

	<%
		PublicadorService p = new PublicadorService();
		Publicador port = p.getPublicadorPort();
    
    	webservices.Usuario usr = (webservices.Usuario) request.getAttribute("usuarioLogueado");
    	
    	List<webservices.OrdenDeCompra> ordenes = (List<webservices.OrdenDeCompra>) request.getAttribute("ordenes");
    %>

<%-- NAVBAR --%>
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
                	<a class="nav-link" href="perfilClienteMOBILE?nickname=<%= usr.getNick() %>">Perfil</a>
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

<%-- PERFIL CLIENTE --%>
<main class="container my-5">
    <section class="row justify-content-center align-items-center text-center text-md-start">
        <div class="col-md-4 col-12 mb-4">
            <img class="img-fluid rounded-circle" style="width: 200px; height: 200px; object-fit: cover;" src="media/<%= usr.getImagen() %>" alt="Imagen de cliente" />
        </div>
        <div class="col-md-6 col-12">
            <p>Tipo de Usuario: <b>Cliente</b></p>
            <p>Nickname: <b><%= usr.getNick() %></b></p>
            <p>Nombre: <b><%= usr.getNombre() %></b></p>
            <p>Apellido: <b><%= usr.getApellido() %></b></p>
            <p>Fecha de Nacimiento: <br><b><%= port.imprimirFechaCliente(usr.getNick()) %></b></p>
        </div>
    </section>
</main>

<%-- SECCIÓN COMPRAS REALIZADAS --%>
<section>
        <h2 class="comprasTitle text-center mt-5">Compras Realizadas</h2>
        
        <% if(ordenes == null || ordenes.isEmpty())  {
        	
      	%>
      	<p class="text-center mt-4">No ha realizado compras :(</p>
      	<% } else { 
      		
      	

      		for(webservices.OrdenDeCompra dt : ordenes) {

      			%>
      			
      	<div class="container align-items-center justify-content-center">
            <div class="card">
                <div class="row g-0">
                    <div class="col-md-8">
                        <div class="card-body">

                           
                            <h3 class="card-title"><%= port.imprimirNumRefOrden(usr.getNick(), dt.getNumero()) %></h1>
                            

                            
                            <% 
                            

                            if ("Entregado".equals(port.imprimirEstadoOrden(usr.getNick(), dt.getNumero()))) { 

                            
                            %>
                            


                            	<span class="badge mb-2" style="background-color: green; font-weight: normal; color: white"><%= port.imprimirEstadoOrden(usr.getNick(), dt.getNumero()) %></span>
                            
                            <% } else {%>
                            	<span class="badge mb-2" style="background-color: yellow; font-weight: normal; color: black"><%= port.imprimirEstadoOrden(usr.getNick(), dt.getNumero()) %></span>

                            
                            <% }%>
                            
                            
                            <br>
            
                            
                            <a style="text-decoration: none; color: white" href="perfilOrden?nickname=<%= port.getNickDTCliente(usr.getNick()) %>&orden=<%= port.getEstadoOrden(dt.getNumero()) %>" >VER DETALLES</a>

                           
                             <p class="card-text"><b>Precio total: </b><%= port.imprimirPrecioTotal(usr.getNick(), dt.getNumero()) %></p>
                            <p class="card-text"><b>Fecha de compra: </b><%= port.imprimirFechaOrden(usr.getNick(), dt.getNumero())%></p>
                           
                            <button class="btn" style="border: none; background-color: #2C2C2C">
                            
                            <a style="text-decoration: none; color: white" href="perfilOrden?nickname=<%= port.getNickDTCliente(usr.getNick()) %>&orden=<%= dt.getNumero() %>" >VER DETALLES</a>

                            </button>
    

                        </div>
                        	
                        	
                        
                    </div>
                </div>
            </div>
           </div>
          
          
      			
      	<% } %>
      	
      		
      	
                
      	<% } %>
        
        
    </section>

<%-- PIE DE PÁGINA --%>
<footer class="d-flex justify-content-center align-items-center bg-dark text-white py-4">
    <p class="text-center mb-0">Todos los derechos reservados, 2024. Laboratorio PA.</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
