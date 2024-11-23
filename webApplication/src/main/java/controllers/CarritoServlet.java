package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webservices.Publicador;
import webservices.PublicadorService;
import webservices.Item;
import webservices.Cliente;
import webservices.OrdenDeCompra;

import java.io.IOException;
import java.util.List;

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
        
        PublicadorService service = new PublicadorService();
        Publicador port = service.getPublicadorPort();
        webservices.Usuario usuario = (webservices.Usuario) session.getAttribute("usuarioLogueado");
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("home");
            return;
        }
        
        
        
        webservices.Cliente cli = port.obtenerCliente(usuario.getNick());
        
        
     // Verificar si el cliente es válido
        if (cli != null) {
            	request.setAttribute("usuarioLogueado", usuario);
                request.setAttribute("usuario", cli);
                request.setAttribute("estado", "logueado");
                request.getRequestDispatcher("/WEB-INF/Carrito.jsp").forward(request, response);
                return;
        }
        // Obtener los productos del carrito del cliente utilizando el Web Service
        List<Item> itemsCarrito = port.getProductosCarrito(usuario.getNick());
        // Pasar los productos del carrito al JSP
        request.setAttribute("itemsCarrito", itemsCarrito);
        request.setAttribute("usuario", usuario); 
        request.setAttribute("estado", "logueado");

        // Redirigir al JSP del carrito
        request.getRequestDispatcher("/WEB-INF/Carrito.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String param = req.getParameter("numRef");
        String action = req.getParameter("action");
        String cant = req.getParameter("cantidad");
        
        // Verificar si la sesión es válida
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            resp.sendRedirect("home");
            return;
        }

        // Inicializar el cliente del Web Service
        PublicadorService publicadorService = new PublicadorService();
        Publicador port = publicadorService.getPublicadorPort();

        // Obtener el usuario logueado desde la sesión
        webservices.Usuario usuarioLogueado = (webservices.Usuario) session.getAttribute("usuarioLogueado");

        try {
            if ("eliminar".equals(action)) {
                int numRef = Integer.parseInt(param);
                port.eliminarProductoDelCarrito(numRef, usuarioLogueado.getNick());
                System.out.println("Producto con numRef " + numRef + " eliminado del carrito.");
            }

            if ("actualizarCant".equals(action)) {
                int cantNum = Integer.parseInt(cant);
                if (cantNum <= 0) {
                    throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir parámetros: " + e.getMessage());
            resp.sendRedirect("Carrito?error=parametrosInvalidos");
            return;
        } catch (Exception e) {
            System.err.println("Error al interactuar con el servicio web: " + e.getMessage());
            resp.sendRedirect("Carrito?error=servicioNoDisponible");
            return;
        }

        // Redirigir al carrito después de realizar la acción
        resp.sendRedirect("Carrito");
    }
}

