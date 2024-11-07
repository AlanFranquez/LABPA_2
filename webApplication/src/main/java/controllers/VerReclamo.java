package controllers;
import com.market.svcentral.exceptions.ReclamoException;
import com.market.svcentral.Cliente;
import com.market.svcentral.Factory;
import com.market.svcentral.ISistema;
import com.market.svcentral.Producto;
import com.market.svcentral.Proveedor;
import com.market.svcentral.Reclamo;
import com.market.svcentral.Usuario;

import java.util.List;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet implementation class VerReclamo
 */
@WebServlet("/VerReclamos")
public class VerReclamo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerReclamo() {
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
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("home");
            return;
        }
        Object usuario = session.getAttribute("usuarioLogueado");
        if (usuario instanceof Proveedor) {
            Proveedor proveedor = (Proveedor) usuario;
            List<Reclamo> reclamosTotales = new ArrayList<>();
            for (Producto producto : proveedor.getProductos()) {
                List<Reclamo> reclamos = producto.getReclamos();
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
