package com.controllers;
import java.io.IOException;

import com.model.EstadoSesion;
import com.model.Factory;
import com.model.ISistema;
import com.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


/**
 * Servlet implementation class Home
 */
@WebServlet ("/formlogin")
public class FormLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FormLogin() {
        super();
        // TODO Auto-generated constructor stub
    }


    private ISistema sist;

    @Override
    public void init() throws ServletException {
        try {
            sist = Factory.getSistema();  // Aquí puede estar fallando
        } catch (Exception e) {
            throw new ServletException("No se pudo inicializar ISistema", e);  // Manejar la excepción
        }
    }
   
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("/WEB-INF/IniciarSesion.jsp").forward(request, response);
	}


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession objSession = request.getSession();
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");
        
        
        
        EstadoSesion nuevoEstado;
     
        
        
        Usuario usr = sist.getUsuario(nickname);
        
        if (usr == null) {
        	objSession.setAttribute("errorMsg", "Los datos no son válidos");
        	response.sendRedirect("formlogin");
        	return;
        }
        
        
		
		if (usr.getNick().equals(nickname) && usr.getContrasena().equals(password)) {
		    System.out.print("Identificaodo correctamente");
			nuevoEstado = EstadoSesion.LOGIN_CORRECTO;
		    request.setAttribute("logueado", usr);
		    objSession.setAttribute("usuarioLogueado", usr); 
		    response.sendRedirect("home");
		} else {
		    nuevoEstado = EstadoSesion.LOGIN_INCORRECTO;
		    System.out.print("Identificaodo ");
		    objSession.setAttribute("errorMsg", "Los datos no son válidos");
		    request.getRequestDispatcher("/WEB-INF/IniciarSesion.jsp").forward(request, response);
		}
        
        objSession.setAttribute("estado", nuevoEstado);
    }

}
