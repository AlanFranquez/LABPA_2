package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.market.svcentral.Cliente;
import com.market.svcentral.DTCliente;
import com.market.svcentral.DTOrdenDeCompra;
import com.market.svcentral.Factory;
import com.market.svcentral.ISistema;

@WebServlet("/perfilOrdenMOBILE")
public class PerfilOrdenMOBILE extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public PerfilOrdenMOBILE() {
        super();
    }
    
    private ISistema sist;

    @Override
    public void init() throws ServletException {
        try {
            sist = Factory.getSistema();  // Aquí puede estar fallando
        } catch (Exception e) {
            throw new ServletException("No se pudo inicializar ISistema", e);  // Manejar la excepción
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Detectar si el acceso proviene de un dispositivo móvil
        String userAgent = request.getHeader("User-Agent");
        boolean isMobile = isMobileDevice(userAgent);
        
        if (!isMobile) {
            response.sendRedirect("home");
            return;
        }

        String ordenParam = request.getParameter("orden");

        HttpSession sess = request.getSession(false); // No crear una nueva sesión
        if (sess == null || sess.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("formloginMOBILE");
            return;
        }

        Cliente cliente = (Cliente) sess.getAttribute("usuarioLogueado");

        if (ordenParam != null && !ordenParam.isEmpty()) {
            int numeroOrden;
            try {
                numeroOrden = Integer.parseInt(ordenParam);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Número de orden inválido.");
                return;
            }

            DTOrdenDeCompra orden = cliente.mostrarCompras(numeroOrden);
            if (orden != null) {
                DTCliente dtcli = (DTCliente) cliente.crearDt();
                request.setAttribute("ordencompra", orden);
                request.setAttribute("usuario", dtcli);
                request.getRequestDispatcher("/WEB-INF/DetalleOrdenMOBILE.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Orden no encontrada.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el número de orden.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        String ordenParam = request.getParameter("numeroOrden");

        // Detectar si el acceso proviene de un dispositivo móvil
        String userAgent = request.getHeader("User-Agent");
        boolean isMobile = isMobileDevice(userAgent);

        if (!isMobile) {
            response.sendRedirect("home");
            return;
        }

        HttpSession sess = request.getSession(false);
        if (sess == null || sess.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("formloginMOBILE");
            return;
        }

        Cliente cliente = (Cliente) sess.getAttribute("usuarioLogueado");

        if (ordenParam != null && !ordenParam.isEmpty()) {
            int numeroOrden;
            try {
                numeroOrden = Integer.parseInt(ordenParam);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Número de orden inválido.");
                return;
            }

            if ("confirmar".equals(accion)) {
                DTOrdenDeCompra orden = cliente.mostrarCompras(numeroOrden);
                if (orden != null) {
                    sist.cambiarEstadoOrden("Entregado", "GRACIAS POR COMPRAR <3", orden.getNumero(), cliente.getNick());
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Orden no encontrada.");
                    return;
                }
            }

            request.setAttribute("ordencompra", cliente.mostrarCompras(numeroOrden));
            response.sendRedirect("perfilOrdenMOBILE?nickname=" + cliente.getNick() + "&orden=" + numeroOrden);

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
