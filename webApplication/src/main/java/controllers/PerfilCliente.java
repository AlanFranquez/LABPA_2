package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import webservices.PublicadorService;
import webservices.DtCliente;
import webservices.OrdenDeCompra;
import webservices.Publicador;

/**
 * Servlet implementation class Perfil
 */
@WebServlet("/perfilCliente")
public class PerfilCliente extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(PerfilCliente.class.getName());

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PerfilCliente() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);  // No crear una nueva sesión si no existe
        
        String userAgent = request.getHeader("User-Agent");
        
        // Verificar si es un dispositivo móvil
        if (userAgent != null && (
                userAgent.contains("Mobile") || 
                userAgent.contains("Android") || 
                userAgent.contains("iPhone") || 
                userAgent.contains("iPad") || 
                userAgent.contains("Windows Phone") || 
                userAgent.contains("BlackBerry"))) {
            request.getRequestDispatcher("/WEB-INF/construccion.jsp").forward(request, response);
            return;
        }
        
        // Verificar si el usuario está logueado
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("formlogin");
            return;
        }
        
        EntityManagerFactory emf = null;
        EntityManager em = null;

        emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
        em = emf.createEntityManager();

        PublicadorService p = new PublicadorService();
        Publicador port = p.getPublicadorPort();

        webservices.Usuario user = (webservices.Usuario) session.getAttribute("usuarioLogueado");
        webservices.Cliente cli = port.obtenerCliente(user.getNick());
        
        if (cli == null) {
            logger.warning("No se encontró al usuario en la base de datos");
            response.sendRedirect("home");
            return;
        }
        
        // Obtener las órdenes de compra del cliente
        List<OrdenDeCompra> ordenes = port.getOrdenesCliente(port.getNickCliente(cli));
        
        // Verificar si el cliente es válido
        if (cli != null) {
            DtCliente dtcli = port.crearDTCliente(cli);

            // Obtener el parámetro "nickname"
            String parametro = request.getParameter("nickname");

            if (parametro != null && port.getNickCliente(cli).equals(parametro)) {
                request.setAttribute("usuarioLogueado", user);
                request.setAttribute("usuario", dtcli);
                request.setAttribute("ordenes", ordenes);  // Pasar la lista de ordenes correctamente
                request.getRequestDispatcher("/WEB-INF/InfoPerfilCliente.jsp").forward(request, response);
                return;
            }
        }

        response.sendRedirect("home");

        // Cerrar los recursos de la base de datos
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }
    
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Delegar la solicitud al método doGet
        doGet(request, response);
    }
}
