package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webservices.Carrito;
import webservices.Cliente;
import webservices.Item;
import webservices.OrdenDeCompra;
import webservices.Producto;
import webservices.Proveedor;
import webservices.Publicador;
import webservices.PublicadorService;
import webservices.Usuario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Servlet implementation class RealizarCompra
 */
@WebServlet("/realizarCompra")
public class RealizarCompra extends HttpServlet {
    private static final long serialVersionUID = 1L;

    PublicadorService p = new PublicadorService();
    Publicador port = p.getPublicadorPort();
    
    @Override
    public void init() throws ServletException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Lógica de redirección para móviles
        String userAgent = request.getHeader("User-Agent");
        if((userAgent != null) && (
                userAgent.contains("Mobile") || 
                userAgent.contains("Android") || 
                userAgent.contains("iPhone") || 
                userAgent.contains("iPad") || 
                userAgent.contains("Windows Phone") || 
                userAgent.contains("BlackBerry")
            )) {
            request.getRequestDispatcher("/WEB-INF/construccion.jsp").forward(request, response);
            return;
        }

        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("home");
            System.out.print("NO HAY SESION");
            return;
        }

        if (session.getAttribute("mensajeExito") != null && !session.getAttribute("mensajeExito").equals("Su compra se ha realizado con éxito.")) {
            response.sendRedirect("home");
            System.out.print("YA HAY MENSAJE DE EXITO");
            return;
        }
        
        session.setAttribute("mensajeExito", "Su compra se ha realizado con éxito.");

        Usuario user = (Usuario) session.getAttribute("usuarioLogueado");

        if (user instanceof Proveedor) {
            response.sendRedirect("home");
            System.out.print("NO ES UN CLIENTE");
            return;
        }

        Cliente cliente = (Cliente) user;
        Carrito carrito = port.obtenerCarritoCliente(cliente.getNick());
        
        if (cliente.getCarrito() == null || port.clienteTieneCarrito(cliente.getNick())) {
            response.sendRedirect("home");
            System.out.print("EL CARRITO ES NULO");
            return;
        }

        request.setAttribute("usuarioLogueado", user);
        request.setAttribute("carrito", cliente.getCarrito());
        request.getRequestDispatcher("/WEB-INF/realizarCompra.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("home");
            return;
        }

        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        webservices.Usuario user = port.obtenerUsuario(u.getNick());

        if (!port.getTipo(user.getNick()).equals("cliente")) {
            response.sendRedirect("home");
            return;
        }

        webservices.Cliente cliente = (webservices.Cliente) user;
        if (cliente.getCarrito() == null || port.clienteTieneCarrito(user.getNick())) {
            response.sendRedirect("home");
            return;
        }

        List<Item> items = port.getItemsCarrito(user.getNick());
        
        List<Producto> prodsComprados = new ArrayList<Producto>();
        
       

        // Mapa para almacenar las órdenes de compra por proveedor
        Map<Proveedor, Map<Integer, Item>> ordenesPorProveedor = new HashMap<>();

        // Organizar los productos por proveedor
        for (Item item : items) {
            Proveedor proveedor = port.obtenerProvDeProducto(item.getProducto().getNumRef());
            ordenesPorProveedor.putIfAbsent(proveedor, new HashMap<>());
            ordenesPorProveedor.get(proveedor).put(item.getProducto().getNumRef(), item);
            
            System.out.print("IMPRIMIENDO ITEMS" + item.getProducto().getNombre());
            
            prodsComprados.add(port.obtenerProducto(item.getProducto().getNumRef()));
        }
        session.setAttribute("productosComprados",prodsComprados);

        // Variable para acumular el precio total de todas las órdenes de compra
        float precioTotalGeneral = 0;

        // Procesar cada proveedor y crear una OrdenDeCompra
        for (Map.Entry<Proveedor, Map<Integer, Item>> entry : ordenesPorProveedor.entrySet()) {
            Proveedor proveedor = entry.getKey();
            Map<Integer, Item> itemsPorProveedor = entry.getValue();

            // Calcular el precio total para este proveedor
            List <Item> listaItems = new ArrayList<>();
            float precioTotalPorProveedor = 0;
            for (Item item : itemsPorProveedor.values()) {
                precioTotalPorProveedor += item.getSubTotal();
                listaItems.add(item);
            }

            // Sumar el precio total del proveedor al total general
            precioTotalGeneral += precioTotalPorProveedor;

            port.realizarCompraPRUEBA(listaItems, precioTotalPorProveedor, proveedor.getNick(), user.getNick());
        }
//        port.vaciarCarritoCli(cliente.getNick());
        port.vaciarCarrito(user.getNick());

        // Guardar el precio total general en la sesión
        session.setAttribute("mensajeExito", "Su compra se ha realizado con éxito.");
        session.setAttribute("precioTotal", String.valueOf(precioTotalGeneral)); // Total general de todas las órdenes
        
        request.getRequestDispatcher("/WEB-INF/paginaExito.jsp").forward(request, response);
    }
}
