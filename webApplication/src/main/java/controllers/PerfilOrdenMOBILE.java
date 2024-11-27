package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webservices.Cliente;
import webservices.Item;
import webservices.OrdenDeCompra;
import webservices.Publicador;
import webservices.PublicadorService;
import webservices.Usuario;

import java.io.IOException;
import java.util.List;

@WebServlet("/perfilOrdenMOBILE")
public class PerfilOrdenMOBILE extends HttpServlet {
    private static final long serialVersionUID = 1L;
    PublicadorService p = new PublicadorService();
    Publicador port = p.getPublicadorPort();
    public PerfilOrdenMOBILE() {
        super();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String nickname = request.getParameter("nickname");
        String ordenParam = request.getParameter("orden");

        String userAgent = request.getHeader("User-Agent");
        boolean isMobile = isMobileDevice(userAgent);
        
        HttpSession sess = request.getSession(false); // No crear una nueva sesión
        if (sess == null || sess.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("formlogin");
            return;
        }
        
        if(!isMobile) {
        	response.sendRedirect("home");
            return;
        }
        
        
        

        Usuario user = (Usuario) sess.getAttribute("usuarioLogueado");
        Cliente cliente = port.obtenerCliente(user.getNick());
        

        // Comprobar que el parámetro de la orden no sea nulo y convertirlo a entero
        if (ordenParam != null && !ordenParam.isEmpty()) {
            int numeroOrden;
            try {
                numeroOrden = Integer.parseInt(ordenParam);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Número de orden inválido.");
                return;
            }

            // Obtener la orden específica
            OrdenDeCompra orden = port.getCompra(numeroOrden, cliente.getNick());
            
            
            System.out.println("=======");
            System.out.println(orden.getNumero());
            System.out.println("=======");
            
            List<Item> items = port.imprimirITemsORDENS(numeroOrden, cliente.getNick());
            String estado = port.getEstadoOrden(numeroOrden, cliente.getNick());
            
           
            System.out.print("ESTADO -->" + estado);
            for(Item it: items) {
            	System.out.print("LISTA --> " + it.getProducto().getNumRef());
            }
            
            request.setAttribute("ordencompra", orden);
			request.setAttribute("usuario", cliente);
			request.setAttribute("items", items);
			request.getRequestDispatcher("/WEB-INF/DetalleOrdenMOBILE.jsp").forward(request, response);
            
            
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el número de orden.");
        }
    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String accion = request.getParameter("accion");
        String ordenParam = request.getParameter("numeroOrden");
        
        String userAgent = request.getHeader("User-Agent");
        boolean isMobile = isMobileDevice(userAgent);
        
        HttpSession sess = request.getSession(false); // No crear una nueva sesión
        if (sess == null || sess.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("formlogin");
            return;
        }
        
        if(!isMobile) {
        	response.sendRedirect("home");
            return;
        }
        
        Usuario user = (Usuario) sess.getAttribute("usuarioLogueado");
        Cliente cliente = port.obtenerCliente(user.getNick());
        request.setAttribute("usuarioOrdenEsp", cliente);

        if (ordenParam != null && !ordenParam.isEmpty()) {
            int numeroOrden;
            try {
                numeroOrden = Integer.parseInt(ordenParam);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Número de orden inválido.");
                return;
            }
            
            if ("confirmar".equals(accion)) {
            	OrdenDeCompra orden = port.getCompra(numeroOrden, cliente.getNick());
                if (orden != null) {
             	    port.setEstado(orden.getNumero(), cliente.getNick(), "Entregada", "El cliente ha recibido el pedido.");
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Orden no encontrada.");
                    return;
                }
            }

            request.setAttribute("ordencompra", port.getCompra(numeroOrden, cliente.getNick()));
            response.sendRedirect("perfilOrden?nickname=" + port.getNickCliente(cliente) + "&orden=" + numeroOrden);

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el número de orden.");
        }
    }
    
    // Método auxiliar para detectar si el dispositivo es móvil
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
