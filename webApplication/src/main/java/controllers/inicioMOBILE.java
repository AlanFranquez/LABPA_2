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

@WebServlet("/inicioMOBILE")
public class inicioMOBILE extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ISistema sist;

    public inicioMOBILE() {
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
        HttpSession session = request.getSession(false);
        List<Producto> productos = sist.getAllProductos();
        request.setAttribute("prods", productos);

        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            // Redirigir a la página de inicio no logueado
            request.getRequestDispatcher("/WEB-INF/inicioMOBILE.jsp").forward(request, response);
            return;
        }

        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado != null) {
            request.setAttribute("usuario", usuarioLogueado);
            request.setAttribute("estado", "logueado");
            // Redirigir a la página de inicio logueado
            request.getRequestDispatcher("/WEB-INF/inicioLogeadoMOBILE.jsp").forward(request, response);
        }
    }
}
