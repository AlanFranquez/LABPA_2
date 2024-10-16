<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión</title>
    <link rel="stylesheet" href="media/styles/IniciarSesion.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
<div class="body">

    <% 
        // Recupera el mensaje de error de la sesión
        String mensajeError = (String) session.getAttribute("errorMsg"); 
    %>

    <main class="container justify-content-center align-items-center">
        <section class="text-center">
            <h1>Iniciar Sesión</h1>
            <p>Asegúrate de haberte registrado con anterioridad</p>
        </section>
        
        <% if (mensajeError != null) { %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <strong>Error!</strong> <%= mensajeError %>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <% 
                // Limpia el mensaje de error después de mostrarlo
                session.removeAttribute("errorMsg"); 
            %>
        <% } %>
        
        <form class="row g-3 container-sm needs-validation" method="post" action="formlogin" id="form" novalidate>
            <div class="form-group">
              <label for="nickname">Nickname</label>
              <input type="text" class="form-control" name="nickname" id="nickname" placeholder="NickName" required>
              <div class="valid-feedback">
                Se ve bien!
              </div>
              <div class="invalid-feedback">
                Por favor brinde una dirección de email válida
              </div>
            </div>
            <div class="form-group">
              <label for="password">Contraseña</label>
              <input type="password" class="form-control" name="password" id="password" placeholder="Ingrese su contraseña" required>
              <div class="valid-feedback">
                Se ve bien!
              </div>
              <div class="invalid-feedback">
                Por favor ingrese su contraseña
              </div>
            </div>
            <button id="submitBtn" type="submit" class="btn btn-submit">Iniciar Sesión</button>
            <a href="mostrarAlerta">¿Olvidó su contraseña?</a>
        </form>
        <section class="container-sm row g-3" id="registrarSection">
            <a href="registrarusuario1" class="btn"> 
              <button type="button" class="btn btn-outline-secondary w-100">Registrarse</button>
            </a>
        </section>
    </main>
</div>

<div class="part-final d-flex justify-content-center align-items-center">
    <p class="text-center">Todos los derechos reservados, 2024. <br> Laboratorio PA.</p>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
