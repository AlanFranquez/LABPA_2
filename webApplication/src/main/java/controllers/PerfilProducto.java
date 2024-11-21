package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webservices.Comentario;
import webservices.DtProducto;
import webservices.Publicador;
import webservices.PublicadorService;
import webservices.Usuario;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class PerfilProducto
 */
@WebServlet("/perfilProducto")
public class PerfilProducto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PublicadorService p = new PublicadorService();
    Publicador port = p.getPublicadorPort();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PerfilProducto() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    

    @Override
    public void init() throws ServletException {
    }

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Cambiado a false para no crear una nueva sesión

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
        
        if (session == null) {
            response.sendRedirect("formlogin");
            return;
        }

        Object usuarioLogueado = session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            response.sendRedirect("formlogin");
            return;
        }
        Usuario user = (Usuario) usuarioLogueado;
        request.setAttribute("usuario", user);

        try {
            String parametro = request.getParameter("producto");
            if (parametro == null) {
                response.sendRedirect("perfilCliente");
                return;
            }

            int paramNumero = Integer.parseInt(parametro);

            DtProducto dtprod = port.obtenerDTProducto(paramNumero);
            
            List<Comentario> comentarios = port.comentariosProducto(paramNumero);
            
            if (dtprod == null) {
                response.sendRedirect("perfilCliente");
                return;
            }

            request.setAttribute("dtprod", dtprod);
            request.setAttribute("comentarios", comentarios);
            request.getRequestDispatcher("/WEB-INF/PerfilProducto.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            // Si el parámetro no es un número válido, redirigir al perfil del cliente
            response.sendRedirect("perfilCliente");
        } catch (Exception e) {
            // Manejar otras excepciones si es necesario
            response.sendRedirect("errorPage"); // O una página de error adecuada
        } 

    }


	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
