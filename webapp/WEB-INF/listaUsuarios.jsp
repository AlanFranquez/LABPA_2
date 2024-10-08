<%@ page import="com.model.DTCliente"%>
<%@ page import="com.model.DTFecha" %>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map"%>  <!-- Añadir esta línea -->
<%@ page import="java.io.File"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <meta charset="UTF-8">
    <title>Lista de Usuarios</title>
</head>
<body>
    <h1>Hello World!</h1>
 
<% 
    Object usuariosObj = request.getAttribute("clientes");
		
    if (usuariosObj != null) {
        List<DTCliente> listaClientes = (List<DTCliente>) usuariosObj;

        if (listaClientes != null && !listaClientes.isEmpty()) {
            for(DTCliente l : listaClientes) {
            	System.out.print(l.getImagenes());
%>				
                <div class="card mb-3">
                    <div class="card-body">
                        <p>Nombre: <%= l.getNombre() %></p>
                        <p>Apellido: <%= l.getApellido() %></p>
                        <p>Correo: <%= l.getCorreo() %></p>
                        <p>Nacimiento: <%= l.getNacimientoFormateado() %></p>
                        <a href="Usuarios?usuario=<%= l.getNick() %>">PERFIL</a>
                        
                        <h5>Imagen:</h5>
                       		<img class="img" style="width: 500px; height: 500px;" alt="" src="media<%= l.getImagenes() %>">


                        </div>
                    </div>
                </div>
                <hr>
<% 
            } // Fin del for
        } else {
%>
            <p>No hay clientes disponibles.</p>
<%
        }
    } else {
%>
        <p>El atributo 'clientes' no fue encontrado en la solicitud.</p>
<% 
    }
%>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
