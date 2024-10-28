<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.market.svcentral.Producto" %>
<%@page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Resultados de Búsqueda</title>
</head>
<body>
<h2>Resultados de Búsqueda</h2>

<% 
List<Producto> productos = (List<Producto>) request.getAttribute("productos");

// Verificar si la lista está vacía o es nula
if (productos == null || productos.isEmpty()) {
    out.println("No se encontraron productos.");
} else {
%>

<!-- Opciones de ordenamiento -->
<form action="paginaResultados" method="POST">
    <label for="ordenar">Ordenar por:</label>
    <select name="ordenar" id="ordenar">
        <option value="alfabetico">Alfabéticamente</option>
        <option value="precio">Precio (descendente)</option>
        <option value="compras">Cantidad de compras</option>
    </select>
    <button type="submit">Ordenar</button>
</form>

<ul>
    <%
    for (Producto producto : productos) {
    %>
     	<p><%= producto.getNombre() %></p>
     	<p> <%= producto.getDescripcion()%></p>
     	<p> $<%= producto.getPrecio() %></p>  
     	<br>
    <% 
    }    
    %>
</ul>

<%
} // Fin del else
%>

</body>
</html>
