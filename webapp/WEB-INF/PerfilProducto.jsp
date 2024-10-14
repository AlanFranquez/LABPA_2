<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.model.DtProducto"%>
<%@page import="com.model.Usuario"%>
<%@page import="com.model.Comentario"%>

<%@page import="com.model.Cliente"%>
<%@page import="com.model.Carrito"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.List"%>
<%@page import="com.model.DTCliente"%>
<!DOCTYPE html>
<html>
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
%>

<meta charset="UTF-8">
<title><%=prod.getNombre()%></title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">

<link href="media/styles/InfoProducto.css" rel="stylesheet">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap"
	rel="stylesheet">
</head>
<body>


	<nav class="navbar navbar-expand-lg navbar-dark"
		style="background-color: #2C2C2C;">
		<div class="container">
			<!-- Logo -->
			<a href="home" class="navbar-brand">ITSCODIGO</a>

			<!-- Botón para colapsar en dispositivos móviles -->
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav"
				aria-controls="navbarNav" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav mx-auto align-items-center">
					<li class="nav-item w-100">
						<form class="d-flex" role="search">
							<input class="form-control me-2 barraBusqueda" type="search"
								placeholder="Buscar productos..." aria-label="Buscar">
							<button class="btn botonBuscar" type="submit">BUSCAR</button>
						</form>
					</li>
				</ul>

				<ul class="navbar-nav align-items-center">
					<!-- Perfil -->
					<li class="nav-item">
						<%
						if (usr != null && usr.getTipo() == "proveedor") {
						%> <a class="nav-link"
						href="perfilProveedor?nickname=<%=usr.getNick()%>">Perfil</a> <%
 } else if (usr != null && usr.getTipo() == "cliente") {
 %> <a class="nav-link" href="perfilCliente?nickname=<%=usr.getNick()%>">Perfil</a>
						<%
						}
						%>
					</li>

					<%
					if (usr != null && usr.getTipo() == "cliente") {
					%>


					<li class="nav-item"><a class="nav-link" href="Carrito">
							<svg xmlns="http://www.w3.org/2000/svg" width="30px"
								height="30px" viewBox="0 0 24 24">
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

	<main class="container mt-5">
		<div class="row justify-content-center align-items-center">
			<div class="col-md-6">
				<!-- Carrusel de imágenes -->
				<div id="productCarousel" class="carousel slide"
					data-bs-ride="carousel">
					<div class="carousel-inner">
						<%
						if (imagenes != null && !imagenes.isEmpty()) {
							// Si hay imágenes, las mostramos en el carrusel
							for (int i = 0; i < imagenes.size(); i++) {
								String imagen = imagenes.get(i);
						%>
						<div class="carousel-item <%=(i == 0) ? "active" : ""%>">
							<img src="media/<%=imagen%>" class="d-block w-100"
								alt="Imagen de producto"
								style="max-height: 400px; object-fit: cover;">
						</div>
						<%
						}
						} else {
						// Si no hay imágenes, mostramos una por defecto
						%>
						<div class="carousel-item active">
							<img
								src="https://via.placeholder.com/400x300?text=Imagen+no+disponible"
								class="d-block w-100" alt="Imagen no disponible"
								style="max-height: 400px; object-fit: cover;">
						</div>
						<%
						}
						%>
					</div>

					<button class="carousel-control-prev" type="button"
						data-bs-target="#productCarousel" data-bs-slide="prev">
						<span class="carousel-control-prev-icon" aria-hidden="true"></span>
						<span class="visually-hidden">Previous</span>
					</button>
					<button class="carousel-control-next" type="button"
						data-bs-target="#productCarousel" data-bs-slide="next">
						<span class="carousel-control-next-icon" aria-hidden="true"></span>
						<span class="visually-hidden">Next</span>
					</button>
				</div>
			</div>
			<div class="col-md-6">
				<h1 class="display-4"><%=prod != null ? prod.getNombre() : "Producto no encontrado"%></h1>
				<p class="lead"><%=prod != null ? prod.getDescripcion() : "Descripción no disponible."%></p>
				<p>
					<strong>Precio:</strong> $<%=prod != null ? prod.getPrecio() : "N/A"%></p>
				<p>
					<strong>Número de Referencia:</strong>
					<%=prod != null ? prod.getNumRef() : "N/A"%></p>
				<p>
					<strong>Categorías:</strong>
				</p>
				<div class="mt-2">
					<%=prod != null ? prod.getCategorias() : "N/A"%>
				</div>
				<p>
					<strong>Especificaciones:</strong>
					<%=prod != null ? prod.getEspecs() : "N/A"%></p>
				<p>
					<strong>Proveedor:</strong>
					<%=prod != null ? prod.getNicknameProveedor() : "N/A"%></p>
				<p>
					<strong>Cantidad Disponible: </strong><%=prod.getStock()%></p>
				<form action="agregarAlCarrito" method="post"
					onsubmit="return validarCantidad(this)">
					<input type="hidden" name="numRef"
						value="<%=prod != null ? prod.getNumRef() : ""%>">
					<div class="row align-items-center">
						<%
						if (usr.getTipo() == "cliente" && carr != null && !carr.existeProducto(prod.getNumRef())) {
						%>
						<div class="col-auto">
							<input class="text-center" type="number" name="cantidad" min="1"
								max="<%=prod.getStock()%>" value="1" style="width: 60px;"
								onchange="validarCantidad(this.form)">
						</div>
						<div class="col-auto">
							<button type="submit" class="btn btn-primary">Agregar al
								Carrito</button>
						</div>
						<%
						}
						%>
					</div>
				</form>
			</div>
		</div>
	</main>

	<br>
	<br>
	<br>
	<div class="container mt-5">
		<h2>Comentarios</h2>
		<div id="commentSection">
			<%
			List<Comentario> comentarios = prod.getComentarios();
			%>

			<%
			if (comentarios == null || comentarios.isEmpty()) {
			%>
			<div class="alert alert-info" role="alert">Todavía no hay
				comentarios.</div>
			<%
			} else {
			%>
			<div class="column">
				<%
				for (Comentario c : comentarios) {
					String comentarioId = "comment" + c.getNumero();
				%>
				<div class="col-md-6 mb-4">
					<div class="card shadow-sm" style="border: none;">
						<div class="card-body">
							<div class="d-flex align-items-start">
								<img src="media/<%=c.getAutor().crearDt().getImagenes()%>"
									alt="Autor" class="mr-3"
									style="width: 80px; height: 80px; object-fit: cover; border-radius: 50%;">
								<div class="ml-3" style="margin-left: 15px;">
									<h5 class="mt-0" style="font-size: 1.25em;"><%=c.getAutor().crearDt().getNombre()%></h5>
									<p style="font-size: 1em;"><%=c.getTexto()%></p>
									<small class="text-muted"><%=c.getFecha().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))%></small>
									<br>

									<div class="accordion" id="accordion<%=comentarioId%>">
										<div class="accordion-item">
											<h2 class="accordion-header" id="heading<%=comentarioId%>">
												<button class="accordion-button collapsed" type="button"
													data-bs-toggle="collapse"
													data-bs-target="#collapse<%=comentarioId%>"
													aria-expanded="false"
													aria-controls="collapse<%=comentarioId%>">
													Responder</button>
											</h2>
											<div id="collapse<%=comentarioId%>"
												class="accordion-collapse collapse"
												aria-labelledby="heading<%=comentarioId%>">
												<div class="accordion-body">
													<form id="formularioRespuesta" action="enviarRespuesta"
														method="post">
														<textarea name="respuesta" class="form-control" rows="3"
															placeholder="Escribe tu respuesta..." required></textarea>
														<input type="hidden" name="dtprod" value="<%=id%>">
														<input type="hidden" name="comentarioId"
															value="<%=c.getNumero()%>">
														<button class="btn btn-primary mt-3" type="submit">Enviar
															Respuesta</button>
													</form>
												</div>
											</div>
										</div>
									</div>
									<div class="mt-3">
										<h6>Respuestas:</h6>
										<%
										List<Comentario> respuestas = c.getRespuestas(); // Método para obtener las respuestas
										%>
										<%
										if (respuestas == null || respuestas.isEmpty()) {
										%>
										<div class="alert alert-secondary" role="alert">No hay
											respuestas a este comentario.</div>
										<%
										} else {
										%>
										<%
										for (Comentario r : respuestas) {
										%>
										<div class="card mt-2" style="border: none;">
											<div class="card-body">
												<div class="d-flex align-items-start">
													<img src="media/<%=r.getAutor().crearDt().getImagenes()%>"
														alt="Autor" class="mr-3"
														style="width: 50px; height: 50px; object-fit: cover; border-radius: 50%;">
													<div class="ml-3" style="margin-left: 15px;">
														<h6 class="mt-0" style="font-size: 1.1em;"><%=r.getAutor().crearDt().getNombre()%></h6>
														<p style="font-size: 0.9em;"><%=r.getTexto()%></p>
														<small class="text-muted"><%=r.getFecha().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))%></small>
													</div>
												</div>
											</div>
										</div>
										<%
										}
										%>
										<%
										}
										%>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<%
				}
				%>
			</div>
			<%
			}
			%>
		</div>

		<%
		if (usr != null && usr.getTipo().equals("cliente")) {
		%>
		<div class="mt-4">
			<h3>Deja un comentario</h3>
			<form id="formulario" action="enviarComentario" method="post">
				<textarea id="comentario" name="comentario" class="form-control"
					rows="3" placeholder="Escribe tu comentario..." required></textarea>
				<input type="hidden" name="dtprod" value="<%=id%>">
				<button class="btn btn-primary mt-3" type="submit">Enviar
					Comentario</button>
			</form>
		</div>
		<%
		}
		%>
	</div>



	<!-- PARTE FINAL PAGINA -->


	<div
		class="part-final d-flex justify-content-center align-items-center">
		<p class="text-center">
			Todos los derechos reservados, 2024. <br> Laboratorio PA.
		</p>
	</div>



	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous">

</script>

	<script>
function validarCantidad(form) {
    const cantidadInput = form.cantidad;
    const stock = parseInt(cantidadInput.max);
    const cantidad = parseInt(cantidadInput.value);
    
    const button = form.querySelector('button[type="submit"]');

    if (cantidad > stock) {
        button.disabled = true; 
    } else {
        button.disabled = false;
    }
}
</script>

	<script type="text/javascript">
document.getElementById("formulario").addEventListener("submit", function(event) {
    event.preventDefault(); 

    const comentario = document.getElementById("comentario").value;
    const formData = new FormData();
    formData.append("comentario", comentario);

    fetch("enviarComentario", {
        method: "POST",
        body: formData
    })
    .then(response => response.text())
    .then(data => {
        const commentSection = document.getElementById("commentSection");
        const newComment = document.createElement("div");
        newComment.innerHTML = `<p>${comentario}</p>`;
        commentSection.appendChild(newComment);

        document.getElementById("comentario").value = '';
    })
    .catch(error => console.error('Error:', error));
});
});

</script>
</body>
</html>