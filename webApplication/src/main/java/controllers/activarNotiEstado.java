package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import webservices.Cliente;
import webservices.Publicador;
import webservices.PublicadorService;

import java.io.IOException;

@WebServlet("/activarNotiEstado")
public class activarNotiEstado extends HttpServlet {
    private static final long serialVersionUID = 1L;
    PublicadorService p = new PublicadorService();
	Publicador port = p.getPublicadorPort();
    public activarNotiEstado() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        System.out.println("\n\nNO SE CUANDO SE ACTIVA ESTOOOOOOOO, PD: DESDE CONTROLADOR activarNotiEstado.java\n\n");
        // Verificar si el token es válido
        if (token == null || token.isEmpty()) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("Token inválido.");
            return;
        }

        // Buscar el cliente por su token
        Cliente cliente = port.getClientePorToken(token);
        String userId = port.getNickCliente(cliente);
        // Si el cliente existe y el token coincide
        if (cliente != null) {
        	port.setRecibirNotificaciones(userId, true); // Activar notificaciones

            // Generar un nuevo token para mayor seguridad
            String nuevoToken = generarNuevoToken(); // Implementar este método
            port.setTokenDesactivacionCliente(userId, nuevoToken);

            // Responder al cliente
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("Has activado el envío de notificaciones con éxito.");
        } else {
            // Token no válido o cliente no encontrado
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("Token inválido o expirado.");
        }
    }

    // Método auxiliar para generar tokens únicos
    private String generarNuevoToken() {
        return java.util.UUID.randomUUID().toString();
    }
}