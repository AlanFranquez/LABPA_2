<%@page import="webservices.GetTipo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="java.util.List"%>
<%@page import="webservices.DtProducto" %>
<%@page import="webservices.Cliente" %>
<%@page import="webservices.Producto" %>
<%@page import="webservices.Usuario" %>
<%@page import="webservices.Item" %>
<%@page import="webservices.Publicador" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
	rel="stylesheet">
<link href="media/styles/Carrito.css" rel="stylesheet">
<title>Carrito de Compras</title>
</head>
<body>
	<%
	webservices.PublicadorService p = new webservices.PublicadorService();
	webservices.Publicador port = p.getPublicadorPort();
	
	webservices.Usuario usr = (webservices.Usuario) request.getAttribute("usuario");
	webservices.Cliente cliente = (webservices.Cliente) session.getAttribute("usuarioLogueado");
	String estadoUser = (String) request.getAttribute("estado");
	
	String nickUser = port.getNickCliente(cliente);
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
                   <% 
if (usr != null && port.getTipo(nickUser).equals("proveedor")) { 
%> 
    <a class="nav-link" href="perfilProveedor?nickname=<%=usr.getNick()%>">Perfil</a> 
<% 
} else if (usr != null && port.getTipo(nickUser).equals("cliente")) { 
%> 
    <a class="nav-link" href="perfilCliente?nickname=<%=usr.getNick()%>">Perfil</a>
<% 
}
%>
                </li>
                <%
                if (usr != null && port.getTipo(nickUser).equals("cliente")) {
                %>
                <li class="nav-item"><a class="nav-link" href="Carrito">
                    <svg xmlns="http://www.w3.org/2000/svg" width="30px" height="30px" viewBox="0 0 24 24">
                        <path fill="white" d="M17 18c-1.11 0-2 .89-2 2a2 2 0 0 0 2 2a2 2 0 0 0 2-2a2 2 0 0 0-2-2M1 2v2h2l3.6 7.59l-1.36 2.45c-.15.28-.24.61-.24.96a2 2 0 0 0 2 2h12v-2H7.42a.25.25 0 0 1-.25-.25q0-.075.03-.12L8.1 13h7.45c.75 0 1.41-.42 1.75-1.03l3.58-6.47c.07-.16.12-.33.12-.5a1 1 0 0 0-1-1H5.21l-.94-2M7 18c-1.11 0-2 .89-2 2a2 2 0 0 0 2 2a2 2 0 0 0 2-2a2 2 0 0 0-2-2" />
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


	<section class="mt-3">
		<div class="container py-5">
			<div
				class="row d-flex justify-content-center align-items-center h-100">
				<div class="col-10">

					<div class="d-flex justify-content-between align-items-center mb-4">
						<h3 class="fw-normal mb-0">Carrito de Compras</h3>
					</div>

					<!-- Iteramos sobre los productos del carrito -->
					<%
					List<Item> itemsCarrito = (List<Item>) request.getAttribute("itemsCarrito");

					if (itemsCarrito != null && !itemsCarrito.isEmpty()) {
						for (Item item : itemsCarrito) {
							Producto producto = item.getProducto();
					%>
					<div class="card rounded-3 mb-4 contenedorProductos">
						<div class="card-body p-4">
							<div
								class="row d-flex justify-content-between align-items-center">
								<div class="col-md-2 col-lg-2 col-xl-2">
									<%
									if (port.obtenerImagenesProducto(producto.getNumRef()) == null || port.obtenerImagenesProducto(producto.getNumRef()).isEmpty()) {
									%>
									<img class="img-fluid rounded-3"
										src="https://upload.wikimedia.org/wikipedia/commons/a/a3/Image-not-found.png"
										alt="<%=port.imprimirNombreProd(producto.getNumRef())%>"
										style="object-fit: cover;">

									<%
									} else {
									%>

									<img class="img-fluid rounded-3"
										src="mostrarImgProducto?productoId=<%= producto.getNumRef() %>"
										alt="Imagen producto"
										style="object-fit: cover;">
									<%}%>
								</div>
								<div class="col-md-3 col-lg-3 col-xl-3">
									<p class="lead fw-normal mb-2 text-white"><%=producto.getNombre()%></p>
									<p class="text-white font-weight-bold">
										Referencia:
										<%=producto.getNumRef()%></p>
								</div>
								<div class="col-md-3 col-lg-3 col-xl-2 d-flex">

									<form action="Carrito" method="post">
										<input type="hidden" name="action" value="actualizarCant">
										<input type="hidden" name="numRef"
											value="<%=item.getProducto().getNumRef()%>"> <input
											min="1" name="cantidad" value="<%=item.getCant()%>"
											type="number" class="form-control form-control-sm"
											oninput="checkearCantidad()" />
										<button type="submit" class="btn btn-primary btnActualizar">Actualizar</button>
									</form>
								</div>
								<div class="col-md-3 col-lg-2 col-xl-2 offset-lg-1">
									<h5 class="mb-0 text-white">
										$<%=item.getSubTotal()%></h5>

								</div>


								<div class="col-md-1 col-lg-1 col-xl-1 text-end">
									<form action="Carrito" method="post">
										<input type="hidden" name="numRef"
											value="<%=producto.getNumRef()%>" />

										<button class="btn btn-danger" type="submit" name="action"
											value="eliminar">x</button>
									</form>
								</div>
							</div>
						</div>
					</div>
					<%
					}
					} else {
					%>
					<div class="alert alert-warning" role="alert">El carrito está
						vacío.</div>
					<%
					}
					%>

					<div class="d-flex justify-content-between">

						<div>
							<a class="btn btnrealizar" style="border: 1px solid black"
								href="buscarproductos?query=&ordenacion=alfabeticamente">
								Seguir Comprando </a>


						</div>

						<div>

							<%
							if (itemsCarrito != null && !itemsCarrito.isEmpty()) {
							%>
							<a href="realizarCompra">
								<button class="btn hacerOrden">Realizar Orden</button>
							</a>

							<%
							}
							%>
						</div>


					</div>


				</div>
			</div>
		</div>
		
		
	</section>


	<div
		class="part-final d-flex justify-content-center align-items-center">

		<p class="text-center">
			Todos los derechos reservados, 2024. <br> Laboratorio PA.
		</p>

	</div>


	<script type="text/javascript">
	function checkearCantidad() {
	    const cantidadInputs = document.querySelectorAll('input[name="cantidad"]');
	    const btnActualizar = document.querySelector('.btnActualizar');
	    const btnRealizarCompra = document.getElementById('btnActualizar'); 

	    let cantidadValida = false;

	    cantidadValida.forEach(input => {
	        const cantidad = parseInt(input.value) || 0;

	        if (cantidad > 0) {
	            cantidadValida- = true;
	        }
	    });

	    btnActualizar.disabled = !hasValidQuantity;
	    btnRealizarCompra.disabled = !hasValidQuantity;
	}

	document.addEventListener('DOMContentLoaded', checkearCantidad);
	</script>


	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
</body>
</html>