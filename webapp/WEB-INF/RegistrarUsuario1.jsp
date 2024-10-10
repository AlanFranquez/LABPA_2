<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <%@ include file="template/BarraNavSinReg.html" %>
</head>
<body>
   

    <!-- Parte de registro -->
    <div class="container">
        <div class="d-flex justify-content-center align-items-center">
            <div class="contenedor-form mt-5">
                <div class="d-flex flex-column align-items-center">
                    <h1 class="text-center" style="padding: 5px 0px;">REGÍSTRATE</h1>
                    <p class="text-center mt-2" style="color: #7E7979; font-size: 16px; width: 500px; margin: 20px auto;">
                        Asegúrate de no elegir un correo usado anteriormente. En caso de datos repetidos se cancelará el proceso.
                    </p>
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

    <!-- Parte de abajo -->
    <div class="part-final d-flex justify-content-center align-items-center">
        <p class="text-center">Todos los derechos reservados, 2024. <br> Laboratorio PA.</p>
    </div>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="RegistrarUsuario1.js"></script>
</body>
</html>
