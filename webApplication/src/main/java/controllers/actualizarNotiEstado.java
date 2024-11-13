package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;

import com.market.svcentral.Cliente;
import com.market.svcentral.Sistema;

@WebServlet("/actualizarNotificaciones")
public class actualizarNotiEstado extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Sistema sistema;

    public actualizarNotiEstado() {
        super();
        sistema = Sistema.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String userId = request.getParameter("userId");
            boolean activar = request.getParameter("activar") != null;

            Cliente cliente = sistema.getClientePorNick(userId);

            if (cliente != null) {
                cliente.setRecibirNotificaciones(activar);
                request.setAttribute("success", "Notificaciones actualizadas");
            } else {
                request.setAttribute("error", "Cliente no encontrado");
            }

            // Reenviar la solicitud a `perfilCliente`
            String nickname = request.getParameter("nickname");
            request.getRequestDispatcher("/perfilCliente?nickname=" + nickname).forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error procesando la solicitud");
            String nickname = request.getParameter("nickname");
            request.getRequestDispatcher("/perfilCliente?nickname=" + nickname).forward(request, response);
        }
    }
}

