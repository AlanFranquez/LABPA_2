<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="com.model.Usuario"%>

 <%Usuario usr = (Usuario) request.getAttribute("usuario"); %>

    <nav class="navbar navbar-expand-lg navbar-dark" style="background-color: #2C2C2C;">
        <div class="container">
            <!-- Logo -->
            <a href="index.html" class="navbar-brand">
                ITSCODIGO
            </a>
            
            <!-- Botón para colapsar en dispositivos móviles -->
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav mx-auto align-items-center">
                    <li class="nav-item w-100">
                        <form class="d-flex" role="search">
                            <input class="form-control me-2 barraBusqueda" type="search" placeholder="Buscar productos..." aria-label="Buscar">
                            <button class="btn botonBuscar" type="submit">BUSCAR</button>
                        </form>
                    </li>
                </ul>

                <ul class="navbar-nav align-items-center">
                    <!-- Perfil -->
                    <li class="nav-item">
                        <a class="nav-link" href="perfilCliente?nickname=<%= usr.getNick() %>">
                            Perfil
                        </a>
                    </li>
                    
                    <!-- Carrito -->
                    <li class="nav-item">
                        <a class="nav-link" href="Carrito.html">
                            <svg xmlns="http://www.w3.org/2000/svg" width="30px" height="30px" viewBox="0 0 24 24">
                                <path fill="white" d="M17 18c-1.11 0-2 .89-2 2a2 2 0 0 0 2 2a2 2 0 0 0 2-2a2 2 0 0 0-2-2M1 2v2h2l3.6 7.59l-1.36 2.45c-.15.28-.24.61-.24.96a2 2 0 0 0 2 2h12v-2H7.42a.25.25 0 0 1-.25-.25q0-.075.03-.12L8.1 13h7.45c.75 0 1.41-.42 1.75-1.03l3.58-6.47c.07-.16.12-.33.12-.5a1 1 0 0 0-1-1H5.21l-.94-2M7 18c-1.11 0-2 .89-2 2a2 2 0 0 0 2 2a2 2 0 0 0 2-2a2 2 0 0 0-2-2"/>
                            </svg>
                        </a>
                    </li>
                    
                   
                    
                    <li class="nav-item">
                    
                    	<button class="btn btn-danger">
                        <a class="nav-link" href="logout">
                            Cerrar Sesión
                        </a>
                        </button>
                    </li>
                </ul>
            </div>
        </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </nav>
