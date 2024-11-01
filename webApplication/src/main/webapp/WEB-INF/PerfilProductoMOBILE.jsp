<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.market.svcentral.DtProducto"%>
<%@page import="com.market.svcentral.Usuario"%>
<%@page import="com.market.svcentral.Comentario"%>

<%@page import="com.market.svcentral.Cliente"%>
<%@page import="com.market.svcentral.Carrito"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.List"%>
<%@page import="com.market.svcentral.DTCliente"%>
<!DOCTYPE html>
<html lang="es">
<head>

<%
DtProducto prod = (DtProducto) request.getAttribute("dtprod");
Usuario usr = (Usuario) request.getAttribute("usuario");
List<String> imagenes = prod.getImagenes();
int id = prod.getNumRef();

Cliente cl = null;
Carrito carr = null;
if (usr.getTipo() == "cliente") {

	cl = (Cliente) usr;
	carr = cl.getCarrito();

}
Boolean comproProducto = false;
if(cl != null) {
	
comproProducto = cl.comproProducto(id);
}
%>
    <meta charset="UTF-8">
    <title><%=prod.getNombre()%></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="media/styles/InfoProducto.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">
</head>
<body>

    <%-- NAVBAR --%>
<nav class="navbar navbar-expand-lg navbar-dark" style="background-color: #2C2C2C;">
    <div class="container">
        <a href="home" class="navbar-brand">ITSCODIGO</a>
        
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">


            <ul class="navbar-nav align-items-center">
                <li class="nav-item">
                    <% if (usr != null && usr.getTipo() == "proveedor") { %>
                        <a class="nav-link" href="perfilProveedorMOBILE?nickname=<%= usr.getNick() %>">Perfil</a>
                    <% } else if(usr != null && usr.getTipo() == "cliente"){ %>
                        <a class="nav-link" href="perfilClienteMOBILE?nickname=<%= usr.getNick() %>">Perfil</a>
                    <% } %>
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

    <main class="container mt-5">
        <div class="row justify-content-center align-items-center">
            <div class="col-lg-6 col-md-8">
                <div id="productCarousel" class="carousel slide" data-bs-ride="carousel">
                    <div class="carousel-inner">
                        <%
                        if (imagenes != null && !imagenes.isEmpty()) {
                            for (int i = 0; i < imagenes.size(); i++) {
                                String imagen = imagenes.get(i);
                        %>
                        <div class="carousel-item <%=(i == 0) ? "active" : ""%>">
                            <img src="media/<%=imagen%>" class="d-block w-100" alt="Imagen de producto" style="max-height: 400px; object-fit: cover;">
                        </div>
                        <%
                        }
                        } else {
                        %>
                        <div class="carousel-item active">
                            <img src="https://thumbs.dreamstime.com/b/image-not-available-icon-set-default-missing-photo-stock-vector-symbol-black-filled-outlined-style-no-found-white-332183016.jpg" class="img-fluid" alt="Producto" style="width: 100%; height: 200px; object-fit: cover;">
                        </div>
                        <%
                        }
                        %>
                    </div>
                    <button class="carousel-control-prev" type="button" data-bs-target="#productCarousel" data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Previous</span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#productCarousel" data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Next</span>
                    </button>
                </div>
            </div>
            <div class="col-lg-6 col-md-8">
                <h1 class="display-5 text-center text-md-start"><%=prod != null ? prod.getNombre() : "Producto no encontrado"%></h1>
                <p class="lead text-center text-md-start"><%=prod != null ? prod.getDescripcion() : "Descripción no disponible."%></p>
                <p><strong>Precio:</strong> $<%=prod != null ? prod.getPrecio() : "N/A"%></p>
                <p><strong>Número de Referencia:</strong> <%=prod != null ? prod.getNumRef() : "N/A"%></p>
                <p><strong>Categorías:</strong> <%=prod != null ? prod.getCategorias() : "N/A"%></p>
                <p><strong>Especificaciones:</strong> <%=prod != null ? prod.getEspecs() : "N/A"%></p>
                <p><strong>Proveedor:</strong> <%=prod != null ? prod.getNicknameProveedor() : "N/A"%></p>
                <p><strong>Cantidad Disponible:</strong> <%=prod.getStock()%></p>
                <form action="agregarAlCarrito" method="post" onsubmit="return validarCantidad(this)">
                    <input type="hidden" name="numRef" value="<%=prod != null ? prod.getNumRef() : ""%>">
                </form>
            </div>
        </div>
    </main>

    <br>
    <br>
    <br>

    

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-FBGh+/U6egj9SD5H7AJn5QdvA5A1KZ6lHri74ZRMNl2Vrb6e28XkTM1SZY33zCf" crossorigin="anonymous"></script>
</body>
</html>
