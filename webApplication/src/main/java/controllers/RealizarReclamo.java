package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;

import com.market.svcentral.*;
import com.market.svcentral.exceptions.*;

/**
 * Servlet implementation class RealizarReclamo
 */
@WebServlet("/RealizarReclamo")
public class RealizarReclamo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RealizarReclamo() {
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String numRef = request.getParameter("numRef");
		
		if(session == null || session.getAttribute("usuarioLogueado") == null) {
			response.sendRedirect("home");
			return;
		}
		
		int numeroRef = 0;
		try {
			numeroRef = Integer.parseInt(numRef);
		} catch(Exception e) {
			System.out.print(e);
			return;
		}
		
		Producto p1 = sist.getProducto(numeroRef);
		Proveedor proveedor = p1.getProveedor();
		Usuario usr = (Usuario) session.getAttribute("usuarioLogueado");
		if(usr == null || !usr.getTipo().equals("cliente")) {
			response.sendRedirect("home");
			return;
		}
		
		
		request.setAttribute("productoReclamo", p1);
		request.setAttribute("proveedorReclamo", proveedor);
		request.setAttribute("usuarioLogueado", usr);
		
		
		request.getRequestDispatcher("/WEB-INF/RealizarReclamo.jsp").forward(request, response);;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		if(session == null || session.getAttribute("usuarioLogueado") == null) {
			response.sendRedirect("home");
			return;
		}
		
		Cliente cl = (Cliente) session.getAttribute("usuarioLogueado");
		if(cl == null) {
			response.sendRedirect("home");
			return;
		}
		
		String numRef = request.getParameter("prodReclamo");
		int numeroReferencia = 0;
		try {
			numeroReferencia = Integer.parseInt(numRef);
		} catch(Exception e) {
			System.out.print(e);
			return;
		}
		
		
		Producto p1 = sist.getProducto(numeroReferencia);
		Proveedor prov = p1.getProveedor();
		
		String mensaje = request.getParameter("mensaje");
		if(mensaje == null) {
			response.sendRedirect("RealizarReclamo");
			return;
		}
		
		try {
			sist.agregarReclamo(mensaje, LocalDateTime.now(), p1, prov, cl);
		} catch (ReclamoException e) {
			System.out.print(e.getMessage());
			e.printStackTrace();
		}
		
		// Recordar cambiarlo"
		request.getRequestDispatcher("/WEB-INF/ReclamoExitoso.jsp").forward(request, response);;
		for(Reclamo r: p1.getReclamos()) {
			System.out.println(r.getTexto());
		}
		
		
	}

}