package com.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.model.Factory;
import com.model.ISistema;
import com.model.Usuario;

@WebServlet("/validarAjax")
public class ValidarAjax extends HttpServlet {
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        String nick = request.getParameter("nick");
        String correo = request.getParameter("correo");

        List<Usuario> usuarios = sist.listaUsuarios();

        // Validación de nick
        if (nick != null && !nick.isEmpty()) {
            boolean nickExiste = false;
            
            
            
            for (Usuario u: usuarios) {
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
            
            for (Usuario u: usuarios) {
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
