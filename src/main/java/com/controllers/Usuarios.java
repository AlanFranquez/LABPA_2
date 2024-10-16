package com.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.model.Cliente;
import com.model.DTCliente;
import com.model.Factory;
import com.model.ISistema;
import com.model.Usuario;

/**
 * Servlet implementation class Usuarios
 */
@WebServlet("/Usuarios")
public class Usuarios extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */

	private ISistema sistema;

    @Override
    public void init() throws ServletException {
        try {
            sistema = Factory.getSistema();  // Aquí puede estar fallando
        } catch (Exception e) {
            throw new ServletException("No se pudo inicializar ISistema", e);  // Manejar la excepción
        }
    }
	
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        
		
        Usuario usr = sistema.getUsuario("agusmari");
        if(usr == null) {
        	System.out.print("Hubo un problema");
        } else {
        	System.out.print("No hubo problemas");
        }
        
        
	
		List<DTCliente> clientesPredeterminados = new ArrayList<>();
		String user = request.getParameter("usuario");
		clientesPredeterminados = sistema.listarClientes();
		
		if(user == null) {
			request.setAttribute("clientes", clientesPredeterminados);
			 request.getRequestDispatcher("/WEB-INF/listaUsuarios.jsp").forward(request, response);
			 
		} else {
			Cliente encontrado = (Cliente) sistema.getUsuario(user);
			DTCliente dtEncontrado = encontrado.crearDt();
			
			
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
