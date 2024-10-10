<%@ page import="java.util.List" %>
<%@ page import="com.model.Proveedor" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro de Producto</title>
    <link href="media/styles/RegistarProducto.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900&display=swap" rel="stylesheet">
	<%@ include file="template/BarraNavSinReg.html" %>
</head>
<body>

    <div class="body">
        <div class="containerForm">
            <form class="product-form" action="registrarproducto" method="POST">
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
                <select id="categories" name="categoria" required>
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
                <input type="file" id="image-upload" name="imagen">
    
                <div class="button-group">
                    <button type="button" class="btn-cancel" onclick="window.location.href='perfilProveedor?nickname=' + encodeURIComponent('<%= ((Proveedor) session.getAttribute("usuarioLogueado")).getNickname() %>');">Cancelar</button>
                    <button type="submit" class="btn-submit">Registrar</button>
                </div>
            </form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
