package controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/registrarusuario1")
public class RegistrarUsuarios1 extends HttpServlet {
    private static final long serialVersionUID = 1L;

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/RegistrarUsuario1.jsp").forward(request, response);
        System.out.println("Redirigiendo a RegistrarUsuario1.jsp inicio Servlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String nick = request.getParameter("nick");
        String correo = request.getParameter("correo");
        HttpSession objSession = request.getSession();
        objSession.removeAttribute("errorMsg");

        if (nick == null || nick.isEmpty() || correo == null || correo.isEmpty()) {
            objSession.setAttribute("errorMsg", "El nickname o correo no pueden estar vacíos");
            response.sendRedirect("/WEB-INF/Error.jsp");
            return;
        }
        
        
        objSession.setAttribute("nick", nick);
        objSession.setAttribute("correo", correo);
        response.sendRedirect("registrarusuario2");
    }

}