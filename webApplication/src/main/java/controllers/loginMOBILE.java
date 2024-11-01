package controllers;

import java.io.IOException;
import com.market.svcentral.EstadoSesion;
import com.market.svcentral.Factory;
import com.market.svcentral.ISistema;
import com.market.svcentral.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/formloginMOBILE")
public class loginMOBILE extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ISistema sist;

    public loginMOBILE() {
        super();
    }

    @Override
    public void init() throws ServletException {
        try {
            sist = Factory.getSistema();
        } catch (Exception e) {
            throw new ServletException("No se pudo inicializar ISistema", e);
        }
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

        EstadoSesion nuevoEstado;
        Usuario usr = sist.getUsuario(nickname);

        if (usr == null || !usr.getContrasena().equals(password)) {
            nuevoEstado = EstadoSesion.LOGIN_INCORRECTO;
            objSession.setAttribute("estado", nuevoEstado);
            objSession.setAttribute("errorMsg", "Los datos no son válidos");
            request.getRequestDispatcher("/WEB-INF/IniciarSesionMOBILE.jsp").forward(request, response);
            response.sendRedirect("formloginMOBILE");  // Redirigir a la página de login con error
            return;
        }

        // Inicio de sesión exitoso
        nuevoEstado = EstadoSesion.LOGIN_CORRECTO;
        objSession.setAttribute("estado", nuevoEstado);
        objSession.setAttribute("usuarioLogueado", usr);
        response.sendRedirect("inicioLogueadoMOBILE");  // Redirigir a inicio logueado
    }
}
