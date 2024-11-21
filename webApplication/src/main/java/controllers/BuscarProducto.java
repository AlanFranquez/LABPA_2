package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webservices.Cliente;
import webservices.Producto;
import webservices.Publicador;
import webservices.PublicadorService;
import webservices.Usuario;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@WebServlet("/buscarproductos")
public class BuscarProducto extends HttpServlet {
    private static final long serialVersionUID = 1L;

    PublicadorService p = new PublicadorService();
    Publicador port = p.getPublicadorPort();
    
    @Override
    public void init() throws ServletException {

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
        
		List<Producto> productos = port.obtenerProductos();
		
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
        	productos = port.buscarProductos(searchQuery);
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
    }
}
