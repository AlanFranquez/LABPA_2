package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import webservices.Cliente;
import webservices.Publicador;
import webservices.PublicadorService;
import webservices.Usuario;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;

@WebServlet("/actualizarNotificaciones")
public class actualizarNotiEstado extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    PublicadorService p = new PublicadorService();
	Publicador port = p.getPublicadorPort();
    
	public actualizarNotiEstado() {
        super();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	try {
            String userId = request.getParameter("userId");
            boolean activar = (request.getParameter("activar")) != null;
            Usuario u = port.obtenerUsuario(userId);
            if (u != null) {
            	port.setRecibirNotificaciones(userId, activar);
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
