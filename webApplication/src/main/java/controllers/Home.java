package controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webservices.Publicador;
import webservices.PublicadorService;


@WebServlet("/home")
public class Home extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public Home() {
        super();
    }

    @Override
    public void init() throws ServletException {
       
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Entrando en el método doGet.");
        
       PublicadorService p = new PublicadorService();
        Publicador port = p.getPublicadorPort();
		
		System.out.print("SALUDANDO DESDE LA WEBAPP --> " + port.saludar());
		
		List<webservices.Producto> pruebaaa = port.obtenerProductos();
		
		System.out.print("PROBANDO QUE FUNCIONA ESTO");
		for(webservices.Producto prodd : pruebaaa) {
			System.out.print(prodd.getNombre());
		}

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
        List<webservices.Producto> productos = null;

        try {
            System.out.println("Iniciando transacción de base de datos.");

            productos = port.obtenerProductos();
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
        webservices.Usuario u = (webservices.Usuario) session.getAttribute("usuarioLogueado");
        
        // Consultar el usuario logueado en la base de datos
        webservices.Usuario usuarioLogueado = port.obtenerUsuario(u.getNick());
        if (usuarioLogueado != null) {
            System.out.println("Usuario logueado encontrado en la base de datos.");
        } else {
            System.out.println("Usuario no encontrado en la base de datos.");
        }

        if (port.obtenerCliente(usuarioLogueado.getNick()) != null) {
            webservices.Cliente clienteLogueado = port.obtenerCliente(usuarioLogueado.getNick());
            System.out.println("Cliente logueado: " + clienteLogueado.getNick());

            // Verificar si el cliente tiene carrito
            if (port.obtenerCarritoDeCliente(clienteLogueado.getNick()) == null) {
                System.out.println("Carrito no encontrado para el cliente, creando nuevo carrito.");
                webservices.Carrito nuevoCarrito = new webservices.Carrito();
                port.setCarritoCliente(clienteLogueado.getNick(), nuevoCarrito);
                System.out.println("Nuevo carrito creado y persistido.");
            } else {
                System.out.println("Carrito existente para el cliente");
            }

            session.setAttribute("carrito", port.obtenerCarritoDeCliente(clienteLogueado.getNick()));
            
        } else {
            System.out.println("El usuario logueado no es un cliente.");
        }
        request.setAttribute("usuario", usuarioLogueado);
        
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
            System.out.println("Transacción commit completada.");
        } catch (Exception e) {
            System.out.println("Error al hacer commit de la transacción.");
            e.printStackTrace();
        } finally {
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