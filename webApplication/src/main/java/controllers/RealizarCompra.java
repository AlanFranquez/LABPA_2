package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.market.svcentral.Carrito;
import com.market.svcentral.Cliente;
import com.market.svcentral.DTEstado;
import com.market.svcentral.Factory;
import com.market.svcentral.ISistema;
import com.market.svcentral.Item;
import com.market.svcentral.OrdenDeCompra;
import com.market.svcentral.Proveedor;
import com.market.svcentral.Usuario;

/**
 * Servlet implementation class RealizarCompra
 */
@WebServlet("/realizarCompra")
public class RealizarCompra extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RealizarCompra() {
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
			response.sendRedirect("home");
			return;
		}
		
		if (session.getAttribute("mensajeExito") != null && session.getAttribute("mensajeExito") != "Su compra se ha realizado con éxito.") {
			response.sendRedirect("home");
			return;
		}
		
		session.setAttribute("mensajeExito", "Su compra se ha realizado con éxito.");
		
		Usuario user = (Usuario) session.getAttribute("usuarioLogueado");
		
		if (!user.getTipo().equals("cliente")) {
			response.sendRedirect("home");
			return;
		}
		
		Cliente cliente = (Cliente) user;
		if (cliente.getCarrito() == null || cliente.getCarrito().getProductos().isEmpty()) {
			
			response.sendRedirect("home");
			return;
		}
		
		request.setAttribute("usuarioLogueado", user);
		request.setAttribute("carrito", cliente.getCarrito());
		request.getRequestDispatcher("/WEB-INF/realizarCompra.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession();
	    
	    if (session == null || session.getAttribute("usuarioLogueado") == null) {
	        response.sendRedirect("home");
	        return;
	    }
	    
	    Usuario user = (Usuario) session.getAttribute("usuarioLogueado");
	    
	    if (!user.getTipo().equals("cliente")) {
	        response.sendRedirect("home");
	        return;
	    }
	    
	    Cliente cliente = (Cliente) user;
	    if (cliente.getCarrito() == null || cliente.getCarrito().getProductos().isEmpty()) {
	        response.sendRedirect("home");
	        return;
	    }
	    
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
	    
	    EntityManager em = emf.createEntityManager();
	    
	    em.getTransaction().begin();
	    
	    Carrito carrito = cliente.getCarrito();
	    List<Item> items = carrito.getProductos();
	    
	    // Mapa para almacenar las órdenes de compra por proveedor
	    Map<Proveedor, Map<Integer, Item>> ordenesPorProveedor = new HashMap<>();
	    
	    // Organizar los productos por proveedor
	    for (Item item : items) {
	        Proveedor proveedor = item.getProveedor();
	        ordenesPorProveedor.putIfAbsent(proveedor, new HashMap<>());
	        ordenesPorProveedor.get(proveedor).put(item.getProducto().getNumRef(), item);
	    }
	    
	    // Variable para acumular el precio total de todas las órdenes de compra
	    float precioTotalGeneral = 0;
	    
	    // Procesar cada proveedor y crear una OrdenDeCompra
	    for (Map.Entry<Proveedor, Map<Integer, Item>> entry : ordenesPorProveedor.entrySet()) {
	        Proveedor proveedor = entry.getKey();
	        Map<Integer, Item> itemsPorProveedor = entry.getValue();
	        
	        // Calcular el precio total para este proveedor
	        float precioTotalPorProveedor = 0;
	        for (Item item : itemsPorProveedor.values()) {
	            precioTotalPorProveedor += item.getSubTotal();
	        }
	        
	        // Sumar el precio total del proveedor al total general
	        precioTotalGeneral += precioTotalPorProveedor;
	        
	        // Crear la OrdenDeCompra
	        OrdenDeCompra ordenCompra = new OrdenDeCompra(itemsPorProveedor, precioTotalPorProveedor, proveedor);
	     // Dentro de algún método donde tengas acceso a EntityManager (em)
	        TypedQuery<DTEstado> query = em.createQuery(
	            "SELECT e FROM DTEstado e WHERE e.estado = :estado", DTEstado.class);
	        query.setParameter("estado", "Comprada");

	        DTEstado estadoComprada = query.getSingleResult(); // Aquí se obtiene el estado "Comprada"

	        // Asignarlo al objeto OrdenCompra
	        ordenCompra.setEstado(estadoComprada);

	        sist.realizarCompra(ordenCompra, cliente.getNick());
	        cliente.agregarCompra(ordenCompra);
	    }
	    
	    // Vaciar el carrito después de la compra
	    carrito.vaciarCarrito();
	    
	    // Guardar el precio total general en la sesión
	    session.setAttribute("mensajeExito", "Su compra se ha realizado con éxito.");
	    session.setAttribute("precioTotal", String.valueOf(precioTotalGeneral)); // Total general de todas las órdenes
	    
	    request.getRequestDispatcher("/WEB-INF/paginaExito.jsp").forward(request, response);
	}

}
