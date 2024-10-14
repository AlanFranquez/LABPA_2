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

<div class="container">
    <div class="d-flex justify-content-center align-items-center">
        <div class="contenedor-form mt-5">
            <div class="d-flex flex-column align-items-center">
                <h1 class="text-center" style="padding: 5px 0px;">REG�STRATE</h1>
                <p class="text-center mt-2" style="color: #7E7979; font-size: 16px; width: 500px; margin: 20px auto;">
                    Aseg�rate de no elegir un correo usado anteriormente. En caso de datos repetidos se cancelar� el proceso.
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
    // Esta funci�n se ejecuta cuando la p�gina se carga
    window.onload = function() {
        var errorMsg = '<%= session.getAttribute("errorMsg") %>';
        if (errorMsg === "El nickname o correo ya est�n registrados") {
            var alertDiv = document.createElement('div');
            alertDiv.className = 'alert alert-danger';
            alertDiv.role = 'alert';
            alertDiv.innerHTML = errorMsg;

            var errorMessageContainer = document.getElementById('error-message-container');
            errorMessageContainer.appendChild(alertDiv);

            <%
                session.removeAttribute("errorMsg");
            %>
        }
    };

    function validateForm() {
        var nick = document.getElementById("nick").value;
        var correo = document.getElementById("correo").value;

        if (nick.trim() === "") {
            alert("El campo Nick no puede estar vac�o.");
            return false; // Evita que el formulario se env�e
        }

        var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailPattern.test(correo)) {
            alert("Por favor, introduce un correo electr�nico v�lido.");
            return false; 
        }

        return true; 
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
