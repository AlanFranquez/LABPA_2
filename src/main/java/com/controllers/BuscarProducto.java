package com.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import com.model.Factory;
import com.model.ISistema;
import com.model.Producto;

/**
 * Servlet implementation class BuscarProducto
 */
@WebServlet("/buscarproductos")
public class BuscarProducto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuscarProducto() {
        super();
        // TODO Auto-generated constructor stub
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
    
    

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchQuery = request.getParameter("search");

        List<Producto> productos;
        
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            // Si no hay consulta, obtén todos los productos
            productos = sist.getAllProductos(); // Método que recupera todos los productos
        } else {
            // Lógica para buscar productos según la consulta
            productos = sist.buscarProductos(searchQuery);
        }
        
        request.setAttribute("productos", productos);
        request.getRequestDispatcher("/WEB-INF/PaginaResultados.jsp").forward(request, response);
    }

}
