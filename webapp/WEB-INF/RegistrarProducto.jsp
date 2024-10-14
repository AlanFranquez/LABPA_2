<%@ page import="java.util.List" %>
<%@ page import="com.model.Proveedor" %>
<%@ page import="com.model.Usuario" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro de Producto</title>
    <link href="media/styles/RegistarProducto.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
	
</head>
<body >

<%
	String estadoUser = (String) request.getAttribute("estado");
    Usuario usr = (Usuario) session.getAttribute("usuarioLogueado");
	
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
	
    <div class="body" >
        <div class="containerForm">
            <form class="product-form" action="registrarproducto" method="POST" enctype="multipart/form-data">
                <h2>Registro de Producto</h2>
    
                <label for="product-name">Nombre del Producto</label>
                <input type="text" id="product-name" name="titulo" placeholder="Televisor Samsung 24''" required>
    
                <label for="reference-number">Número de Referencia</label>
                <input type="number" id="reference-number" name="referencia" placeholder="235235" required>
    
                <label for="description">Descripción</label>
                <textarea id="description" name="descripcion" placeholder="Televisor muy bueno, lalala" required></textarea>
    
                <label for="specifications">Especificaciones</label>
                <input type="text" id="specifications" name="especificaciones" placeholder="Pantalla OLED IPS 60Hz" required>
    
                <label for="price">Precio</label>
                <input type="number" id="price" name="precio" placeholder="430" required>
                
                <label for="stock">Stock</label>
                <input type="number" id="stock" name="stock" placeholder="20" required>

                <label for="categories">Categorías</label>
                <select id="categories" name="categoria" required multiple size="6">
                    <option value="" disabled selected>Seleccionar Categoría</option>
                    <% 
                        // Obtener el array desde el request
                        List<String> categorias = (List<String>) request.getAttribute("categories");
                        
                        // Verificamos si la lista no es nula ni vacía
                        if (categorias != null && !categorias.isEmpty()) {
                            for (String categoria : categorias) {
                    %>
                                <option value="<%= categoria %>"><%= categoria %></option>
                    <%
                            }
                        } else {
                    %>
                        <option value="" disabled>No hay categorías disponibles</option>
                    <%
                        }
                    %>
                </select>
    
                <label for="image-upload">Elegir imagen</label>
				<input type="file" id="image-upload" name="imagenes" multiple accept="image/*">

				<script>
				    const fileInput = document.getElementById('image-upload');
					    
					    fileInput.addEventListener('change', function() {
							if (this.files.length > 3) {
								alert('Puedes seleccionar un máximo de 3 imágenes.');
				           		this.value = ''; 
							}
				    	});
				</script>

    
<%
Proveedor proveedorLogueado = (Proveedor) session.getAttribute("usuarioLogueado");
String nickname = (proveedorLogueado != null) ? proveedorLogueado.getNickname() : "";
%>

<div class="button-group">
    <button type="button" class="btn-cancel" 
        onclick="window.location.href='perfilProveedor?nickname=' + encodeURIComponent('<%= nickname %>');">
        Cancelar
    </button>
    <button type="submit" class="btn-submit">Registrar</button>
</div>
            </form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
