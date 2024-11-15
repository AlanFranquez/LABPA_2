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
import webservices.PublicadorService;
import webservices.Carrito;
import webservices.Producto;
import webservices.Publicador;
import webservices.Usuario;

@WebServlet("/home")
public class Home extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public Home() {
        super();
    }

 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Entrando en el método doGet.");
        PublicadorService p = new PublicadorService();
    	
    	Publicador port = p.getPublicadorPort();
    	
    	System.out.print(port.saludar());
    	
    	System.out.print("PRODUCTO DESDE EL WEBSERVICE "+ port.obtenerProducto(1).getNombre());
        
        
        HttpSession session = request.getSession(false);
        if (session != null) {
            System.out.println("Sesión obtenida correctamente.");
        } else {
            System.out.println("No hay sesión activa.");
        }
        String userAgent = request.getHeader("User-Agent");
        boolean isMobile = isMobileDevice(userAgent);
        
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

            productos = port.listarProductos();
            
            System.out.print("COMPROBANDO");
            for(Producto lala : productos) 
            {
            	System.out.print(lala.getNombre());
            }
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
        
        // Consultar el usuario logueado en la base de datos
        Usuario usuarioLogueado = port.obtenerUsuario(u.getNick());
        if (usuarioLogueado != null) {
            System.out.println("Usuario logueado encontrado en la base de datos.");
        } else {
            System.out.println("Usuario no encontrado en la base de datos.");
        }

        if (port.comprobarCliente(usuarioLogueado.getNick())) {
            webservices.Cliente clienteLogueado = port.obtenerCliente(usuarioLogueado.getNick());
            System.out.println("Cliente logueado: " + clienteLogueado.getNick());

            // Verificar si el cliente tiene carrito
            if (port.obtenerCarritoDeCliente(clienteLogueado.getNick()) ==  null) {
                System.out.println("Carrito no encontrado para el cliente, creando nuevo carrito.");
                Carrito nuevoCarrito = new Carrito();
                port.setCarritoCliente(usuarioLogueado.getNick(), nuevoCarrito);
                
                System.out.println("Nuevo carrito creado y persistido.");
            } else {
                System.out.println("El Cliente ya tiene carrito");
            }

            session.setAttribute("carrito", clienteLogueado.getCarrito());
            
        } else {
            System.out.println("El usuario logueado no es un cliente.");
        }
        request.setAttribute("usuario", usuarioLogueado);
        
        System.out.println("Dispositivo detectado: " + (isMobile ? "Móvil" : "Escritorio"));

        // Establecer productos para la vista
        if (productos != null && !productos.isEmpty()) {
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
