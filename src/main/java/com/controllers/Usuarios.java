package com.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.exceptions.UsuarioRepetidoException;
import com.model.Cliente;
import com.model.DTCliente;
import com.model.DTFecha;
import com.model.Factory;
import com.model.ISistema;

/**
 * Servlet implementation class Usuarios
 */
@WebServlet("/Usuarios")
public class Usuarios extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */

	private static ISistema sist = Factory.getSistema();
    
	
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DTFecha fecha1 = new DTFecha(1, 1, 1990);
        DTFecha fecha2 = new DTFecha(15, 6, 1985);
        DTFecha fecha3 = new DTFecha(5, 6, 1990);

        
        try {
        	sist.agregarCliente("Juan", "Juan123", "Perez", "Juan@gmail.com", fecha1, "123", "123");
        	sist.agregarCliente("Maria", "agusmari", "Agustina", "mariaagustina@gmail.com", fecha1, "123", "123");
        	sist.agregarCliente("Alberto", "albert1341", "Hernandez", "Ahernandez@gmail.com", fecha2, "123", "123");
		} catch (UsuarioRepetidoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
		if(sist.getUsuario("Juan123") == null) {
			System.out.print("no hay nada");
		}
	
		List<DTCliente> clientesPredeterminados = new ArrayList<>();
		String user = request.getParameter("usuario");
		clientesPredeterminados = sist.listarClientes();
		
		if(user == null) {
			request.setAttribute("clientes", clientesPredeterminados);
			 request.getRequestDispatcher("/WEB-INF/listaUsuarios.jsp").forward(request, 		response);
			 
		} else {
			Cliente encontrado = (Cliente) sist.getUsuario(user);
			DTCliente dtEncontrado = encontrado.crearDt();
			if(encontrado == null) {
				// HACER UNA PAGINA DE ERROR O ALGo
			}
			
			request.setAttribute("user", dtEncontrado);
			request.getRequestDispatcher("/WEB-INF/usuarioIndividual.jsp").forward(request, response);
		}
		

		
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
