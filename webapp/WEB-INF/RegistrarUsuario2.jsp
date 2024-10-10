<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="media/styles/RegistrarUsuario2.css" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
    <title>Registrar Usuario</title>
    <%@ include file="template/BarraNavSinReg.html" %>
</head>
<body>
    
    <!-- 
    Parte de registro
    -->
    
    <div class="d-flex justify-content-center m-5">
        <div class="contenedor-form">
            <div class="d-flex flex-column align-items-center">
                <form action="" id="myform" class="d-flex align-items-center flex-column">
                
                    <h2 class="form-group text-center">Completar los datos</h2>
                    <p class="text-center" style="color: #7E7979; font-size: 16px; width: 500px; margin: 10px auto;">
                    Continúa tu proceso de registro
                    </p>
                    
                    <div class="form-group">
                        <label for="nombre">Nombre *</label>
                        <input type="text" placeholder="Alan" id="nombre" name="nombre" class="form-control">
                    </div>
                
                    <div class="form-group">
                        <label for="apellido">Apellido *</label>
                        <input type="text" placeholder="Perez" id="apellido" name="apellido" class="form-control">
                    </div>
                    
                    <div class="form-group align-items-start">
                        <label for="imagen">Imagen *</label>
                        <input type="file" placeholder="Ingresa una imagen" id="imagen" name="imagen" class="form-control">
                    </div>
                    
                    <div class="form-group align-items-start">
                        <label for="nacimiento">Fecha de Nacimiento *</label>
                        <input type="date" id="nacimiento" name="nacimiento" class="form-control">
                    </div>
                    
                    <div class="form-group">
                        <label for="password">Contraseña *</label>
                        <input type="password" placeholder="Ingrese su contraseña" id="password" name="password" class="form-control">
                    </div>

                    <div class="form-group">
                        <label for="confirmPassword">Confirmar contraseña *</label>
                        <input type="password" placeholder="Confirme su contraseña" id="confirmPassword" name="confirmPassword" class="form-control">
                    </div>

                    <div class="form-group">
                        <label for="tipoUsuario">Tipo de Usuario *</label>
                        <select id="tipoUsuario" name="tipoUsuario" class="form-control">
                            <option value="cliente">Cliente</option>
                            <option value="proveedor">Proveedor</option>
                        </select>
                    </div>

                    <div class="form-group" id="datosProveedor" style="display: none;">
                        <label for="nombreCompania">Nombre de la Compañía *</label>
                        <input type="text" placeholder="Ingrese el nombre de la compañía" id="nombreCompania" name="nombreCompania" class="form-control">
                    </div>

                    <div class="form-group" id="linkCompania" style="display: none;">
                        <label for="sitioWeb">Sitio Web de la Compañía *</label>
                        <input type="url" placeholder="https://www.ejemplo.com" id="sitioWeb" name="sitioWeb" class="form-control">
                    </div>

                    <button type="submit" class="boton-Reg">
                        Finalizar Registro
                    </button>
                    
                </form>
            </div>
        </div>
    </div>

    <!-- 
    parte de abajo
    -->

    <div class="part-final d-flex justify-content-center align-items-center" style="background-color: black;">
        <p class="text-center">Todos los derechos reservados, 2024. <br> Laboratorio PA.</p>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="RegistrarUsuario2.js"></script>
    <script>
        document.getElementById("tipoUsuario").addEventListener("change", function() {
            var tipo = this.value;
            document.getElementById("datosProveedor").style.display = tipo === "proveedor" ? "block" : "none";
            document.getElementById("linkCompania").style.display = tipo === "proveedor" ? "block" : "none";
        });
    </script>
</body>
</html>
