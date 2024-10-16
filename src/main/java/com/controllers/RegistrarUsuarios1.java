package com.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        List<Usuario> usuarios = sist.listaUsuarios();
        List<String> usuariosStrings = new ArrayList<String>();
        List<String> correos = new ArrayList<String>();
        for(Usuario u : usuarios) {
        	usuariosStrings.add(u.getNick());
        	correos.add(u.getCorreo());
        }
        
        
        
        request.setAttribute("usuariosLista", usuariosStrings);
    	request.setAttribute("correos", correos);
    	request.getRequestDispatcher("/WEB-INF/RegistrarUsuario1.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      
        HttpSession objSession = request.getSession();
        objSession.removeAttribute("errorMsg");

        String nick = request.getParameter("nick");
        String correo = request.getParameter("correo");
        
        
        objSession.setAttribute("nick", nick);
        objSession.setAttribute("correo", correo);
        response.sendRedirect("registrarusuario2");
    }

}
