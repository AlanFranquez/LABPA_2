<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Producto Agregado</title>
    <link rel="stylesheet" href="styles.css"> <!-- Puedes agregar tu hoja de estilos aquí -->
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            text-align: center;
            padding: 50px;
        }
        .message {
            background-color: #4CAF50; /* Verde */
            color: white;
            padding: 15px;
            border-radius: 5px;
            display: inline-block;
        }
        .button-group {
            margin-top: 20px;
        }
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            color: white;
        }
        .btn-home {
            background-color: #007BFF; /* Azul */
        }
        .btn-home:hover {
            background-color: #0056b3; /* Azul oscuro */
        }
    </style>
</head>
<body>

    <div class="message">
        Producto agregado con éxito.
    </div>

    <div class="button-group">
        <a class="btn btn-home" href="lab-2/home">Volver a la página de inicio</a>
        <a class="btn btn-home" href="perfilProveedor">Volver al perfil del proveedor</a>
    </div>

</body>
</html>
