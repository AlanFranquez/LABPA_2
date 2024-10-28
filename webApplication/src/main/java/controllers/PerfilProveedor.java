package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.market.svcentral.DTProveedor;
import com.market.svcentral.Proveedor;

/**
 * Servlet implementation class Perfil
 */
@WebServlet("/perfilProveedor")
public class PerfilProveedor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public PerfilProveedor() {
        super();
    }

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        if (session == null) {
            response.sendRedirect("formlogin");
            return;
        }
        
        
        Proveedor usuarioLogueado = (Proveedor) session.getAttribute("usuarioLogueado");
        DTProveedor dtprov = usuarioLogueado.crearDt();
        
        String parametro = request.getParameter("nickname");
        
        if (usuarioLogueado.getNick().equals(parametro)) {
            request.setAttribute("usuario", dtprov);
            System.out.println("Tipo de usuario almacenado en sesión: " + usuarioLogueado.getClass().getName());
            request.getRequestDispatcher("/WEB-INF/InfoPerfilProveedor.jsp").forward(request, response);
            return;
        }
        
       
        response.sendRedirect("home");
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
