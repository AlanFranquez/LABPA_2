package com.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.util.Map;

import com.model.Factory;
import com.model.ISistema;
import com.model.Usuario;
import com.model.Carrito;
import com.model.Producto;
import com.model.Proveedor;
import com.model.DtProducto;
import com.model.DTItem;
import com.model.Cat_Producto;
import com.model.Categoria;

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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Obtener la lista de productos de la sesión o inicializarla
        List<Producto> listaProductos = (List<Producto>) request.getSession().getAttribute("listaProductos");

        // Enviar la lista al JSP
        request.setAttribute("productos", listaProductos);
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
        // Obtener el ID del producto y la cantidad
        String numRef = request.getParameter("numRef");
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        int IntnumRef = Integer.parseInt(request.getParameter("numRef")); // Convertir el parámetro a número
        Producto producto = obtenerProductoPorNumRef(IntnumRef);

        // Obtener la sesión actual
        HttpSession session = request.getSession();
     
        // Obtener el carrito de la sesión o crear uno nuevo si no existe
        Carrito carrito = (Carrito) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new Carrito(); 
            session.setAttribute("carrito", carrito);
        }
        

        // Agregar el producto al carrito la cantidad especificada
        if (producto != null && cantidad > 0 && cantidad <= producto.getStock()) {
            for (int i = 0; i < cantidad; i++) {
                carrito.agregarProducto(producto, cantidad);
            }
        }

        // Guardar el carrito actualizado en la sesión
        session.setAttribute("carrito", carrito);

        // Redirigir de nuevo a la lista de productos o al carrito
        response.sendRedirect("WEB-INF/listaProductos.jsp"); 
    }
	
	
	// Método para obtener el producto por su número de referencia
    private Producto obtenerProductoPorNumRef(int IntnumRef) {
    	
    	Iterator<Map.Entry<String, Categoria>> iterator;

    	while (iterator.hasNext()) {
    	    Map.Entry<String, Categoria> entry = iterator.next();
    	    Categoria categoria = entry.getValue();

    	    // Verificar si la categoría es una instancia de Cat_Producto
    	    if (categoria instanceof Cat_Producto) {
    	        Cat_Producto prodC = (Cat_Producto) categoria;

    	        Iterator<Map.Entry<Integer, Producto>> prodIterator = prodC.getProductos().entrySet().iterator();

    	        while (prodIterator.hasNext()) {
    	            Map.Entry<Integer, Producto> prodEntry = prodIterator.next();
    	            Producto p = prodEntry.getValue();

    	            // el número de referencia
    	            if (p.getNumRef() == IntnumRef) {
    	                return p;
    	            }
    	        }
    	    }
    	}
    	return null;
    }
}
