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

import java.io.IOException;
import java.util.Random;



@WebServlet("/enviarComentario")
public class enviarComentario extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
        
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		Random numRandom = new Random();
        int comentarioId = numRandom.nextInt(10000);
		String mensaje = request.getParameter("comentario");
		String parametro = request.getParameter("dtprod");
		
		PublicadorService p = new PublicadorService();
		Publicador port = p.getPublicadorPort();
		

		if (parametro == null || parametro.isEmpty()) {
		    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El producto no está disponible.");
		    return;
		}

		int paramNum = Integer.parseInt(parametro);
		
		if (session == null || session.getAttribute("usuarioLogueado") == null) {
			response.sendRedirect("formlogin");
			return;
		}
		
		
		Cliente cliente = (Cliente) session.getAttribute("usuarioLogueado");
		Producto producto1 = port.obtenerProducto(paramNum);

		// Crear el comentario
		
		port.agregarComentario(comentarioId, mensaje, cliente.getNick(), paramNum);	
		
		// Notificar a los interesados
        port.notificarComentario(paramNum, comentarioId);
		
		request.setAttribute("coms", port.listarComentarios(producto1.getNumRef()));
		response.sendRedirect("perfilProducto?producto=" + paramNum);
		
	}
}