package controllers;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.market.svcentral.Factory;
import com.market.svcentral.ISistema;
import com.market.svcentral.Producto;
import com.market.svcentral.Proveedor;
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
        
        String userAgent = request.getHeader("User-Agent");
        boolean isMobile = isMobileDevice(userAgent);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        Usuario u = (Usuario) (session != null ? session.getAttribute("usuarioLogueado") : null);
        
        Usuario usuarioLogueado = em.find(Usuario.class, u.getNick());
        if (usuarioLogueado == null) {
            // Si no hay usuario logueado, mostrar productos y redirigir al inicio móvil
            List<Producto> productos = sist.getAllProductos();
            request.setAttribute("prods", productos);
            request.getRequestDispatcher("/WEB-INF/inicioMOBILE.jsp").forward(request, response);
            return;
        }

        // Restricción para proveedores en cualquier dispositivo
        if (usuarioLogueado instanceof Proveedor) {
            session.setAttribute("estado", "LOGIN_INCORRECTO");
            session.setAttribute("errorMsg", "Acceso no permitido para proveedores.");
            response.sendRedirect("formloginMOBILE"); // Redirigir al formulario de login con error
            return;
        }

        // Restricción para clientes en PC (solo permiten dispositivos móviles)
        if (!isMobile && !(usuarioLogueado instanceof Proveedor)) {
            session.setAttribute("estado", "LOGIN_INCORRECTO");
            session.setAttribute("errorMsg", "Acceso permitido solo desde dispositivos móviles.");
            response.sendRedirect("formloginMOBILE"); // Redirigir al formulario de login con error
            return;
        }

        // Configuración para inicio de sesión exitoso desde móvil
        request.setAttribute("usuario", usuarioLogueado);
        request.setAttribute("estado", "logueado");
        List<Producto> productos = sist.getAllProductos();
        request.setAttribute("prods", productos);
        
        // Redirigir a la página de inicio logueado móvil
        request.getRequestDispatcher("/WEB-INF/inicioLogueadoMOBILE.jsp").forward(request, response);
        
        em.getTransaction().commit();
        em.close();
        emf.close();
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
