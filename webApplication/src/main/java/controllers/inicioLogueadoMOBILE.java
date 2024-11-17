package controllers;

import java.io.IOException;
import java.util.List;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.Publicador;
import services.PublicadorService;

@WebServlet("/inicioLogueadoMOBILE")
public class inicioLogueadoMOBILE extends HttpServlet {
    private static final long serialVersionUID = 1L;


    public inicioLogueadoMOBILE() {
        super();
    }

    @Override
    public void init() throws ServletException {
      
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Mantener sin crear nueva sesión
        
        
        PublicadorService p = new PublicadorService();
        Publicador port = p.getPublicadorPort();
        
        String userAgent = request.getHeader("User-Agent");
        boolean isMobile = isMobileDevice(userAgent);

        services.Usuario u = (services.Usuario) (session != null ? session.getAttribute("usuarioLogueado") : null);
        
        services.Usuario usuarioLogueado = port.obtenerUsuario(u.getNick());
        if (usuarioLogueado == null) {
            List<services.Producto> productos = port.obtenerProductos();
            request.setAttribute("prods", productos);
            request.getRequestDispatcher("/WEB-INF/inicioMOBILE.jsp").forward(request, response);
            return;
        }

        // Restricción para proveedores en cualquier dispositivo
        if (usuarioLogueado instanceof services.Proveedor) {
            session.setAttribute("estado", "LOGIN_INCORRECTO");
            session.setAttribute("errorMsg", "Acceso no permitido para proveedores.");
            response.sendRedirect("formloginMOBILE"); // Redirigir al formulario de login con error
            return;
        }

        // Restricción para clientes en PC (solo permiten dispositivos móviles)
        if (!isMobile && !(usuarioLogueado instanceof services.Proveedor)) {
            session.setAttribute("estado", "LOGIN_INCORRECTO");
            session.setAttribute("errorMsg", "Acceso permitido solo desde dispositivos móviles.");
            response.sendRedirect("formloginMOBILE"); // Redirigir al formulario de login con error
            return;
        }

        // Configuración para inicio de sesión exitoso desde móvil
        request.setAttribute("usuario", usuarioLogueado);
        request.setAttribute("estado", "logueado");
        List<services.Producto> productos = port.obtenerProductos();
        request.setAttribute("prods", productos);
        
        // Redirigir a la página de inicio logueado móvil
        request.getRequestDispatcher("/WEB-INF/inicioLogeadoMOBILE.jsp").forward(request, response);
        
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
