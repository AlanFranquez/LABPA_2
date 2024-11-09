package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.market.svcentral.Cliente;
import com.market.svcentral.Factory;
import com.market.svcentral.ISistema;
import com.market.svcentral.Producto;
import com.market.svcentral.Usuario;
@WebServlet("/buscarproductos")
public class BuscarProducto extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ISistema sist;

    @Override
    public void init() throws ServletException {
        try {
            sist = Factory.getSistema();
        } catch (Exception e) {
            throw new ServletException("No se pudo inicializar ISistema", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response); 
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String searchQuery = request.getParameter("query");
        String ordenacion = request.getParameter("ordenacion");

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
        
        // Verificar si la sesión es válida
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("home");
            return; 
        }

        Usuario user = (Usuario) session.getAttribute("usuarioLogueado");
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
		EntityManager em = emf.createEntityManager();
		List<Producto> productos = null;
		em.getTransaction().begin();
		
		

        if (searchQuery == null || searchQuery.trim().isEmpty()) {
        	try {
        		productos = em.createQuery("SELECT p FROM Producto p", Producto.class)
        			    .getResultList();
        		System.out.print("Busqueda en la base de datos :)");
    			
    		} catch (Exception e) {
    			System.out.print(e);
    			return;
    		}
        } else {
        	try {
        		String jpql = "SELECT p FROM Producto p WHERE p.nombre LIKE :searchQuery";
        		productos = em.createQuery(jpql, Producto.class)
        		    .setParameter("searchQuery", "%" + searchQuery + "%")
        		    .getResultList();
        		
        		System.out.print("Busqueda en la base de datos :)");
			} catch (Exception e) {
				System.out.print(e);
    			return;
			}
        	
            productos = sist.buscarProductos(searchQuery);
        }

        if ("alfabeticamente".equals(ordenacion)) {
            productos.sort(Comparator.comparing(Producto::getNombre));
        } else if ("precio".equals(ordenacion)) {
            productos.sort(Comparator.comparing(Producto::getPrecio).reversed());
        } else if ("cantidad".equals(ordenacion)) {
            productos.sort(Comparator.comparing(Producto::getCantidadComprada).reversed());
        }

        if (user instanceof Cliente) {
            Cliente cliente = (Cliente) user;
            request.setAttribute("carrito", cliente.getCarrito());
        }

        request.setAttribute("usuarioLogueado", user);
        request.setAttribute("productos", productos);
        request.getRequestDispatcher("/WEB-INF/listaProductos.jsp").forward(request, response);
        
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}
