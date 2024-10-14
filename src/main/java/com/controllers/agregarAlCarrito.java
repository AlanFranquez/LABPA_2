package com.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.model.Carrito;
import com.model.Cliente;
import com.model.Factory;
import com.model.ISistema;
import com.model.Item;
import com.model.Producto;

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
        } catch (Exception e) {
            throw new ServletException("No se pudo inicializar ISistema", e);  
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
            
            Producto producto = sist.getProducto(Integer.parseInt(numRef));
            
            if (producto != null && cantidad > 0 && cantidad <= producto.getStock()) {
                Item it = new Item(cantidad, producto);
                carrito.agregarProducto(it);
                
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
