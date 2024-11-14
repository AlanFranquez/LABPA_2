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

import com.market.svcentral.Usuario;
import com.market.svcentral.Item;
import com.market.svcentral.Cliente;

/**
 * Servlet implementation class Carrito
 */
@WebServlet("/Carrito")
public class CarritoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CarritoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Verificar si la sesión es válida
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("home");
            return;
        }


    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
    	EntityManager em = emf.createEntityManager();
    	em.getTransaction().begin();
        
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");

        Usuario user = em.find(Usuario.class, u.getNick());
        
        // Verificar si el usuario es un cliente
        if (user instanceof Cliente) {
            Cliente cliente = (Cliente) user;

            List<Item> itemsCarrito = cliente.getCarrito().getProductos();

            request.setAttribute("itemsCarrito", itemsCarrito);
        }
        if (user != null) {
            request.setAttribute("usuario", user); 
            request.setAttribute("estado", "logueado");
            request.getRequestDispatcher("/WEB-INF/Carrito.jsp").forward(request, response);
        } else {
            response.sendRedirect("formlogin");
            request.setAttribute("estado", "noLogueado");
        }
        
        em.getTransaction().commit();
        em.close();
        emf.close();
        
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	HttpSession session = req.getSession(false);
    	String param = req.getParameter("numRef");
    	
    	String action = req.getParameter("action");
    	String cant = req.getParameter("cantidad");
    	
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
    	EntityManager em = emf.createEntityManager();
    	

        // Verificar si la sesión es válida
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            resp.sendRedirect("home");
            return;
        }
        
        System.out.println("Cantidad recibida: " + req.getParameter("cantidad"));
        em.getTransaction().begin();
        
        int paramNumero = 0;
        if ("eliminar".equals(action)) {
              try {
              	paramNumero = Integer.parseInt(param);
              } catch (Exception e) {
              	 System.out.print("Hubo un error");
              	 resp.sendRedirect("home");
              	 return;
      		}
              Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
              Usuario user = em.find(Usuario.class, u.getNick());
              
              
              if (user instanceof Cliente) {
                  Cliente cliente = (Cliente) user;

                  cliente.getCarrito().eliminarProd(paramNumero);
                  System.out.println("Producto con numRef " + paramNumero + " eliminado del carrito.");
                  session.setAttribute("carrito", cliente.getCarrito());
              }
              

        }
        
        if ("actualizarCant".equals(action)) {
        	 Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
             Usuario user = em.find(Usuario.class, u.getNick());
        	
        	Cliente cliente = (Cliente) user;
        	int cantNum = 0;
        	
        	try {
        		cantNum = Integer.parseInt(cant);
              	paramNumero = Integer.parseInt(param);
              } catch (Exception e) {
              	 System.out.print("Hubo un error");
              	 resp.sendRedirect("home");
              	 return;
      		}
        	
        	
        	
        	Item item = cliente.getCarrito().getItem(paramNumero);
        	
        	if (item != null) {
        		item.setCant(cantNum);
        		System.out.print(item.getCant());
        	}
        }
        
        em.getTransaction().commit();
        em.close();
        emf.close();
        
        resp.sendRedirect("Carrito");
    }
}


