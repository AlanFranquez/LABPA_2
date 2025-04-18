<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="webservices.*"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html lang="es">
<head>

<%
PublicadorService p = new PublicadorService();
Publicador port = p.getPublicadorPort();
//List<String> imagenesBase64 = request.getAttribute("imagenesBase64");
Producto prod = (Producto) request.getAttribute("prod");
Usuario usr = (Usuario) request.getAttribute("usuario");
int id = port.imprimirNumRef(prod.getNumRef());
List<webservices.Comentario> comentarios = (List<webservices.Comentario>) request.getAttribute("coms");
String nickUser = usr.getNick();

Cliente cl = null;
Carrito carr = null;
if (port.getTipo(nickUser).equals("cliente")) {

	cl = (Cliente) usr;
	carr = cl.getCarrito();

}

Boolean comproProducto = false;
if (cl != null) {

	comproProducto = port.comproProducto(nickUser, id);
}
%>
    <meta charset="UTF-8">
    <title><%=port.imprimirNombreProd(id)%></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="media/styles/InfoProducto.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">
</head>
<body>

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
                    <% if(usr != null && port.getTipo(nickUser) == "cliente"){ %>
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
            <div class="col-md-6">
				<div id="productCarousel" class="carousel slide"
					data-bs-ride="carousel">

					<!-- Controles de navegación -->
					<button class="carousel-control-prev" type="button"
						data-bs-target="#productCarousel" data-bs-slide="prev">
						<span class="carousel-control-prev-icon" aria-hidden="true"></span>
						<span class="visually-hidden">Previous</span>
					</button>

					<div class="carousel-inner">
						
							<img alt="Imagen Producto" class="d-block w-100"
								style="overflow: hidden; height: 800px'"
								src="mostrarImgProducto?productoId=<%=id%>&indice=<%=0%>"
								class="d-block w-100">
					
					</div>

					<!-- Controles de navegación -->
					<button class="carousel-control-next" type="button"
						data-bs-target="#productCarousel" data-bs-slide="next">
						<span class="carousel-control-next-icon" aria-hidden="true"></span>
						<span class="visually-hidden">Next</span>
					</button>
				</div>

			</div>
            <div class="col-lg-6 col-md-8">
                <h1 class="display-5 text-center text-md-start"><%= prod != null ? port.imprimirNombreProd(id) : "Producto no encontrado"%></h1>
                <p class="lead text-center text-md-start"><%= prod != null ? port.imprimirDescripcion(id) : "Descripción no disponible."%></p>
                <p><strong>Precio:</strong> $<%= prod != null ? port.imprimirPrecioProd(id) : "N/A"%></p>
                <p><strong>Número de Referencia:</strong> <%=prod != null ? port.imprimirNumRef(id) : "N/A"%></p>
                <p><strong>Categorías:</strong> <%=prod != null ? port.imprimirCats(prod.getNumRef()) : "N/A"%></p>
                <p><strong>Especificaciones:</strong> <%=prod != null ? port.imprimirEspec(prod.getNumRef()) : "N/A"%></p>
                <p><strong>Proveedor:</strong> <%=prod != null ? port.getnickProvDTProd(id) : "N/A"%></p>
                <p><strong>Cantidad Disponible:</strong> <%= port.imprimirStock(id) %></p>
            </div>
        </div>
    </main>
    

    

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-FBGh+/U6egj9SD5H7AJn5QdvA5A1KZ6lHri74ZRMNl2Vrb6e28XkTM1SZY33zCf" crossorigin="anonymous"></script>
</body>
</html>
