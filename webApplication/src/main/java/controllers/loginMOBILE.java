package controllers;

import java.io.IOException;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.Publicador;
import services.PublicadorService;

@WebServlet("/formloginMOBILE")
public class loginMOBILE extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public loginMOBILE() {
        super();
    }

    @Override
    public void init() throws ServletException {
      
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Muestra el formulario de inicio de sesión
        request.getRequestDispatcher("/WEB-INF/IniciarSesionMOBILE.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession objSession = request.getSession();
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");
        
        PublicadorService p = new PublicadorService();
        Publicador port = p.getPublicadorPort();
        
        String nuevoEstado;
        services.Usuario usr = port.obtenerUsuario(nickname);
        String userAgent = request.getHeader("User-Agent");
        boolean isMobile = isMobileDevice(userAgent);

        // Validación de usuario no encontrado o contraseña incorrecta
        if (usr == null || !usr.getContrasena().equals(password)) {
            nuevoEstado = "nologueado";
            objSession.setAttribute("estado", nuevoEstado);
            objSession.setAttribute("errorMsg", "Los datos no son válidos");
            request.getRequestDispatcher("/WEB-INF/IniciarSesionMOBILE.jsp").forward(request, response);
            return;
        }

     // Validación de usuario tipo Proveedor o cliente en dispositivo no móvil
        if (usr instanceof services.Proveedor) {
            nuevoEstado = "nologueado";
            objSession.setAttribute("estado", nuevoEstado);
            objSession.setAttribute("errorMsg", "No se permite acceso a Proveedores");
            request.getRequestDispatcher("/WEB-INF/IniciarSesionMOBILE.jsp").forward(request, response);
            return;
        } else if (!isMobile) {
            nuevoEstado = "nologueado";
            objSession.setAttribute("estado", nuevoEstado);
            objSession.setAttribute("errorMsg", "No se permite acceso desde este dispositivo");
            request.getRequestDispatcher("/WEB-INF/IniciarSesionMOBILE.jsp").forward(request, response);
            return;
        }


        nuevoEstado = "logueado";
        objSession.setAttribute("estado", nuevoEstado);
        objSession.setAttribute("usuarioLogueado", usr);
        response.sendRedirect("home"); 
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
