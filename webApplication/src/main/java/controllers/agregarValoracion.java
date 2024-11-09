package controllers;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.market.svcentral.Cliente;
import com.market.svcentral.Factory;
import com.market.svcentral.ISistema;
import com.market.svcentral.Producto;
import com.market.svcentral.exceptions.ProductoException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet("/agregarValoracion")
public class agregarValoracion extends HttpServlet{
	private static final long serialVersionUID = 1L;
	       
    private ISistema sist;
    @Override
	public void init() throws ServletException {
    	try {
    		sist = Factory.getSistema();
    	} catch (Exception exeption) {
    		throw new ServletException("No se pudo inicializar ISistema", exeption);
    	}
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String rate = request.getParameter("rate");
		String parametro = request.getParameter("dtprod");
		System.out.println("puntaje: " + rate);
		
		if (parametro == null || parametro.isEmpty()) {
		    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El producto no está disponible.");
		    return;
		}
		if (rate == null || rate.isEmpty()) {
			System.out.println("No seleccionaste un valor");
			return;
		}
		int numRef = Integer.parseInt(parametro);
		int puntaje = Integer.parseInt(rate);
			
		if (session == null || session.getAttribute("usuarioLogueado") == null) {
			response.sendRedirect("formlogin");
			return;
		}
		
		Cliente cliente = (Cliente) session.getAttribute("usuarioLogueado");
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
            EntityManager m = emf.createEntityManager();
            m.getTransaction().begin();
            
            Cliente cli = m.find(Cliente.class, cliente.getNick());
            cli.agregarPuntaje(puntaje, numRef);
            
            m.getTransaction().commit();
            m.close();
            emf.close();
		} catch (ProductoException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		// Redirigir a la página del producto
		response.sendRedirect("perfilProducto?producto=" + numRef);
	}
}