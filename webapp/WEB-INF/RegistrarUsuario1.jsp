<%@page import="com.model.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="media/styles/RegistrarUsuario.css" rel="stylesheet">
    <title>Registrar Usuario</title>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark fixed-top" style="background-color: #2C2C2C; font-family: Arial, sans-serif;">
    <div class="container">
        <a href="home" class="navbar-brand">
            ITSCODIGO
        </a>
        
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav mx-auto align-items-center"></ul>

            <ul class="navbar-nav align-items-center">
                <li class="nav-item">
                    <a class="nav-link" href="registrarusuario1">
                        Registrar
                    </a>
                </li>
                
                <li class="nav-item">
                    <a class="nav-link" href="formlogin">
                        Login
                    </a>
                </li>
                
                <li class="nav-item">
                    <a class="nav-link" href="#">
                        Ayuda
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>


<div class="container">
    <div class="d-flex justify-content-center align-items-center">
        <div class="contenedor-form mt-5">
            <div class="d-flex flex-column align-items-center">
                <h1 class="text-center">REGÍSTRATE</h1>
                <p class="text-center mt-2">Asegúrate de no elegir un correo usado anteriormente. En caso de datos repetidos se cancelará el proceso.</p>

                <div id="mensajeError"></div>

                <div class="icon-container mb-3">
                    <img alt="Icono de la web" src="media/images/icono.svg" style="width: 80px; height: 80px;">
                </div>
                <form id="myform" action="registrarusuario1" method="post" class="d-flex align-items-center flex-column">
                    <div class="form-group">
                        <label for="nick">Nick</label>
                        <input type="text" placeholder="Alan123" id="nick" name="nick" class="form-control" required>
                    </div>

                    <div class="form-group align-items-start">
                        <label for="correo">Correo</label>
                        <input type="email" placeholder="powerranger@gmail.com" id="correo" name="correo" class="form-control" required>
                    </div>
                    
                    <button type="submit" class="boton-Reg">Registrar Usuario</button>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="part-final footer d-flex justify-content-center align-items-center">
            <p class="text-center">Todos los derechos reservados, 2024. <br> Laboratorio PA.</p>
</div>

<script>
let usuarioValido = false;
let correoValido = false;

let boton = document.querySelector(".boton-Reg");
boton.disabled = true;

document.querySelector("#nick").addEventListener("input", function() {
    const nombreUsuario = this.value;
    const mensajeError = document.querySelector("#mensajeError");
    mensajeError.innerHTML = '';

    if (nombreUsuario.length > 2) {
        let consulta = new XMLHttpRequest();
        consulta.open("POST", "validarAjax", true);
        consulta.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

        consulta.onreadystatechange = function() {
            if (consulta.readyState === 4) {
                if (consulta.status === 200) {
                    if (consulta.responseText === "existe") {
                        mensajeError.innerHTML = "<div class='alert alert-danger'>El nick ya existe</div>";
                        usuarioValido = false;
                    } else if (consulta.responseText === "noexiste") {
                        mensajeError.innerHTML = "<div class='alert alert-success'>Nick disponible.</div>";
                        usuarioValido = true;
                    } else {
                        mensajeError.innerHTML = "<div class='alert alert-danger'>Nick no valido</div>";
                        usuarioValido = false;
                    }
                    validarFormulario(); // Verificar si el formulario es válido
                }
            }
        };

        consulta.send("nick=" + encodeURIComponent(nombreUsuario));
    } else {
        mensajeError.innerHTML = '';
        usuarioValido = false;
        validarFormulario(); 
    }
});


document.querySelector("#correo").addEventListener("change", function() {
    const correo = document.querySelector("#correo").value;
    const mensajeError = document.querySelector("#mensajeError");
    const regexCorreo = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    if (regexCorreo.test(correo)) {
        let consulta = new XMLHttpRequest();
        consulta.open("POST", "validarAjax", true);
        consulta.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

        consulta.onreadystatechange = function() {
            if (consulta.readyState === 4 && consulta.status === 200) {
                if (consulta.responseText === "existe") {
                    mensajeError.innerHTML = "<div class='alert alert-danger'>El correo ya existe</div>";
                    correoValido = false;
                } else if (consulta.responseText === "noexiste") {
                    mensajeError.innerHTML = "<div class='alert alert-success'>Correo disponible.</div>";
                    correoValido = true;
                }
                validarFormulario(); 
            }
        };

        consulta.send("correo=" + encodeURIComponent(correo));
    } else {
        mensajeError.innerHTML = "<div class='alert alert-danger'>Formato de correo inválido</div>";
        correoValido = false;
        validarFormulario();
    }
});


function validarFormulario() {
    if (usuarioValido && correoValido) {
        boton.disabled = false;
    } else {
        boton.disabled = true;
    }
}


</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
