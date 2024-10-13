package com.controllers;

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
import java.util.Map.Entry;

import com.model.Carrito;
import com.model.Cliente;
import com.model.Factory;
import com.model.ISistema;
import com.model.Item;
import com.model.OrdenDeCompra;
import com.model.Usuario;

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
        } catch (Exception e) {
            throw new ServletException("No se pudo inicializar ISistema", e);
        }
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		
		if(session == null || session.getAttribute("usuarioLogueado") == null) {
			response.sendRedirect("home");
			return;
		}
		
		if(session.getAttribute("mensajeExito") != null && session.getAttribute("mensajeExito") != "Su compra se ha realizado con éxito.") {
			response.sendRedirect("home");
			return;
		}
		
		session.setAttribute("mensajeExito", "Su compra se ha realizado con éxito.");
		
		Usuario user = (Usuario) session.getAttribute("usuarioLogueado");
		
		if(!user.getTipo().equals("cliente")) {
			response.sendRedirect("home");
			return;
		}
		
		Cliente cl = (Cliente) user;
		if(cl.getCarrito() == null || cl.getCarrito().getProductos().isEmpty()) {
			
			response.sendRedirect("home");
			return;
		}
		
		request.setAttribute("usuarioLogueado", user);
		request.setAttribute("carrito", cl.getCarrito());
		request.getRequestDispatcher("/WEB-INF/realizarCompra.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
        HttpSession session = request.getSession();
        
        if(session == null || session.getAttribute("usuarioLogueado") == null) {
			response.sendRedirect("home");
			return;
		}
		
		Usuario user = (Usuario) session.getAttribute("usuarioLogueado");
		
		if(!user.getTipo().equals("cliente")) {
			response.sendRedirect("home");
			return;
		}
		
		Cliente cl = (Cliente) user;
		if(cl.getCarrito() == null || cl.getCarrito().getProductos().isEmpty()) {
			
			response.sendRedirect("home");
			return;
		}
		
		Carrito c = cl.getCarrito();
		List<Item> items = c.getProductos();
		float precioTotal = 0;
		
		for(Item i : items) {
			precioTotal += i.getSubTotal();
		}
        
        Map<Integer, Item> itemsMap = new HashMap<Integer, Item>();
        
        for (Item i : items) {
            itemsMap.put(i.getProducto().getNumRef(), i);
            
        }
        
        OrdenDeCompra o = new OrdenDeCompra(itemsMap, precioTotal);
        sist.realizarCompra(o, cl.getNick());
        cl.agregarCompra(o);
        
        c.vaciarCarrito();
        
        Map<Integer, OrdenDeCompra> ordenes = cl.getCompras();
        
        for(Map.Entry<Integer, OrdenDeCompra> entry : ordenes.entrySet()) {
        	
        	OrdenDeCompra orden = entry.getValue();
        	
        	System.out.print(orden.getNumero());
        }
        
        session.setAttribute("mensajeExito", "Su compra se ha realizado con éxito.");
        session.setAttribute("precioTotal", String.valueOf(precioTotal));
        
        request.getRequestDispatcher("/WEB-INF/paginaExito.jsp").forward(request, response);

	}

}
