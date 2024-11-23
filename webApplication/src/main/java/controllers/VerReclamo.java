package controllers;

import java.util.List;

import com.market.svcentral.Reclamo;

import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webservices.Producto;
import webservices.Proveedor;
import webservices.Publicador;
import webservices.PublicadorService;

import java.io.IOException;

/**
 * Servlet implementation class VerReclamo
 */
@WebServlet("/VerReclamos")
public class VerReclamo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	PublicadorService p = new PublicadorService();
    Publicador port = p.getPublicadorPort();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerReclamo() {
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
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("home");
            return;
        }
        Object usuario = session.getAttribute("usuarioLogueado");
        if (usuario instanceof Proveedor) {
            Proveedor proveedor = (Proveedor) usuario;
            List<Reclamo> reclamosTotales = new ArrayList<>();
            for (Producto producto : port.obtenerProductos()) {
                List<Reclamo> reclamos = port.obtenerReclamosProd(producto.getNumRef());
                if (reclamos != null) {
                    reclamosTotales.addAll(reclamos); 
                }
            
            if (reclamos != null) {
                request.setAttribute("reclamos", reclamos);
            } else {
                request.setAttribute("reclamos", new ArrayList<Reclamo>());
            }
            
            request.setAttribute("proveedor", proveedor);
            request.getRequestDispatcher("/WEB-INF/VerReclamo.jsp").forward(request, response);
            }
            } else {
            response.sendRedirect("home");
        }
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
