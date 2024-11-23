package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webservices.ObtenerProducto;
import webservices.Publicador;
import webservices.PublicadorService;
import webservices.ReclamoException;
import webservices.ReclamoException_Exception;

import java.io.IOException;
import java.time.LocalDateTime;



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
    
    @Override
    public void init() throws ServletException {
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String userAgent = request.getHeader("User-Agent");
        PublicadorService p = new PublicadorService();
        Publicador port = p.getPublicadorPort();
	     
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
		
		webservices.Producto p1 = port.obtenerProducto(numeroRef);
		webservices.Proveedor proveedor = port.obtenerProvDeProducto(numeroRef);
		webservices.Usuario usr = (webservices.Usuario) session.getAttribute("usuarioLogueado");
		if(usr == null || usr instanceof webservices.Proveedor) {
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
		
		PublicadorService p = new PublicadorService();
        Publicador port = p.getPublicadorPort();
		
		if(session == null || session.getAttribute("usuarioLogueado") == null) {
			response.sendRedirect("home");
			return;
		}
		
		webservices.Cliente cl = (webservices.Cliente) session.getAttribute("usuarioLogueado");
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
		
		webservices.Producto prod = port.obtenerProducto(numeroReferencia);
		String mensaje = request.getParameter("mensaje");
		if(mensaje == null) {
			response.sendRedirect("RealizarReclamo");
			return;
		}
		
		try {
			port.realizarReclamo(numeroReferencia, mensaje, cl.getNick());
		} catch (ReclamoException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Recordar cambiarlo"
		request.getRequestDispatcher("/WEB-INF/ReclamoExitoso.jsp").forward(request, response);;
		
		
		
	}

}