<%@page import="com.market.svcentral.Sistema"%>
<%@page import="com.market.svcentral.Usuario"%>
<%@page import="com.market.svcentral.Producto"%>
<%@page import="com.market.svcentral.DTCliente"%>
<%@page import="com.market.svcentral.DtProducto"%>
<%@page import="com.market.svcentral.Carrito"%>
<%@page import="com.market.svcentral.Cliente"%>
<%@page import="com.market.svcentral.Item"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Home</title>
<link rel="stylesheet" href="media/styles/index.css">
<link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900&display=swap" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>

<%
Usuario usr = (Usuario) request.getAttribute("usuarioLogueado");
Carrito c = (Carrito) request.getAttribute("carrito");
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
                    if (usr != null && usr.getTipo() == "proveedor") {
                    %> 
                    <a class="nav-link" href="perfilProveedor?nickname=<%=usr.getNick()%>">Perfil</a> 
                    <%
                    } else if (usr != null && usr.getTipo() == "cliente") {
                    %> 
                    <a class="nav-link" href="perfilCliente?nickname=<%=usr.getNick()%>">Perfil</a>
                    <%
                    }
                    %>
                </li>
                <%
                if (usr != null && usr.getTipo() == "cliente") {
                %>
                <li class="nav-item"><a class="nav-link" href="Carrito.html">
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

<main class="container">
    <ul class="list-group list-group-flush">
        <% List<Item> items = c.getProductos(); %>
        <% for(Item i : items) { %>
            <li class="list-group-item d-flex column p-2 align-items-center mt-3" style="border: 1px solid gray; border-radius: 5px">
                <div class="p-2">
                	<%
    List<String> imagenes = i.getProducto().crearDT().getImagenes();
%>

<% if (imagenes == null || imagenes.isEmpty()) { %>
    <img alt="Img del producto" src="https://upload.wikimedia.org/wikipedia/commons/a/a3/Image-not-found.png" style="height: 100px; width: 100px;">
<% } else { %>
    <img alt="Img del producto" class="img-fluid mr-2"  style="max-width: 160px; height: auto;" src="media/<%= imagenes.getFirst() %>">
<% } %>
                   
                </div>
                <div>
                    <h4><%= i.getProducto().crearDT().getNombre() %></h4>
                    <p><b>Número de Referencia: </b><%= i.getProducto().getNumRef() %></p>
                    <p><b>Descripción: </b><%= i.getProducto().crearDT().getDescripcion() %></p>
                    <p><b>Precio: $</b><%= i.getProducto().getPrecio() %></p>
                    <p><b>Cantidad Solicitada: </b><%= i.getCant() %></p>
                </div>
            </li>
        <% } %>
    </ul>

    <section class="order-form mt-4">
        <div class="container pt-4">
            <div class="row">
                <div class="col-12 px-4">
                    <h1>Formulario de Compra</h1>
                    <span>Rellena el formulario con los datos correspondientes para generar una orden de compra</span>
                    <hr class="mt-1" />
                </div>

                <div class="col-12">
                    <form id="orderForm" action="realizarCompra" method="POST">
                        <div class="row mx-4">
                            <div class="col-12">
                                <label class="order-form-label">Nombre</label>
                                <input type="text" id="nombre" class="form-control order-form-input" placeholder="Carlos" />
                            </div>
                            <div class="col-sm-6">
                                <label class="form-label">Apellido</label>
                                <input type="text" id="apellido" class="form-control order-form-input" placeholder="Barragan" />
                            </div>
                        </div>

                        <div class="row mt-3 mx-4">
                            <div class="col-12">
                                <label class="order-form-label">Titular de la Tarjeta</label>
                                <input type="text" id="titular" class="form-control order-form-input" placeholder="BROU" />
                            </div>
                        </div>

                        <div class="row mt-3 mx-4">
                            <div class="col-12">
                                <label class="order-form-label">Número de la tarjeta</label>
                                <input type="text" id="numeroTarjeta" class="form-control order-form-input" placeholder="12345678" />
                            </div>
                        </div>

                        <div class="row mt-3 mx-4">
                            <div class="col-12">
                                <label class="order-form-label">3 últimos dígitos: </label>
                                <input type="text" id="codigoSeguridad" class="form-control order-form-input" placeholder="456" />
                            </div>
                        </div>

                        <div class="row mt-3 mx-4">
                            <div class="col-12">
                                <label class="order-form-label">Fecha de Vencimiento: </label>
                                <input type="date" id="fechaVencimiento" class="form-control order-form-input" />
                            </div>
                        </div>

                        <div class="row mt-3 mx-4">
                            <div class="col-sm-6 mt-2 ps-sm-0">
                                <label class="form-label" for="ciudad">Ciudad</label>
                                <input type="text" id="ciudad" class="form-control order-form-input" placeholder="Trinidad" />
                            </div>
                            <div class="col-sm-6 mt-2 ps-sm-0">
                                <label class="form-label" for="pais">País</label>
                                <input type="text" id="pais" class="form-control order-form-input" placeholder="Uruguay" />
                            </div>
                        </div>

                        <div class="row mt-3 mx-4">
                            <div class="col-12">
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" id="flexCheckDefault" required />
                                    <label class="form-check-label" for="flexCheckDefault">Estoy de acuerdo con los términos y condiciones</label>
                                </div>
                            </div>
                        </div>

                        <div class="row mt-3">
                        <div id="errorMessages" hidden class="text-center alert alert-danger"></div>
                </div>
                            <div class="col-12">
                                <button id="btnSubmit" class="btn btn-primary d-block mx-auto" style="background-color: black;" type="submit">Enviar</button>
                            </div>
                        </div>
                    </form>
                   
            </div>
        </div>
    </section>
</main>

<script>
document.getElementById('orderForm').onsubmit = function(event) {
    event.preventDefault(); // Evita el envío por defecto del formulario

    // Captura los valores del formulario
    const nombre = document.getElementById('nombre').value.trim();
    const apellido = document.getElementById('apellido').value.trim();
    const titular = document.getElementById('titular').value.trim();
    const numeroTarjeta = document.getElementById('numeroTarjeta').value.trim();
    const codigoSeguridad = document.getElementById('codigoSeguridad').value.trim();
    const fechaVencimiento = document.getElementById('fechaVencimiento').value;
    const ciudad = document.getElementById('ciudad').value.trim();
    const pais = document.getElementById('pais').value.trim();
    const hoy = new Date();
    const fechaActual = hoy.toISOString().split('T')[0];

    // Limpiar mensajes de error anteriores
    const errorMessagesDiv = document.getElementById('errorMessages');
    errorMessagesDiv.innerHTML = '';

    // Validaciones del lado del cliente
    if (!nombre || !apellido || !titular || !numeroTarjeta || !codigoSeguridad || !fechaVencimiento || !ciudad || !pais) {
        errorMessagesDiv.innerHTML += '<p>Todos los campos son obligatorios.</p>';
        errorMessagesDiv.removeAttribute('hidden');
        setTimeout(() => {
            errorMessagesDiv.setAttribute('hidden', true);
        }, 3000);
        return;
    }

    if (fechaVencimiento <= fechaActual) {
        errorMessagesDiv.innerHTML += '<p>La fecha de vencimiento debe ser mayor a la fecha actual.</p>';
        errorMessagesDiv.removeAttribute('hidden');
        setTimeout(() => {
            errorMessagesDiv.setAttribute('hidden', true);
        }, 3000);
        return;
    }
    
    if (numeroTarjeta.length !== 8 || isNaN(numeroTarjeta)) {
        errorMessagesDiv.innerHTML += '<p>El número de tarjeta debe tener 8 dígitos y solo contener números.</p>';
        errorMessagesDiv.removeAttribute('hidden');
        setTimeout(() => {
            errorMessagesDiv.setAttribute('hidden', true);
        }, 3000);
        return;
    }

    if (codigoSeguridad.length !== 3 || isNaN(codigoSeguridad)) {
        errorMessagesDiv.innerHTML += '<p>El código de seguridad debe tener 3 dígitos y solo contener números.</p>';
        errorMessagesDiv.removeAttribute('hidden');
        setTimeout(() => {
            errorMessagesDiv.setAttribute('hidden', true);
        }, 3000);
        return;
    }

    // Si todas las validaciones pasan, envía el formulario
    console.log("Todos los campos son válidos, enviando formulario...");
    document.getElementById('orderForm').submit(); // Esto envía el formulario
};
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
