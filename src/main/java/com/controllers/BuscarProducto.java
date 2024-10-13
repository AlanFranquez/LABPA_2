package com.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import com.model.Cliente;
import com.model.Factory;
import com.model.ISistema;
import com.model.Producto;
import com.model.Usuario;

@WebServlet("/buscarproductos")
public class BuscarProducto extends HttpServlet {
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

  
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String searchQuery = request.getParameter("query");
        String ordenacion = request.getParameter("ordenacion");
        
        // Verificar si la sesión es válida
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("home");
            return; 
        }
       

        Usuario user = (Usuario) session.getAttribute("usuarioLogueado");
        List<Producto> productos;

        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            productos = sist.getAllProductos();
        } else {
            productos = sist.buscarProductos(searchQuery);
        }

        if ("alfabeticamente".equals(ordenacion)) {
            productos.sort(Comparator.comparing(Producto::getNombre));
        } else if ("precio".equals(ordenacion)) {
            productos.sort(Comparator.comparing(Producto::getPrecio).reversed());
        } else if("cantidad".equals(ordenacion)) {
        	productos.sort(Comparator.comparing(Producto::getCantidadComprada).reversed());
        }
    
        
        
        if (user instanceof Cliente) {
            Cliente cliente = (Cliente) user;
            request.setAttribute("carrito", cliente.getCarrito());
        }
        
      

        request.setAttribute("usuarioLogueado", user);
        request.setAttribute("productos", productos);
        request.getRequestDispatcher("/WEB-INF/listaProductos.jsp").forward(request, response);
    }

	
}
