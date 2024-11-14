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
<html>
<head>

<%
DtProducto prod = (DtProducto) request.getAttribute("dtprod");
Usuario usr = (Usuario) request.getAttribute("usuario");
List<String> imagenes = prod.getImagenes();
List<Comentario> comentarios = (List<Comentario>) request.getAttribute("comentarios");
int id = prod.getNumRef();

Cliente cl = null;
Carrito carr = null;
if (usr.getTipo().equals("cliente")) {

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
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<link rel="stylesheet" type="text/css" href="./media/styles/InfoProducto.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap"
	rel="stylesheet">
</head>
<body>


	

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
if (usr != null && usr.getTipo().equals("proveedor")) { 
%> 
    <a class="nav-link" href="perfilProveedor?nickname=<%=usr.getNick()%>">Perfil</a> 
<% 
} else if (usr != null && usr.getTipo().equals("cliente")) { 
%> 
    <a class="nav-link" href="perfilCliente?nickname=<%=usr.getNick()%>">Perfil</a>
<% 
}
%>
                </li>
                <%
                if (usr != null && usr.getTipo().equals("cliente")) {
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
								src="https://thumbs.dreamstime.com/b/image-not-available-icon-set-default-missing-photo-stock-vector-symbol-black-filled-outlined-style-no-found-white-332183016.jpg"
								class="img-fluid" alt="Producto"
								style="width: 100%; height: 200px; object-fit: cover;" />

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
					<%=prod != null ? prod.getCategorias() : "N/A"%>
				</p>
				<div></div>
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
						if (usr.getTipo().equals("cliente") && carr != null && !carr.existeProducto(prod.getNumRef())) {
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
		
		<%
				if (usr.getTipo().equals("cliente") && comproProducto) {
				%>
				<div class="mt-5">
					<a
						href="RealizarReclamo?numRef=<%=prod != null ? prod.getNumRef() : ""%>"
						class="btn btn-warning">Realizar Reclamo</a>
				</div>
				<%
				}
				%>
	</main>

<div>
		<%
		int[] puntajes = prod.obtenerPuntaje();
		%>
		<h3>Puntaje Medio: <%=puntajes[0]%></h3>
		<ul>
		<li>1:<%=puntajes[1]%></li>
		<li>2:<%=puntajes[2]%></li>
		<li>3:<%=puntajes[3]%></li>
		<li>4:<%=puntajes[4]%></li>
		<li>5:<%=puntajes[5]%></li>
		</ul>
	</div>

	<%
	if (usr != null && usr.getTipo().equals("cliente") && comproProducto) {
	%>
	<div class="col-auto star-widget">
		<form id="puntaje" action="agregarValoracion" method="post">
			<span id="valoracion">Valoración</span><br>
	        <input type="radio" name="rate" id="rate-5" value="5">
	        <label for="rate-5" class="bi bi-star"></label>
	        <input type="radio" name="rate" id="rate-4" value="4">
	        <label for="rate-4" class="bi bi-star"></label>
	        <input type="radio" name="rate" id="rate-3" value="3">
	        <label for="rate-3" class="bi bi-star"></label>
	        <input type="radio" name="rate" id="rate-2" value="2">
	        <label for="rate-2" class="bi bi-star"></label>
	        <input type="radio" name="rate" id="rate-1" value="1">
	        <label for="rate-1" class="bi bi-star"></label>
	        <input type="hidden" name="dtprod" value="<%=id%>">
	        <button type="submit" class="btn btn-primary" id="submit-btn" disabled>Enviar</button>
	        <header></header>
		</form>
	</div>
	<% } %>


	<br>
	<br>
	<br>
	<div class="container mt-5">
		<h2>Comentarios</h2>
		<div id="commentSection">
			<%
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
					int comentarioId = c.getNumero();
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
									<%
									if (comproProducto) {
									%>
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

									<%
									}
									%>
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
		if (usr != null && usr.getTipo().equals("cliente") && comproProducto) {
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

<script>
    const submitButton = document.getElementById('submit-btn');
    const radios = document.querySelectorAll('input[name="rate"]');

    function enableSubmitButton() {
        let isSelected = false;
        radios.forEach(radio => {
            if (radio.checked) {
                isSelected = true;
            }
        });
        submitButton.disabled = !isSelected;
    }

    radios.forEach(radio => {
        radio.addEventListener('change', enableSubmitButton);
    });
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