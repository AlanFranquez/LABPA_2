package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;
import java.util.List;

import webservices.*;


@WebServlet("/perfilProductoMOBILE")
public class perfilProductoMOBILE extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public perfilProductoMOBILE() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Detectar si el acceso proviene de un dispositivo móvil
        String userAgent = request.getHeader("User-Agent");
        boolean isMobile = isMobileDevice(userAgent);

        if (!isMobile) {
            response.sendRedirect("home");
            return;
        }

        PublicadorService p = new PublicadorService();
        Publicador port = p.getPublicadorPort();


        if (session == null) {
            response.sendRedirect("formlogin");
            return;
        }

        webservices.Usuario usuarioLogueado = (webservices.Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            response.sendRedirect("formlogin");
            return;
        }
        webservices.Usuario user = (webservices.Usuario) usuarioLogueado;
        request.setAttribute("usuario", user);
        request.setAttribute("nickusuario", user.getNick());

        String parametro = request.getParameter("producto");
        if (parametro == null) {
            response.sendRedirect("perfilCliente");
            return;
        }

        System.out.print("VALOR A CONVERTIR -> " + parametro);
        int paramNumero = Integer.parseInt(parametro);

        // Obtener el producto del servicio
        webservices.Producto producto =  port.obtenerProducto(paramNumero);
        

        List<webservices.Comentario> coms = port.listarComentarios(paramNumero);
        for (webservices.Comentario c : coms) {
            System.out.println("COMENTARIOS");
            System.out.println(c.getTexto());
        }

        // Agregar atributos al request
        request.setAttribute("coms", coms);
        request.setAttribute("prod", producto);

        // Redirigir al JSP correspondiente
        request.getRequestDispatcher("/WEB-INF/PerfilProductoMOBILE.jsp").forward(request, response);
    }

    
 // Método auxiliar para detectar si el dispositivo es móvil
    private boolean isMobileDevice(String userAgent) {
        return userAgent != null && (
            userAgent.contains("Mobile") || 
            userAgent.contains("Android") || 
            userAgent.contains("iPhone") || 
            userAgent.contains("iPad") || 
            userAgent.contains("Windows Phone") || 
            userAgent.contains("BlackBerry")
        );
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
}
