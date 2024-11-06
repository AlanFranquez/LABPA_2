package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.market.svcentral.Carrito;
import com.market.svcentral.Cliente;
import com.market.svcentral.Factory;
import com.market.svcentral.ISistema;
import com.market.svcentral.Item;
import com.market.svcentral.Producto;

/**
 * Servlet implementation class agregarAlCarrito
 */
@WebServlet("/agregarAlCarrito")
public class agregarAlCarrito extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public agregarAlCarrito() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private ISistema sist;

    @Override
    public void init() throws ServletException {
        try {
            sist = Factory.getSistema();
        } catch (Exception exeption) {
            throw new ServletException("No se pudo inicializar ISistema", exeption);  
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	req.getRequestDispatcher("/WEB-INF/listaProductos.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        Cliente cliente = (Cliente) session.getAttribute("usuarioLogueado");
        
        if (cliente != null && cliente.getTipo().equals("cliente")) {
     
            Carrito carrito = cliente.getCarrito();
            
            String numRef = request.getParameter("numRef");
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            
            int numero = Integer.parseInt(numRef);
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
            EntityManager m = emf.createEntityManager();
            m.getTransaction().begin();
            Producto producto = m.find(Producto.class, numero);
            
            
            m.getTransaction().commit();
            m.close();
            emf.close();
            
            if (producto != null && cantidad > 0 && cantidad <= producto.getStock()) {
                
            	if(carrito.existeProducto(numero)) {
            		Item itemExistente = carrito.getItem(numero);
            		
            		itemExistente.setCant(itemExistente.getCant() + cantidad);
            	} else {
            		
            		Item item = new Item(cantidad, producto);
            		carrito.agregarProducto(item);
            	}
            	
                
            	session.setAttribute("usuarioLogueado", cliente);
                
               response.sendRedirect("Carrito");
            } else {
               
                response.sendRedirect("home");
            }
        } else {
            response.sendRedirect("formlogin");
        }
    }

}
