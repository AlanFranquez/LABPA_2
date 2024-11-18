package controllers;

import java.io.IOException;
import java.util.List;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webservices.Publicador;
import webservices.PublicadorService;


@WebServlet("/homeMOBILE")
public class inicioMOBILE extends HttpServlet {
    private static final long serialVersionUID = 1L;
  

    public inicioMOBILE() {
        super();
    }

    @Override
    public void init() throws ServletException {
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        PublicadorService p = new PublicadorService();
        Publicador port = p.getPublicadorPort();
        
        
        List<webservices.Producto> productos = port.obtenerProductos();
        request.setAttribute("prods", productos);
        
        String userAgent = request.getHeader("User-Agent");
        boolean isMobile = isMobileDevice(userAgent);

        if (session == null || session.getAttribute("usuarioLogueado") == null) {
        	if(!isMobile) {
        		request.getRequestDispatcher("/WEB-INF/inicioNoLogueado.jsp").forward(request, response);
                return;
            }
        	// Redirigir a la página de inicio no logueado
            request.getRequestDispatcher("/WEB-INF/inicioMOBILE.jsp").forward(request, response);
            return;
        }
        
        

        webservices.Usuario u = (webservices.Usuario) session.getAttribute("usuarioLogueado");
        webservices.Usuario usuarioLogueado = port.obtenerUsuario(u.getNick());
        System.out.print("SE TRAEN LOS DATOS DESDE LA BASE DE DATOS");
        if (usuarioLogueado != null) {
            request.setAttribute("usuario", usuarioLogueado);
            request.setAttribute("estado", "logueado");
            request.getRequestDispatcher("/WEB-INF/inicioLogeadoMOBILE.jsp").forward(request, response);
        }
        
        
    }
    
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
}
