package controllers;

import java.util.List;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		String userAgent = request.getHeader("User-Agent");

		if ((userAgent != null) && (userAgent.contains("Mobile") || userAgent.contains("Android")
				|| userAgent.contains("iPhone") || userAgent.contains("iPad") || userAgent.contains("Windows Phone")
				|| userAgent.contains("BlackBerry"))) {
			request.getRequestDispatcher("/WEB-INF/construccion.jsp").forward(request, response);
			return;
		}
		if (session == null || session.getAttribute("usuarioLogueado") == null) {
			response.sendRedirect("home");
			return;
		}
		List<webservices.ReclamoDTO> reclamos = new ArrayList<>();
		Object usuario = session.getAttribute("usuarioLogueado");
		if (usuario instanceof Proveedor) {
			Proveedor proveedor = (Proveedor) usuario;
			
			reclamos = port.obtenerReclamos(proveedor.getNick());
			
			request.setAttribute("proveedor", proveedor);
			request.setAttribute("reclamos", reclamos);
		} else {
			response.sendRedirect("home");
		}

		

		request.getRequestDispatcher("/WEB-INF/VerReclamo.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
