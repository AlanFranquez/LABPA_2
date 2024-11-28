<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@ page import="webservices.PublicadorService" %>
<%@ page import="webservices.Publicador" %>
<%@ page import="webservices.DtCliente" %>
<%@ page import="webservices.Usuario" %>
<%@ page import="webservices.OrdenDeCompra" %>
<%@ page import="webservices.DtOrdenDeCompra" %>



<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=1440, initial-scale=1.0">
    <title>Perfil Cliente</title>
    <link href="media/styles/IniciarSesion.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
    
    <%
    	webservices.PublicadorService p = new PublicadorService();
    	webservices.Publicador port = p.getPublicadorPort();
    	
		webservices.Usuario usr = (webservices.Usuario) request.getAttribute("usuario");

    	
    	List<webservices.OrdenDeCompra> ordenes = (List<webservices.OrdenDeCompra>) request.getAttribute("ordenes");
    	
    	webservices.Cliente usuarioLogueado = (webservices.Cliente) request.getAttribute("usuarioLogueado");
    	String nickUser = port.getNickCliente(usuarioLogueado);
		
    	
    	
    %>
  


<nav class="navbar navbar-expand-lg navbar-dark"
		style="background-color: #2C2C2C;">
		<div class="container">
			<a href="home" class="navbar-brand">ITSCODIGO</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav"
				aria-controls="navbarNav" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav mx-auto align-items-center">
					<li class="nav-item">
						<form action="buscarproductos" method="POST" class="d-flex">
							<input type="text" name="query" placeholder="Buscar productos..."
								class="form-control me-2" aria-label="Buscar">
							<button type="submit" class="btn btn-outline-light">Buscar</button>
						</form>
					</li>
				</ul>
				<ul class="navbar-nav align-items-center">
					<li class="nav-item">
						<%
						if (usr != null && port.getTipo(nickUser).equals("proveedor")) {
						%> <a class="nav-link"
						href="perfilProveedor?nickname=<%=usr.getNick()%>">Perfil</a> <%
 } else if (usr != null && port.getTipo(nickUser).equals("cliente")) {
 %> <a class="nav-link" href="perfilCliente?nickname=<%=usr.getNick()%>">Perfil</a>
						<%
						}
						%>
					</li>
					<%
					if (usr != null && port.getTipo(nickUser).equals("cliente")) {
					%>
					<li class="nav-item"><a class="nav-link" href="Carrito"> <svg
								xmlns="http://www.w3.org/2000/svg" width="30px" height="30px"
								viewBox="0 0 24 24">
                        <path fill="white"
									d="M17 18c-1.11 0-2 .89-2 2a2 2 0 0 0 2 2a2 2 0 0 0 2-2a2 2 0 0 0-2-2M1 2v2h2l3.6 7.59l-1.36 2.45c-.15.28-.24.61-.24.96a2 2 0 0 0 2 2h12v-2H7.42a.25.25 0 0 1-.25-.25q0-.075.03-.12L8.1 13h7.45c.75 0 1.41-.42 1.75-1.03l3.58-6.47c.07-.16.12-.33.12-.5a1 1 0 0 0-1-1H5.21l-.94-2M7 18c-1.11 0-2 .89-2 2a2 2 0 0 0 2 2a2 2 0 0 0 2-2a2 2 0 0 0-2-2" />
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
    
    <main class="container mt-5 d-flex"> 
    <section class="row justify-content-center align-items-center">
        <div class="col-md-6 col-12 text-center">

    <img class="img-fluid rounded-circle" style="width: 200px; height: 200px; object-fit: cover;" 
          src="mostrarImagen?nick=<%= nickUser %>" 
         alt="Imagen de cliente" />
</div>

        <div class="col-md-6 col-12">
            <p>Tipo de Usuario: <b>Cliente</b></p>
            <p>Nickname: <b><%= port.getNickDTCliente(nickUser) %></b></p>
            <p>Nombre: <b><%= port.getNombreDTCliente(nickUser) %></b></p>
            <p>Apellido: <b><%= port.getApellidoDTCliente(nickUser) %></b></p>
            <p>Fecha de Nacimiento: <br><b><%= port.imprimirFechaCliente(usr.getNick()) %></b></p> 
        	<form action="actualizarNotificaciones" method="get">
				<input type="hidden" name="userId" value="<%=nickUser%>">
				<input type="hidden" name="nickname" value="<%=nickUser%>">
				<div class="form-check">
					<input class="form-check-input" type="checkbox"
							id="notificacionesCheckbox" name="activar"
							<%if (port.getRecibirNotificacionesDTCliente(nickUser)) {%> checked <%}%>> 
					<label class="form-check-label" for="notificacionesCheckbox">
						Notificaciones Activas 
					</label>
				</div>
				<button type="submit" class="btn btn-primary mt-3">Actualizar</button>
			</form>
        </div>
    </section>
</main>
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
                            

                            if ("Entregada".equals(port.imprimirEstadoOrden(usr.getNick(), dt.getNumero()))) { 

                            
                            %>
                            


                            	<span class="badge mb-2" style="background-color: green; font-weight: normal; color: white"><%= port.imprimirEstadoOrden(usr.getNick(), dt.getNumero()) %></span>
                            
                            <% } else {%>
                            	<span class="badge mb-2" style="background-color: yellow; font-weight: normal; color: black"><%= port.imprimirEstadoOrden(usr.getNick(), dt.getNumero()) %></span>

                            
                            <% }%>
                            
                            
                            <br>
            
                            
                            <a style="text-decoration: none; color: white" href="perfilOrden?nickname=<%= port.getNickDTCliente(nickUser) %>&orden=<%= port.getEstadoOrden(dt.getNumero(), usr.getNick()) %>" >VER DETALLES</a>

                           
                             <p class="card-text"><b>Precio total: </b><%= port.imprimirPrecioTotal(usr.getNick(), dt.getNumero()) %></p>
                            <p class="card-text"><b>Fecha de compra: </b><%= port.imprimirFechaOrden(usr.getNick(), dt.getNumero())%></p>
                           
                            <button class="btn" style="border: none; background-color: #2C2C2C">
                            
                            <a style="text-decoration: none; color: white" href="perfilOrden?nickname=<%= port.getNickDTCliente(nickUser) %>&orden=<%= dt.getNumero() %>" >VER DETALLES</a>

                            </button>
    

                        </div>
                        	
                        	
                        
                    </div>
                </div>
            </div>
           </div>
          
          
      			
      	<% } %>
      	
      		
      	
                
      	<% } %>
        
        
    </section>
    
     <div class="part-final d-flex justify-content-center align-items-center" style="background-color: #2C2C2C;
				width: 100%;
				height: 200px; 
				margin: 50px 0px 0px 0px;">
    			<p class="text-center text-white">Todos los derechos reservados, 2024. <br> Laboratorio PA.</p>
		   </div>
           

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>