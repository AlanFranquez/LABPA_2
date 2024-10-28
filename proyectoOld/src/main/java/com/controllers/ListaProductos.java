package com.controllers;

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
import com.model.Carrito;
import com.model.Producto;


/**
 * Servlet implementation class ListaProductos
 */
@WebServlet("/ListaProductos")
public class ListaProductos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListaProductos() {
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Producto> productos = sist.getAllProductos();
        
        request.setAttribute("productos", productos); // Guardar la lista de productos en el request
        request.getSession().setAttribute("sistema", sist);
        request.getRequestDispatcher("/WEB-INF/listaProductos.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("agregarAlCarrito".equals(action)) {
            agregarAlCarrito(request, response);
        }
    }

	private void agregarAlCarrito(HttpServletRequest request, HttpServletResponse response) throws IOException {


        // Obtener la sesión actual
        HttpSession session = request.getSession();
     
        // Obtener el carrito de la sesión o crear uno nuevo si no existe
        Carrito carrito = (Carrito) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new Carrito(); 
            session.setAttribute("carrito", carrito);
        }
        

        

        // Guardar el carrito actualizado en la sesión
        session.setAttribute("carrito", carrito);

        // Redirigir de nuevo a la lista de productos o al carrito
        response.sendRedirect("WEB-INF/listaProductos.jsp"); 
    }
}
