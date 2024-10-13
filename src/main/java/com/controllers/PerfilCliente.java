package com.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.model.Cliente;
import com.model.DTCliente;
import com.model.Usuario;

/**
 * Servlet implementation class Perfil
 */
@WebServlet("/perfilCliente")
public class PerfilCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PerfilCliente() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("formlogin");
            return;
        }
        
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        Cliente cli = (Cliente) usuarioLogueado;
        DTCliente dtcli = cli.crearDt();
        
        String parametro = request.getParameter("nickname");
        
        if (usuarioLogueado.getNick().equals(parametro)) {
        	request.setAttribute("usuarioLogueado", usuarioLogueado);
            request.setAttribute("usuario", dtcli);
            request.getRequestDispatcher("/WEB-INF/InfoPerfilCliente.jsp").forward(request, response);
            return;
        }
        
       
        response.sendRedirect("home");
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
