package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.market.svcentral.Cliente;
import com.market.svcentral.Sistema;

@WebServlet("/desactivarNotiEstado")
public class desactivarNotiEstado extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private Sistema sistema;

    public desactivarNotiEstado() {
        super();
        sistema = Sistema.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");

        if (token == null || token.isEmpty()) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("Token inválido.");
            return;
        }

        Cliente cliente = sistema.getClientePorToken(token);
        if (cliente != null && token.equals(cliente.getTokenDesactivacion())) {
            // Desactiva el envío de notificaciones y borra el token
            cliente.setRecibirNotificaciones(false);
            cliente.setTokenDesactivacion(null);

            // Respuesta al usuario en UTF-8
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("Has desactivado el envío de notificaciones con éxito.");
        } else {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("Token inválido o expirado.");
        }
    }
}
