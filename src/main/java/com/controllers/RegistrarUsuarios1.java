package com.controllers;

import java.io.IOException;
import com.exceptions.UsuarioException;
import com.model.Factory;
import com.model.ISistema;
import com.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/registrarusuario1")
public class RegistrarUsuarios1 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ISistema sist;

    @Override
    public void init() throws ServletException {
        try {
            sist = Factory.getSistema();  // Inicializa el sistema
        } catch (Exception e) {
            throw new ServletException("No se pudo inicializar ISistema", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/RegistrarUsuario1.jsp").forward(request, response);
        System.out.println("Redirigiendo a RegistrarUsuario1.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener parámetros del formulario
        String nick = request.getParameter("nick");
        String correo = request.getParameter("correo");
        HttpSession objSession = request.getSession();

        // Validar los datos de entrada
        if (nick == null || nick.isEmpty() || correo == null || correo.isEmpty()) {
            objSession.setAttribute("errorMsg", "Nickname o correo no pueden estar vacíos");
            response.sendRedirect("Error.jsp");  // Redirige a una página de error
            return;
        }

        try {
            // Verificar si el usuario ya existe
            Usuario prueba = sist.getUsuario(nick);
            Usuario prueba2 = sist.getUserByEmail(correo);

            if (prueba != null || prueba2 != null) {
                objSession.setAttribute("errorMsg", "El nickname o correo ya están registrados");
                request.getRequestDispatcher("/WEB-INF/RegistrarUsuario1.jsp").forward(request, response);  // Redirige al formulario original
            } else {
                // Si los datos son válidos, guarda el usuario en sesión y redirige a la segunda página
                objSession.setAttribute("nickname", nick);
                objSession.setAttribute("correo", correo);
                System.out.println("Redirigiendo a RegistrarUsuario2.jsp");
                request.getRequestDispatcher("/WEB-INF/RegistrarUsuario2.jsp").forward(request, response);
            }

        } catch (UsuarioException e) {
            // Manejo de excepción si no se puede obtener el usuario por email
            e.printStackTrace();
            objSession.setAttribute("errorMsg", "Error al verificar los datos del usuario");
            response.sendRedirect("Error.jsp");
        }
    }
}
