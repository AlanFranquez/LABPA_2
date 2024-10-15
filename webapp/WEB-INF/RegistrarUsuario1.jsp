<%@page import="com.model.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.model.Producto" %>
<%@page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="media/styles/RegistrarUsuario.css" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
    <title>Registrar Usuario</title>
</head>
<body>

<%
    List<String> usuarios = (List<String>) request.getAttribute("usuariosLista");
    if (usuarios != null) {
%>
<script>
    // Crear un array de strings con los usuarios existentes
    var usuariosExistentes = [
        <%
            for (int i = 0; i < usuarios.size(); i++) {
                out.print("\"" + usuarios.get(i) + "\"");
                if (i < usuarios.size() - 1) {
                    out.print(", "); // Agregar coma entre elementos
                }
            }
        %>
    ];
</script>
<%
    } else {
%>
<script>
    var usuariosExistentes = [];
</script>
<%
    }
%>

<div class="container">
    <div class="d-flex justify-content-center align-items-center">
        <div class="contenedor-form mt-5">
            <div class="d-flex flex-column align-items-center">
                <h1 class="text-center" style="padding: 5px 0px;">REGÍSTRATE</h1>
                <p class="text-center mt-2" style="color: #7E7979; font-size: 16px; width: 500px; margin: 20px auto;">
                    Asegúrate de no elegir un correo usado anteriormente. En caso de datos repetidos se cancelará el proceso.
                </p>

                <!-- Contenedor para el mensaje de error -->
                <div id="error-message-container"></div>

                <div class="icon-container mb-3">
                    <img alt="Icono de la web" src="media/images/icono.svg" style="width: 80px; height: 80px;">
                </div>
                <form id="myform" action="registrarusuario1" method="post" class="d-flex align-items-center flex-column" onsubmit="return validateForm()">
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

<!-- Parte de abajo -->
<div class="part-final d-flex justify-content-center align-items-center">
    <p class="text-center">Todos los derechos reservados, 2024. <br> Laboratorio PA.</p>
</div>

<script>
    document.getElementById("nick").addEventListener("input", function() {
        let nick = document.querySelector("#nick").value;
        
        console.log("Hello World");
        
        var errorMessageContainer = document.getElementById('error-message-container');
        var submitButton = document.querySelector("button[type='submit']");

        if (nick.length > 3) {
            // Comprobar si el nombre ya existe en el array
            if (usuariosExistentes.includes(nick)) {
                this.classList.add("is-invalid");
                console.log("is invalido");
                // Agregar clase para marcar el campo
                errorMessageContainer.innerHTML = "<div class='alert alert-danger'>El nombre de usuario no está disponible.</div>";
                submitButton.disabled = true; // Desactiva el botón
            } else {
                this.classList.remove("is-invalid");
                console.log("es valido");
                errorMessageContainer.innerHTML = "<div class='alert alert-success'>Bonito Usuario, es valido</div>";
                submitButton.disabled = false; // Habilitar el botón
            }
        } else {
            this.classList.remove("is-invalid");
            errorMessageContainer.innerHTML = ""; // Limpiar mensaje de error
            submitButton.disabled = false;
        }
    });
    
 // Crear un array de strings con los usuarios existentes
    var correosExistentes = [
        <%
        	List<String> correos = (List<String>) request.getAttribute("correos");
        	
            for (int i = 0; i < correos.size(); i++) {
                out.print("\"" + correos.get(i) + "\"");
                if (i < usuarios.size() - 1) {
                    out.print(", "); 
                }
            }
        %>
    ];
    
    
    document.getElementById("correo").addEventListener("input", function() {
        let correo = document.querySelector("#correo").value;
        
        console.log("Hello World");
        
        var errorMessageContainer = document.getElementById('error-message-container');
        var submitButton = document.querySelector("button[type='submit']");

        if (correo.length > 3) {
            // Comprobar si el nombre ya existe en el array
            if (correosExistentes.includes(correo)) {
                this.classList.add("is-invalid");
                console.log("is invalido");
                // Agregar clase para marcar el campo
                errorMessageContainer.innerHTML = "<div class='alert alert-danger'>El correo está en uso.</div>";
                submitButton.disabled = true; // Desactiva el botón
            } else {
                this.classList.remove("is-invalid");
                console.log("es valido");
                errorMessageContainer.innerHTML = "<div class='alert alert-success'>Correo valido</div>";
                submitButton.disabled = false; // Habilitar el botón
            }
        } else {
            this.classList.remove("is-invalid");
            errorMessageContainer.innerHTML = ""; // Limpiar mensaje de error
            submitButton.disabled = false;
        }
    });
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
