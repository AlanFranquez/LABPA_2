package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webservices.Carrito;
import webservices.Publicador;
import webservices.PublicadorService;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@WebServlet("/mostrarImagen")
public class MostrarImagen extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtén el usuario logueado desde la sesión
        HttpSession session = request.getSession();
        Object usuarioLogueado = session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            System.out.println("No hay un usuario logueado en la sesión.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuario no autenticado.");
            return;
        }

        // Suponiendo que usuarioLogueado tiene un método para obtener el nick
        String nick = ((webservices.Usuario) usuarioLogueado).getNick();
        System.out.println("Mostrando imagen para el usuario: " + nick);

        PublicadorService service = new PublicadorService();
        Publicador port = service.getPublicadorPort();

        String imagenBase64 = port.obtenerImagenUsuario(nick);

        if (imagenBase64 != null) {
            System.out.println("Imagen encontrada y enviada para el usuario: " + nick);
            response.setContentType("image/jpg");
            byte[] imageBytes = Base64.getDecoder().decode(imagenBase64);
            response.getOutputStream().write(imageBytes);
        } else {
            System.out.println("No se encontró imagen para el usuario: " + nick);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Imagen no encontrada");
        }
    }
}
