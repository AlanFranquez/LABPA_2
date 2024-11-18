package controllers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webservices.Publicador;
import webservices.PublicadorService;

/**
 * Servlet implementation class Home
 */
@WebServlet ("/formlogin")
public class FormLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;


    @Override
    public void init() throws ServletException {
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/IniciarSesion.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession objSession = request.getSession();
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");
        
        String userAgent = request.getHeader("User-Agent");
        
        PublicadorService p = new PublicadorService();
        Publicador port = p.getPublicadorPort();
        
        
        if((userAgent != null) && (
                userAgent.contains("Mobile") || 
                userAgent.contains("Android") || 
                userAgent.contains("iPhone") || 
                userAgent.contains("iPad") || 
                userAgent.contains("Windows Phone") || 
                userAgent.contains("BlackBerry")
            )) {
        	request.getRequestDispatcher("/WEB-INF/construccion.jsp").forward(request, response);
        	return;
        } 

        if (nickname == null || nickname.isEmpty() || password == null || password.isEmpty()) {
            objSession.setAttribute("errorMsg", "Por favor, ingrese todos los datos.");
            response.sendRedirect("formlogin");
            return;
        }

        String nuevoEstado;

        webservices.Usuario usr = port.obtenerUsuario(nickname);
       
        
        
        if (usr == null) {
            objSession.setAttribute("errorMsg", "Los datos no son válidos");
            response.sendRedirect("formlogin");
            return;
        }

        if (usr.getNick().equals(nickname) && usr.getContrasena().equals(password)) {
            nuevoEstado = "logueado";
            request.setAttribute("logueado", usr);
            objSession.setAttribute("usuarioLogueado", usr);
            response.sendRedirect("home");
        } else {
            nuevoEstado = "nologueado";
            objSession.setAttribute("errorMsg", "Los datos no son válidos");
            request.getRequestDispatcher("/WEB-INF/IniciarSesion.jsp").forward(request, response);
        }

        objSession.setAttribute("estado", nuevoEstado);
        
      
    }
}
