package com.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.model.Cliente;
import com.model.DTCliente;
import com.model.DTProveedor;
import com.model.DtProducto;
import com.model.Factory;
import com.model.ISistema;
import com.model.Proveedor;
import com.model.Usuario;

/**
 * Servlet implementation class PerfilProducto
 */
@WebServlet("/perfilProducto")
public class PerfilProducto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PerfilProducto() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    private ISistema sistema;

    @Override
    public void init() throws ServletException {
        try {
            sistema = Factory.getSistema();  
        } catch (Exception e) {
            throw new ServletException("No se pudo inicializar ISistema", e);  
        }
    }

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Cambiado a false para no crear una nueva sesión

       
        if (session == null) {
            response.sendRedirect("formlogin");
            return;
        }

        Object usuarioLogueado = session.getAttribute("usuarioLogueado");
        
        if(usuarioLogueado == null) {
        	response.sendRedirect("formlogin");
        }
        Usuario user = (Usuario) usuarioLogueado;
        request.setAttribute("usuario", user);

        try {
            String parametro = request.getParameter("producto");
            if (parametro == null) {
                response.sendRedirect("perfilCliente");
                return;
            }
            int paramNumero = Integer.parseInt(parametro);
            DtProducto dtprod = sistema.getDtProducto(paramNumero);

            if (dtprod == null) {
                response.sendRedirect("perfilCliente");
                return;
            }

            request.setAttribute("dtprod", dtprod);
            request.getRequestDispatcher("/WEB-INF/PerfilProducto.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect("perfilCliente");
        } catch (Exception e) {
            // Manejar otras excepciones si es necesario
            response.sendRedirect("errorPage"); // O una página de error adecuada
        }
    }


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
