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
            System.out.println("ISistema inicializado correctamente.");
        } catch (Exception e) {
            throw new ServletException("No se pudo inicializar ISistema", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Entrando en el método doGet.");

        HttpSession session = request.getSession(false);
        if (session != null) {
            System.out.println("Sesión obtenida correctamente.");
        } else {
            System.out.println("No hay sesión activa.");
        }
     // Determinar si el dispositivo es móvil
        String userAgent = request.getHeader("User-Agent");
        boolean isMobile = isMobileDevice(userAgent);
        
     // Redirigir según el dispositivo
        if (isMobile) {
            System.out.println("Redirigiendo a vista MOBILE logueado.");
            response.sendRedirect("homeMOBILE");
            return;
        } 

        // Inicia el EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
        EntityManager em = emf.createEntityManager();
        List<Producto> productos = null;

        try {
            em.getTransaction().begin();
            System.out.println("Iniciando transacción de base de datos.");

            productos = em.createQuery("SELECT p FROM Producto p", Producto.class).getResultList();
            System.out.println("Consulta a la base de datos ejecutada. Número de productos: " + (productos != null ? productos.size() : "0"));
        } catch (Exception e) {
            System.out.println("Error al ejecutar la consulta a la base de datos.");
            e.printStackTrace();
            return;
        }

        // Si no hay usuario logueado
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            System.out.println("No hay usuario logueado. Redirigiendo a inicio no logueado.");
            request.setAttribute("prods", productos);
            request.getRequestDispatcher("/WEB-INF/inicioNoLogueado.jsp").forward(request, response);
            return;
        }

        // Usuario logueado
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        
        Usuario user = em.find(Usuario.class, u.getNick());
        // Consultar el usuario logueado en la base de datos
        Usuario usuarioLogueado = em.find(Usuario.class, user.getNick());
        if (usuarioLogueado != null) {
            System.out.println("Usuario logueado encontrado en la base de datos.");
        } else {
            System.out.println("Usuario no encontrado en la base de datos.");
        }

        if (usuarioLogueado instanceof Cliente) {
            Cliente clienteLogueado = (Cliente) usuarioLogueado;
            System.out.println("Cliente logueado: " + clienteLogueado.getNick());

            // Verificar si el cliente tiene carrito
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
        }
        request.setAttribute("usuario", user);
        
        System.out.println("Dispositivo detectado: " + (isMobile ? "Móvil" : "Escritorio"));

        // Establecer productos para la vista
        if (productos != null && !productos.isEmpty()) {
            System.out.println("Productos disponibles: " + productos.size());
            request.setAttribute("prods", productos);
        } else {
            System.out.println("No hay productos disponibles para mostrar.");
        }

        

        request.getRequestDispatcher("/WEB-INF/inicio.jsp").forward(request, response);
        // Commit y cierre de transacción
        try {
            em.getTransaction().commit();
            System.out.println("Transacción commit completada.");
        } catch (Exception e) {
            System.out.println("Error al hacer commit de la transacción.");
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
            System.out.println("EntityManager y EntityManagerFactory cerrados.");
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
