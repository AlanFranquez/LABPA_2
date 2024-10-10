package com.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;

import com.model.Cliente;
import com.model.Comentario;
import com.model.Factory;
import com.model.ISistema;
import com.model.Producto;

@WebServlet("/enviarRespuesta")
public class enviarRespuesta extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ISistema sist;

    @Override
    public void init() throws ServletException {
        try {
            sist = Factory.getSistema();
        } catch (Exception e) {
            throw new ServletException("No se pudo inicializar ISistema", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        String respuestaTexto = request.getParameter("respuesta");
        String comentarioIdStr = request.getParameter("comentarioId");
        
        String parametro = request.getParameter("dtprod");

		if (parametro == null || parametro.isEmpty()) {
		    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El producto no está disponible.");
		    return;
		}
		
int paramNum = Integer.parseInt(parametro);
		
		if (session == null || session.getAttribute("usuarioLogueado") == null) {
			response.sendRedirect("formlogin");
			return;
		}
		

        if (comentarioIdStr == null || comentarioIdStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El comentario no está disponible.");
            return;
        }

        int comentarioId = Integer.parseInt(comentarioIdStr);
       
        
        Cliente cli = (Cliente) session.getAttribute("usuarioLogueado");
        Producto p1 = sist.getProducto(paramNum);
        Comentario coment = p1.getComentario(comentarioId);
        
        if (coment == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Comentario no encontrado.");
            return;
        }

        Comentario respuesta = new Comentario(comentarioId, respuestaTexto, cli, LocalDateTime.now());
        coment.agregarRespuesta(respuesta);

        // Redirigir a la página del producto
        response.sendRedirect("perfilProducto?producto=" + paramNum);
    }
}
