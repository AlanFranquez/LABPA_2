package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import webservices.*;

@WebServlet("/perfilClienteMOBILE")
public class PerfilClienteMOBILE extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public PerfilClienteMOBILE() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // No crear nueva sesión si no existe

        // Detectar si el acceso proviene de un dispositivo móvil
        String userAgent = request.getHeader("User-Agent");
        boolean isMobile = isMobileDevice(userAgent);
        
        PublicadorService p = new PublicadorService();
        Publicador port = p.getPublicadorPort();
        
        if(!isMobile) {
        	response.sendRedirect("home");
            return;
        }
        
        // Redirigir instantáneamente si no hay sesión o no hay usuario logueado y el acceso es desde móvil
        if ((session == null || session.getAttribute("usuarioLogueado") == null)) {
            response.sendRedirect("home");
            return;
        }
        

        webservices.Usuario usuarioLogueado = (webservices.Usuario) session.getAttribute("usuarioLogueado");
        

        // Redirigir si el usuario logueado no es de tipo Cliente
        if (!(usuarioLogueado instanceof Cliente)) {
            response.sendRedirect("home");
            return;
        }
        

        String parametro = request.getParameter("nickname");
        
        // Redirigir si el nickname del usuario logueado no coincide con el parámetro
        if (parametro == null || !usuarioLogueado.getNick().equals(parametro)) {
            response.sendRedirect("home");
            return;
        }

        // Si todas las condiciones se cumplen, cargar los datos de perfil
        webservices.Cliente cli = port.obtenerCliente(usuarioLogueado.getNick());
     // Obtener las órdenes de compra del cliente
        List<OrdenDeCompra> ordenes = port.getOrdenesCliente(port.getNickCliente(cli));
        
        request.setAttribute("ordenes", ordenes);  // Pasar la lista de ordenes correctamente
        request.setAttribute("usuarioLogueado", usuarioLogueado);
        request.setAttribute("usuario", cli);
        request.getRequestDispatcher("/WEB-INF/InfoPerfilCilenteMOBILE.jsp").forward(request, response);
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    // Método auxiliar para detectar si el dispositivo es móvil
    private boolean isMobileDevice(String userAgent) {
        return userAgent != null && (
            userAgent.contains("Mobile") || 
            userAgent.contains("Android") || 
            userAgent.contains("iPhone") || 
            userAgent.contains("iPad") || 
            userAgent.contains("Windows Phone") || 
            userAgent.contains("BlackBerry")
        );
    }
}
