package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webservices.Cliente;
import webservices.Publicador;
import webservices.PublicadorService;

import java.io.IOException;
import java.util.Random;


@WebServlet("/enviarRespuesta")
public class enviarRespuesta extends HttpServlet {
    private static final long serialVersionUID = 1L;


    @Override
    public void init() throws ServletException {
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        String respuestaTexto = request.getParameter("respuesta");
        String comentarioIdStr = request.getParameter("comentarioId");
        
        PublicadorService p = new PublicadorService();
        Publicador port = p.getPublicadorPort();
        
        String parametro = request.getParameter("dtprod");

		if (parametro == null || parametro.isEmpty()) {
		    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El producto no está disponible.");
		    return;
		}
		
		int paramNum = Integer.parseInt(parametro);
		
        if (comentarioIdStr == null || comentarioIdStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El comentario no está disponible.");
            return;
        }

        int comentarioId = Integer.parseInt(comentarioIdStr);
        Cliente cli = (Cliente) session.getAttribute("usuarioLogueado");
        Random rand = new Random();
        int numeroRandom = rand.nextInt(20000);

        port.agregarRespuesta(comentarioId, respuestaTexto, numeroRandom, cli.getNick());
        port.notificarRespuestaComentario(paramNum, comentarioId, numeroRandom);
        
        // Redirigir a la página del producto
        response.sendRedirect("perfilProducto?producto=" + paramNum);
    }
}
