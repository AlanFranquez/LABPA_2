package controllers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.market.svcentral.EstadoSesion;
import com.market.svcentral.Factory;
import com.market.svcentral.ISistema;
import com.market.svcentral.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Home
 */
@WebServlet ("/formlogin")
public class FormLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ISistema sist;

    @Override
    public void init() throws ServletException {
        try {
            sist = Factory.getSistema();
        } catch (Exception e) {
            throw new ServletException("No se pudo inicializar ISistema", e);
        }
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

        EstadoSesion nuevoEstado;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
        EntityManager em = emf.createEntityManager();

        Usuario usr = em.find(Usuario.class, nickname);
        
        if (usr == null) {
            objSession.setAttribute("errorMsg", "Los datos no son válidos");
            response.sendRedirect("formlogin");
            return;
        }

        if (usr.getNick().equals(nickname) && usr.getContrasena().equals(password)) {
            nuevoEstado = EstadoSesion.LOGIN_CORRECTO;
            request.setAttribute("logueado", usr);
            objSession.setAttribute("usuarioLogueado", usr);
            response.sendRedirect("home");
        } else {
            nuevoEstado = EstadoSesion.LOGIN_INCORRECTO;
            objSession.setAttribute("errorMsg", "Los datos no son válidos");
            request.getRequestDispatcher("/WEB-INF/IniciarSesion.jsp").forward(request, response);
        }

        objSession.setAttribute("estado", nuevoEstado);
        
        // Cerrar recursos
        try {
            em.close();
            emf.close();
        } catch (Exception e) {
            e.printStackTrace();  // Manejo de errores
        }
    }
}
