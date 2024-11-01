package controllers;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.market.svcentral.Factory;
import com.market.svcentral.ISistema;
import com.market.svcentral.Producto;
import com.market.svcentral.Usuario;

@WebServlet("/inicioLogueadoMOBILE")
public class inicioLogueadoMOBILE extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ISistema sist;

    public inicioLogueadoMOBILE() {
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
        HttpSession session = request.getSession(false); // Mantener sin crear nueva sesión
        if (sist == null) {
            throw new ServletException("ISistema no está inicializado.");
        }
        
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            // Usuario no logueado
            List<Producto> productos = sist.getAllProductos();
            request.setAttribute("prods", productos);
            request.getRequestDispatcher("/WEB-INF/inicioNoLogueado.jsp").forward(request, response);
            return;
        }

        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        request.setAttribute("usuario", usuarioLogueado);
        request.setAttribute("estado", "logueado");
        List<Producto> productos = sist.getAllProductos();
        request.setAttribute("prods", productos);

        // Redirigir a la página de inicio logueado
        request.getRequestDispatcher("/WEB-INF/inicioLogueadoMOBILE.jsp").forward(request, response);
    }
}
