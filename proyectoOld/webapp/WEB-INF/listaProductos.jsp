<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.model.Producto"%>
<%@ page import="com.model.DtProducto"%>
<%@ page import="com.model.Usuario"%>
<%@ page import="com.model.Carrito"%>
<%@ page import="com.model.Cliente"%>
<%@ page import="com.model.ISistema"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<link href="media/styles/listarProducto.css" rel="stylesheet">
<title>Lista de Productos</title>
</head>
<body>
	<%
	List<Producto> productos = (List<Producto>) request.getAttribute("productos");
	Usuario usr = (Usuario) request.getAttribute("usuarioLogueado");

	Carrito carr = null;
	Cliente cl = null;
	if (usr.getTipo() == "cliente") {
		cl = (Cliente) usr;
		carr = cl.getCarrito();
	}
	%>

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
						if (usr != null && usr.getTipo() == "proveedor") {
						%> <a
						class="nav-link"
						href="perfilProveedor?nickname=<%=usr.getNick()%>">Perfil</a> <%
 } else if (usr != null && usr.getTipo() == "cliente") {
 %>
						<a class="nav-link"
						href="perfilCliente?nickname=<%=usr.getNick()%>">Perfil</a> <%
 }
 %>
					</li>

					<%
					if (usr != null && usr.getTipo() == "cliente") {
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


	<div class="container mt-5">
		<div class="row">
			<div class="col-md-12">
				<div class="grid search">
					<div class="grid-body">
						<div class="row">
							<div class="col-md-3">
								<div class="container mt-4">
									<div class="accordion" id="accordionExample">
										<div class="accordion-item">
											<h2 class="accordion-header" id="headingOne">
												<button class="accordion-button" type="button"
													data-bs-toggle="collapse" data-bs-target="#collapseOne"
													aria-expanded="true" aria-controls="collapseOne">
													Ordenar Productos</button>
											</h2>
											<div id="collapseOne"
												class="accordion-collapse collapse collapse"
												aria-labelledby="headingOne"
												data-bs-parent="#accordionExample">
												<div class="accordion-body">
													<form action="buscarproductos" method="POST">
														<select name="ordenacion" class="form-select mb-2"
															onchange="this.form.submit()">
															<option value="">Seleccionar ordenación</option>
															<option value="alfabeticamente">Alfabéticamente
																(Ascendente)</option>
															<option value="precio">Por Precio (Descendente)</option>
															<option value="cantidad">Por Cantidad de Compras</option>
														</select>
													</form>
												</div>
											</div>
										</div>


									</div>
								</div>
							</div>

							<!-- RESULTADOS -->
							<div class="col-md-9">
								<h2>
									<i class="fa fa-file-o"></i> Resultado
								</h2>
								<hr>
								<div class="padding"></div>

								<section>

									<%
									List<DtProducto> listaDTProductos = new ArrayList<>();
									if (productos == null || productos.isEmpty()) {
									%>
									<%
									} else {
									for (Producto p : productos) {
										DtProducto dtp = p.crearDT();
										listaDTProductos.add(dtp);
									}
									}

									if (listaDTProductos.isEmpty()) {
									%>
									<p class="text-center mt-4">No hay productos disponibles.</p>
									<%
									} else {
									%>
									<div class="row justify-content-center">
										<%
										for (DtProducto dt : listaDTProductos) {
										%>
										<div class="col-md-4 col-sm-6 mb-4">
											<div class="card h-100 text-center">
											
												<% if(dt.getImagenes() == null || dt.getImagenes().isEmpty()) {%>
												<img class="card-img-top"
													src="https://upload.wikimedia.org/wikipedia/commons/a/a3/Image-not-found.png"
													alt="<%=dt.getNombre()%>"
													style="height: 200px; object-fit: cover;">
												
												<% } else { %>
													
													<img class="card-img-top"
													src="media/<%=dt.getImagenes().getFirst()%>"
													alt="<%=dt.getNombre()%>"
													style="height: 200px; object-fit: cover;">
												<%}%>
												
												<div class="card-body d-flex flex-column">
													<h5 class="card-title"><%=dt != null ? dt.getNombre() : "Producto no encontrado"%></h5>
													<p class="card-text">
														<strong>Precio:</strong> $<%=dt != null ? dt.getPrecio() : "N/A"%></p>
													<p class="card-text">
														<span style="color: red;">Cantidad disponible:</span>
														<%=dt != null ? dt.getStock() : "N/A"%></p>
													<div class="mt-auto">
														<form action="agregarAlCarrito" method="post"
															style="display: inline-block;"
															onsubmit="return validarCantidad(this)">
															<input type="hidden" name="numRef"
																value="<%=dt != null ? dt.getNumRef() : ""%>">
															<input class="text-center" type="number" name="cantidad"
																min="1" max="<%=dt.getStock()%>" value="1"
																style="width: 60px;"
																onchange="validarCantidad(this.form)"> <br>
															<div class="row mt-2">
																<a
																	href="perfilProducto?producto=<%=dt != null ? dt.getNumRef() : ""%>"
																	class="btn" style="color: #0000EE; cursor: pointer">Ver
																	Detalles</a>
																<%
																if (usr.getTipo() == "cliente" && carr != null && !carr.existeProducto(dt.getNumRef())) {
																%>
																<button type="submit" class="btn btn-primary"
																	id="addToCartButton">Agregar al Carrito</button>

																<%
																}
																%>

															</div>
														</form>
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
								</section>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

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

	<!-- Pie de página -->
	<div
		class="part-final d-flex justify-content-center align-items-center">
		<p class="text-center">
			Todos los derechos reservados, 2024. <br> Laboratorio PA.
		</p>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
	<script src="RegistrarUsuario1.js"></script>
</body>
</html>
