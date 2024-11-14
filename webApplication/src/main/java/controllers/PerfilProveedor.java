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

import com.market.svcentral.DTProveedor;
import com.market.svcentral.Producto;
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
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        Proveedor u = (Proveedor) session.getAttribute("usuarioLogueado");
        Proveedor usuarioLogueado = em.find(Proveedor.class, u.getNick());
        String parametro = request.getParameter("nickname");
        
        if (usuarioLogueado.getNick().equals(parametro)) {
        	List<Producto> productos = null;
            String jpql = "SELECT p FROM Producto p WHERE p.proveedor.nick = :proveedorNick";  // Usar el parámetro correctamente
            productos = em.createQuery(jpql, Producto.class)
                .setParameter("proveedorNick", parametro)  // Usar el valor del parámetro correctamente
                .getResultList();

            request.setAttribute("usuario", usuarioLogueado);
            request.setAttribute("productos", productos);
            System.out.println("Tipo de usuario almacenado en sesión: " + usuarioLogueado.getClass().getName());
            request.getRequestDispatcher("/WEB-INF/InfoPerfilProveedor.jsp").forward(request, response);
            return;
        }
        
       
        response.sendRedirect("home");
        
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
