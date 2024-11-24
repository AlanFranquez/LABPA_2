package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import webservices.Publicador;
import webservices.PublicadorService;

import java.io.IOException;
import java.util.List;


@WebServlet("/validarAjax")
public class ValidarAjax extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
       
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        String nick = request.getParameter("nick");
        String correo = request.getParameter("correo");

        PublicadorService p = new PublicadorService();
        Publicador port = p.getPublicadorPort();
        
        List<webservices.Usuario> usuarios = port.listaUsuarios();

        // Validación de nick
        if (nick != null && !nick.isEmpty()) {
            boolean nickExiste = false;
            
            
            
            for (webservices.Usuario u: usuarios) {
            	if (nick.equals(u.getNick())) {
            		nickExiste = true;
            		break;
            	}
            }
            
            if (nickExiste) {
                response.getWriter().write("existe");
            } else {
                response.getWriter().write("noexiste");
            }
            return; 
        }

        if (correo != null && !correo.isEmpty()) {
            boolean correoExiste = false;
            
            for (webservices.Usuario u: usuarios) {
            	if (correo.equals(u.getCorreo())) {
            		correoExiste = true;
            		break;
            	}
            }
            
            if (correoExiste) {
                response.getWriter().write("existe");
            } else {
                response.getWriter().write("noexiste");
            }
            return;
        }

        response.getWriter().write("invalid");
    }
}
