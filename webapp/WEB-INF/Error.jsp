<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error</title>
    <link rel="stylesheet" href="styles.css"> <!-- Estilo opcional -->
</head>
<body>
    <h1>Error</h1>
    <p>
        <% String errorMessage = (String) request.getAttribute("error"); %>
        <% if (errorMessage != null) { %>
            <strong>Mensaje de error:</strong> <%= errorMessage %>
        <% } else { %>
            <strong>Se ha producido un error inesperado.</strong>
        <% } %>
    </p>
    <div class="button-group">
        <button onclick="window.location.href='lab2-home';">Ir a la Página de Inicio</button>
        <button onclick="window.history.back();">Volver a la Página Anterior</button>
    </div>
</body>
</html>
