<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>En proceso</title>
    <style>
        body {
            background-color: white; /* Un color gris claro para el fondo */
            display: flex;
            flex-direction: column;
            justify-content: center; 
            align-items: center; 
            height: 100vh; /* Usar toda la altura de la pantalla */
            margin: 0; 
            padding: 0;
        }

        img {
            max-width: 100%; /* La imagen no excederá el ancho del contenedor */
            height: auto; /* Mantiene la proporción de la imagen */
            margin-bottom: 20px; /* Espacio entre la imagen y el botón */
        }

        a.btn {
            padding: 10px 20px; /* Tamaño del botón */
            font-size: 16px; /* Tamaño de fuente para el botón */
            text-decoration: none; /* Elimina el subrayado del enlace */
            color: white; /* Color de texto del botón */
            background-color: #6c757d; /* Color de fondo del botón */
            border-radius: 5px; /* Bordes redondeados para el botón */
            transition: background-color 0.3s; /* Transición suave para el color de fondo */
        }

        a.btn:hover {
            background-color: #5a6268; /* Color de fondo al pasar el mouse */
        }
    </style>
</head>
<body>
    <img alt="En proceso" src="http://construyendogeografia20.com.uy/wp-content/uploads/2017/09/Web-en-Construccion.png">
    <a class="btn" href="home">Volver</a>
</body>
</html>
