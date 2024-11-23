package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import webservices.Publicador;
import webservices.PublicadorService;
import webservices.Carrito;
import webservices.Cliente;
import webservices.Item;
import webservices.Producto;

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
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	req.getRequestDispatcher("/WEB-INF/listaProductos.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        PublicadorService service = new PublicadorService();
        Publicador port = service.getPublicadorPort();
        
        webservices.Usuario usuario = (webservices.Usuario) session.getAttribute("usuarioLogueado");
        webservices.Cliente cl = port.obtenerCliente(usuario.getNick());
        
        if (cl != null && port.getTipo(cl.getNick()).equals("cliente")) {
     
            Carrito carrito = port.obtenerCarritoCliente(cl.getNick());
            
            String numRef = request.getParameter("numRef");
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            
            int numero = Integer.parseInt(numRef);
           
            Producto producto = port.obtenerProducto(numero);
            
            System.out.println("numRef: " + numRef);
            System.out.println("cantidad: " + cantidad);
            
            if (producto != null && cantidad > 0 && cantidad <= producto.getStock()) { 
            	if(port.existeProdCarrito(carrito, numero)) {
            		Item itemExistente = port.obtenerItemCarrito(cl.getNick(), numero);
            		
            		itemExistente.setCant(itemExistente.getCant() + cantidad);
            	} else {
            		Item item = port.prodsAItem(cantidad, numero);
            		port.agregarProductoAlCarrito(item, cl.getNick());
            		System.out.println("Producto agregado-Servlet.");
            		System.out.println("carrito:" + port.obtenerItemCarrito(numRef, numero));
            	}
            	
                
            	session.setAttribute("usuarioLogueado", cl);
                
               response.sendRedirect("Carrito");
            } else {
               
                response.sendRedirect("home");
            }
        } else {
            response.sendRedirect("formlogin");
        }
    }

}
