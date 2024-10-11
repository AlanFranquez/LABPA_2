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
        System.out.println("Redirigiendo a RegistrarUsuario1.jsp inicio Servlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener parámetros del formulario
        String nick = request.getParameter("nick");
        String correo = request.getParameter("correo");
        HttpSession objSession = request.getSession();

        // Validar los datos
        if (nick == null || nick.isEmpty() || correo == null || correo.isEmpty()) {
            objSession.setAttribute("errorMsg", "El nickname o correo no pueden estar vacíos");
            response.sendRedirect("/WEB-INF/Error.jsp");
            return;
        }

        try {
            // Verificar si el usuario ya existe
            Usuario prueba = null;
            Usuario prueba2 = null;

            prueba = sist.getUsuario(nick);

            try {
                prueba2 = sist.getUserByEmail(correo);
            } catch (UsuarioException e) {
                // El usuario por correo no fue encontrado, pero eso no es un error grave
                System.out.println("Usuario con correo no encontrado");
            }

            if (prueba != null || prueba2 != null) {
                objSession.setAttribute("errorMsg", "El nickname o correo ya están registrados");
                System.out.println("Redirigiendo a RegistrarUsuario1.jsp con error");
                request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request, response);
            } else {
                // Si los datos son válidos, guarda el usuario en sesión y redirige a la segunda página
                objSession.setAttribute("nickname", nick);
                objSession.setAttribute("correo", correo);
                System.out.println("Redirigiendo a RegistrarUsuario2.jsp");
                response.sendRedirect(request.getContextPath() + "/registrarusuario2");
            }

        } catch (Exception e) {
            // Manejo de excepción genérica si ocurre algún otro problema
            e.printStackTrace();
            objSession.setAttribute("errorMsg", "Error al verificar los datos del usuario");
            System.out.println("Error general");
            response.sendRedirect("/WEB-INF/Error.jsp");
        }
    }

}
