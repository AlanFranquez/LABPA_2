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
import com.market.svcentral.Usuario;
import com.market.svcentral.Carrito;
import com.market.svcentral.Cliente;

@WebServlet("/home")
public class Home extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ISistema sist;

    public Home() {
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

        // Inicia el EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
        EntityManager em = emf.createEntityManager();
        List<Producto> productos = null;

        em.getTransaction().begin();

        try {
            productos = em.createQuery("SELECT p FROM Producto p", Producto.class).getResultList();
            System.out.println("Busqueda en la base de datos :)");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            System.out.println("No hay usuario logueado. Redirigiendo a inicio no logueado.");
            request.setAttribute("prods", productos);
            request.getRequestDispatcher("/WEB-INF/inicioNoLogueado.jsp").forward(request, response);
            return;
        }

        Usuario user = (Usuario) session.getAttribute("usuarioLogueado");
        System.out.println("Usuario logueado: " + (user != null ? user.getNick() : "null"));

        Usuario usuarioLogueado = em.find(Usuario.class, user.getNick());
        if (usuarioLogueado != null && usuarioLogueado instanceof Cliente) {
            Cliente clienteLogueado = (Cliente) usuarioLogueado;
            System.out.println("Cliente logueado: " + clienteLogueado.getNick());

            if (clienteLogueado.getCarrito() == null) {
                System.out.println("Carrito no encontrado para el cliente, creando nuevo carrito.");
                Carrito nuevoCarrito = new Carrito();
                clienteLogueado.setCarrito(nuevoCarrito);
                em.persist(nuevoCarrito);
                System.out.println("Nuevo carrito creado y persistido.");
            } else {
                System.out.println("Carrito existente para el cliente: " + clienteLogueado.getCarrito().getId());
            }

            session.setAttribute("carrito", clienteLogueado.getCarrito());
        } else {
            System.out.println("El usuario logueado no es un cliente.");
        String userAgent = request.getHeader("User-Agent");
        boolean isMobile = isMobileDevice(userAgent);

        request.setAttribute("prods", productos);

        // Comprobación de sesión
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            // Si no hay sesión, redirigir a la página de inicio no logueado
            if (isMobile) {
                request.getRequestDispatcher("/WEB-INF/inicioMOBILE.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/WEB-INF/inicioNoLogueado.jsp").forward(request, response);
            }
            return;
        }

        // Si hay sesión, obtener usuario logueado
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        request.setAttribute("usuario", usuarioLogueado);
        request.setAttribute("estado", "logueado");

        // Redirigir según el tipo de dispositivo
        if (isMobile) {
            request.getRequestDispatcher("/WEB-INF/inicioLogeadoMOBILE.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/inicio.jsp").forward(request, response);
        }

        if (productos != null) {
            request.setAttribute("prods", productos);
        }

        System.out.println("Entra aca");
        request.setAttribute("usuario", usuarioLogueado);
        request.setAttribute("estado", "logueado");

        request.getRequestDispatcher("/WEB-INF/inicio.jsp").forward(request, response);

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
