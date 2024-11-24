package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import webservices.*;


/**
 * Servlet implementation class Usuarios
 */
@WebServlet("/Usuarios")
public class Usuarios extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */


   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		// Si no hay usuario logueado
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            System.out.println("No hay usuario logueado. Redirigiendo a inicio no logueado.");
            request.getRequestDispatcher("/WEB-INF/inicioNoLogueado.jsp").forward(request, response);
            return;
        }
        
        PublicadorService p = new PublicadorService();
        Publicador port = p.getPublicadorPort();
        
		webservices.Usuario usr = (webservices.Usuario) session.getAttribute("usuarioLogueado");
        if (usr == null) {
        	System.out.print("Hubo un problema");
        } else {
        	System.out.print("No hubo problemas");
        }
        
        
	
		List<Cliente> clientesPredeterminados = new ArrayList<>();
		String user = request.getParameter("usuario");
		clientesPredeterminados = port.listarClientes();
		
		
		if (user == null) {
			request.setAttribute("clientes", clientesPredeterminados);
			request.getRequestDispatcher("/WEB-INF/listaUsuarios.jsp").forward(request, response);
			 
		} else {
			DtCliente dtEncontrado = port.obtenerDTCliente(user);
			
			
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
