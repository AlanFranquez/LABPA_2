package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import webservices.Publicador;
import webservices.PublicadorService;

import java.io.IOException;

@WebServlet("/Saludo")
public class Saludo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PublicadorService p = new PublicadorService();
    Publicador port = p.getPublicadorPort();
    @Override
    public void init() throws ServletException {
    }
	
    public Saludo() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        port.saludarDesdeSvCentral("Mimi");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
}
