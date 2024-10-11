package com.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.model.Carrito;

/**
 * Servlet implementation class Carrito
 */
@WebServlet("/Carrito")
public class CarritoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CarritoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/Carrito.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    Carrito carrito = (Carrito) request.getSession().getAttribute("carrito");

	    if (carrito != null) {
	        carrito.generarOrdenDeCompra();
	    } else {
	        // Manejar el caso en que el carrito es nulo
	        request.setAttribute("error", "El carrito no se ha encontrado en la sesión.");
	        request.getRequestDispatcher("/WEB-INF/Carrito.jsp").forward(request, response);
	    }
	}
}


