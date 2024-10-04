package controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet ("/home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Home() {
        super();
    }

    /**
	 * inicializa la sesión si no estaba creada 
	 */
	public static void initSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("estado_sesion") == null) {
			session.setAttribute("estado_sesion", "no login");
		}
	}
	
	/**
	 * Devuelve el estado de la sesión
	 * @param request
	 * @return 
	 */
	public static String getEstado(HttpServletRequest request)
	{
		return (String) request.getSession().getAttribute("estado_sesion");
	}

	private void processRequest(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		initSession(req);
		
		switch(getEstado(req)){
			case "no login":
				req.getRequestDispatcher("/WEB-INF/index.html").
						forward(req, resp);
				break;
			case "incorrecto":
				req.getRequestDispatcher("/WEB-INF/iniciarSesion.html").
						forward(req, resp);
				break;
			case "login":
				//resp.sendRedirect("/gamebook/perfil");
				break;
		}
	}
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
