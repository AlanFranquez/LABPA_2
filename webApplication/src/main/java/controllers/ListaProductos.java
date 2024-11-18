package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webservices.Publicador;
import webservices.PublicadorService;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.market.svcentral.Factory;
import com.market.svcentral.ISistema;
import com.market.svcentral.Carrito;
import com.market.svcentral.Producto;


/**
 * Servlet implementation class ListaProductos
 */
@WebServlet("/ListaProductos")
public class ListaProductos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListaProductos() {
        super();
        // TODO Auto-generated constructor stub
    }
    

    @Override
    public void init() throws ServletException {
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		PublicadorService p = new PublicadorService();
		Publicador port = p.getPublicadorPort();		
		List<webservices.Producto> productos = null;
		
		
		try {
			productos = port.obtenerProductos();
			
		} catch (Exception e) {
			System.out.print(e);
			return;
		}
		System.out.print("Productos traidos de la base de datos correctamente");
		
        request.setAttribute("productos", productos);
        request.getRequestDispatcher("/WEB-INF/listaProductos.jsp").forward(request, response);
     
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("agregarAlCarrito".equals(action)) {
            agregarAlCarrito(request, response);
        }
    }

	private void agregarAlCarrito(HttpServletRequest request, HttpServletResponse response) throws IOException {


        HttpSession session = request.getSession();
     
        Carrito carrito = (Carrito) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new Carrito(); 
            session.setAttribute("carrito", carrito);
        }
        

        

        // Guardar el carrito actualizado en la sesión
        session.setAttribute("carrito", carrito);

        // Redirigir de nuevo a la lista de productos o al carrito
        response.sendRedirect("WEB-INF/listaProductos.jsp"); 
    }
}
