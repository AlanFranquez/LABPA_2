package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.market.svcentral.Cliente;
import com.market.svcentral.DTCliente;
import com.market.svcentral.Usuario;
import com.market.svcentral.*;

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
        
        String userAgent = request.getHeader("User-Agent");
        
        
        if((userAgent != null) && (
                userAgent.contains("Mobile") || 
                userAgent.contains("Android") || 
                userAgent.contains("iPhone") || 
                userAgent.contains("iPad") || 
                userAgent.contains("Windows Phone") || 
                userAgent.contains("BlackBerry")
            )) {
        	request.getRequestDispatcher("/WEB-INF/construccion.jsp").forward(request, response);
        	return;
        } 
        
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("formlogin");
            return;
        }
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        Cliente cli = (Cliente) em.find(Usuario.class, usuarioLogueado.getNick());
        
        if(cli == null) {
        	System.out.print("No se encontró al usuario en la bd");
        	response.sendRedirect("home");
        	return;
        }
        
        DTCliente dtcli = cli.crearDt();
        
        List<DTOrdenDeCompra> ordenes= cli.mostrarCompras();
        
        String parametro = request.getParameter("nickname");
        
        if (usuarioLogueado.getNick().equals(parametro)) {
        	request.setAttribute("usuarioLogueado", usuarioLogueado);
            request.setAttribute("usuario", dtcli);
            request.setAttribute("ordenes", ordenes);
            request.getRequestDispatcher("/WEB-INF/InfoPerfilCliente.jsp").forward(request, response);
            return;
        }
        
       
        response.sendRedirect("home");
        
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
