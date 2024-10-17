package com.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.model.Factory;
import com.model.ISistema;
import com.model.Producto;
import com.model.Usuario;

@WebServlet("/home")
public class Home extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Home() {
        super();
    }
    
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
        HttpSession session = request.getSession(false);
        
        List<Producto> productos = sist.getAllProductos();

        
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            request.getRequestDispatcher("/WEB-INF/inicioNoLogueado.jsp").forward(request, response);
            return;
        }

        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (productos != null) {
        	request.setAttribute("prods", productos);
        }
        
        

        if (usuarioLogueado != null) {
            request.setAttribute("usuario", usuarioLogueado); 
            request.setAttribute("estado", "logueado");
            request.getRequestDispatcher("/WEB-INF/inicio.jsp").forward(request, response);
        } else {
            response.sendRedirect("formlogin");
            request.setAttribute("estado", "noLogueado");
        }
    }
}
