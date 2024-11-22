<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="webservices.PublicadorService"%>
<%@ page import="webservices.Publicador"%>
<%@ page import="webservices.DtCliente"%>
<%@ page import="webservices.Usuario"%>
<%@ page import="webservices.OrdenDeCompra"%>
<%@ page import="webservices.DtOrdenDeCompra"%>
<%@ page import="webservices.*"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
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
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<link rel="stylesheet" type="text/css"
	href="./media/styles/InfoProducto.css">
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


	<main class="container mt-5">
		<div class="row justify-content-center align-items-center">
			<div class="col-md-6">
				<div id="productCarousel" class="carousel slide"
					data-bs-ride="carousel">


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
				<h1 class="display-4"><%=prod != null ? port.imprimirNombreProd(id) : "Producto no encontrado"%></h1>
				<p class="lead"><%=prod != null ? port.imprimirDescripcion(id) : "Descripción no disponible."%></p>
				<p>
					<strong>Precio:</strong> $<%=prod != null ? port.imprimirPrecioProd(id) : "N/A"%></p>
				<p>
					<strong>Número de Referencia:</strong>
					<%=prod != null ? port.imprimirNumRef(id) : "N/A"%></p>
				<p>
					<strong>Categorías:</strong>
					<%=prod != null ? port.imprimirCats(prod.getNumRef()) : "N/A"%>
				</p>
				<div></div>
				<p>
					<strong>Especificaciones:</strong>
					<%=prod != null ? port.imprimirEspec(prod.getNumRef()) : "N/A"%></p>
				<p>
					<strong>Proveedor:</strong>
					<%=prod != null ? port.getnickProvDTProd(id) : "N/A"%></p>
				<p>
					<strong>Cantidad Disponible: </strong><%=port.imprimirStock(id)%></p>
				<form action="agregarAlCarrito" method="post"
					onsubmit="return validarCantidad(this)">
					<input type="hidden" name="numRef"
						value="<%=prod != null ? port.imprimirNumRef(id) : ""%>">
					<div class="row align-items-center">
						<%
						if (port.getTipo(nickUser).equals("cliente") && carr != null
								&& !port.existeProdCarrito(carr, port.imprimirNumRef(id))) {
						%>
						<div class="col-auto">
							<input class="text-center" type="number" name="cantidad" min="1"
								max="<%=port.imprimirStock(id)%>" value="1" style="width: 60px;"
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
		if (port.getTipo(nickUser).equals("cliente") && comproProducto) {
		%>
		<div class="mt-5">
			<a
				href="RealizarReclamo?numRef=<%=prod != null ? port.imprimirNumRef(id) : ""%>"
				class="btn btn-warning">Realizar Reclamo</a>
		</div>
		<%
		}
		%>
	</main>

	<br><br>

	<div class="container my-4 p-3 border rounded shadow-sm">
		<%
		List<Integer> puntajes = port.getPuntajeDTProd(id);
		%>
		<h3 class="text-primary">
			Puntaje Medio: <span class="fw-bold"><%=puntajes.get(0)%></span>
		</h3>

		<ul class="list-group my-3">
			<li
				class="list-group-item d-flex justify-content-between align-items-center">
				1 Estrella <span class="badge bg-primary rounded-pill"><%=puntajes.get(1)%></span>
			</li>
			<li
				class="list-group-item d-flex justify-content-between align-items-center">
				2 Estrellas <span class="badge bg-secondary rounded-pill"><%=puntajes.get(2)%></span>
			</li>
			<li
				class="list-group-item d-flex justify-content-between align-items-center">
				3 Estrellas <span class="badge bg-success rounded-pill"><%=puntajes.get(3)%></span>
			</li>
			<li
				class="list-group-item d-flex justify-content-between align-items-center">
				4 Estrellas <span class="badge bg-warning rounded-pill"><%=puntajes.get(4)%></span>
			</li>
			<li
				class="list-group-item d-flex justify-content-between align-items-center">
				5 Estrellas <span class="badge bg-danger rounded-pill"><%=puntajes.get(5)%></span>
			</li>
		</ul>
	</div>

	<%
	if (usr != null && port.getTipo(nickUser).equals("cliente") && comproProducto) {
	%>
	<div class="container my-4 p-3 border rounded shadow-sm">
		<span id="valoracion" class="d-block mb-3 fs-5 fw-bold">Valoración</span>
		<div class="col-auto star-widget">
			<form id="puntaje" action="agregarValoracion" method="post"
				style="display: inline-block; text-align: center;">
				<input type="radio" name="rate" id="rate-5" value="5"> <label
					for="rate-5" class="bi bi-star"></label> <input type="radio"
					name="rate" id="rate-4" value="4"> <label for="rate-4"
					class="bi bi-star"></label> <input type="radio" name="rate"
					id="rate-3" value="3"> <label for="rate-3"
					class="bi bi-star"></label> <input type="radio" name="rate"
					id="rate-2" value="2"> <label for="rate-2"
					class="bi bi-star"></label> <input type="radio" name="rate"
					id="rate-1" value="1"> <label for="rate-1"
					class="bi bi-star"></label> <input type="hidden" name="dtprod"
					value="<%=id%>">
				<button type="submit" class="btn btn-primary mt-3" id="submit-btn"
					disabled>Enviar</button>
			</form>
		</div>
	</div>



	<%
	}
	%>

	<div class="container">
		<%
		if (usr != null && usr instanceof Cliente && comproProducto) {
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

<div class="container mt-5">

<h1>COMENTARIOS</h1>

	<%
	if (comentarios == null) {
	%>
	<div class="alert alert-info" role="alert">Todavía no hay
		comentarios.</div>
	<%
	} else {
	%>

	<%
	for (Comentario c : comentarios) {
	%>

	<div class="card card-body">
		<h6 class="font-weight-bold"><%=port.imprimirTextoComentario(c.getNumero(), id)%></h6>
		<p><%=port.imprimirAutor(c.getNumero(), id)%></p>
		<p><%=port.imprimirFechaComentario(c.getNumero(), id)%></p>
	</div>



	

			<%
			}
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