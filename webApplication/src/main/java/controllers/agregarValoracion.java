package controllers;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webservices.Cliente;
import webservices.Publicador;
import webservices.PublicadorService;
@WebServlet("/agregarValoracion")
public class agregarValoracion extends HttpServlet{
	private static final long serialVersionUID = 1L;
	       
    @Override
	public void init() throws ServletException {
    	
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String rate = request.getParameter("rate");
		String parametro = request.getParameter("dtprod");
		System.out.println("puntaje: " + rate);
		
		PublicadorService p = new PublicadorService();
		
		Publicador port = p.getPublicadorPort();
		
		if (parametro == null || parametro.isEmpty()) {
		    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El producto no está disponible.");
		    return;
		}
		if (rate == null || rate.isEmpty()) {
			System.out.println("No seleccionaste un valor");
			return;
		}
		int numRef = Integer.parseInt(parametro);
		int puntaje = Integer.parseInt(rate);
			
		if (session == null || session.getAttribute("usuarioLogueado") == null) {
			response.sendRedirect("formlogin");
			return;
		}
		
		Cliente cliente = (Cliente) session.getAttribute("usuarioLogueado");
		Cliente cli = port.obtenerCliente(cliente.getNick());
		port.agregarValoracion(puntaje, numRef, cli.getNick());
		// Redirigir a la página del producto
		response.sendRedirect("perfilProducto?producto=" + numRef);
	}
}