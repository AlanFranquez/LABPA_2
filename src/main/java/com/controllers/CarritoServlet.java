package com.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import com.model.Usuario;
import com.model.Item;
import com.model.Cliente;

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

        Usuario user = (Usuario) session.getAttribute("usuarioLogueado");

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
        
        System.out.println("Cantidad recibida: " + req.getParameter("cantidad"));

        
        int paramNumero = 0;
        if("eliminar".equals(action)) {
              try {
              	paramNumero = Integer.parseInt(param);
              }catch (Exception e) {
              	System.out.print("Hubo un error");
              	resp.sendRedirect("home");
              	return;
      		}

              Usuario user = (Usuario) session.getAttribute("usuarioLogueado");

              if (user instanceof Cliente) {
                  Cliente cliente = (Cliente) user;

                  cliente.getCarrito().eliminarProd(paramNumero);
              }
        }
        
        if("actualizarCant".equals(action)) {
        	Usuario user = (Usuario) session.getAttribute("usuarioLogueado");
        	
        	Cliente cl = (Cliente) user;
        	int cantNum = 0;
        	
        	try {
        		cantNum = Integer.parseInt(cant);
              	paramNumero = Integer.parseInt(param);
              }catch (Exception e) {
              	System.out.print("Hubo un error");
              	resp.sendRedirect("home");
              	return;
      		}
        	
        	
        	
        	Item it = cl.getCarrito().getItem(paramNumero);
        	
        	if(it != null) {
        		it.setCant(cantNum);
        		System.out.print(it.getCant());
        	}
        }
        
      
        
        resp.sendRedirect("Carrito");
    }
}


