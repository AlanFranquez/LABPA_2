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

@WebServlet("/desactivarNotiEstado")
public class desactivarNotiEstado extends HttpServlet {
    private static final long serialVersionUID = 1L;
    PublicadorService p = new PublicadorService();
	Publicador port = p.getPublicadorPort();
    public desactivarNotiEstado() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");

        if (token == null || token.isEmpty()) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("Token inválido.");
            return;
        }

        Cliente cliente = port.getClientePorToken(token);
        String userId = port.getNickCliente(cliente);
        if (cliente != null) {
            // Desactiva el envío de notificaciones y borra el token
        	port.setRecibirNotificaciones(userId, false);
            port.setTokenDesactivacionCliente(userId, null);

            // Respuesta al usuario en UTF-8
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("Has desactivado el envío de notificaciones con éxito.");
        } else {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("Token inválido o expirado.");
        }
    }
}