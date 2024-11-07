package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.market.svcentral.Cliente;
import com.market.svcentral.DTCliente;
import com.market.svcentral.DTEstado;
import com.market.svcentral.DTOrdenDeCompra;
import com.market.svcentral.Factory;
import com.market.svcentral.ISistema;

/**
 * Servlet implementation class PerfilOrden
 */
@WebServlet("/perfilOrden")
public class PerfilOrden extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PerfilOrden() {
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

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String nickname = request.getParameter("nickname");
        String ordenParam = request.getParameter("orden");

        HttpSession sess = request.getSession(false); // No crear una nueva sesión
        if (sess == null || sess.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("formlogin");
            return;
        }
        

        Cliente cliente = (Cliente) sess.getAttribute("usuarioLogueado");

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
            DTOrdenDeCompra orden = cliente.mostrarCompras(numeroOrden);
            if (orden != null) {
                DTCliente dtcli = (DTCliente) cliente.crearDt();
                request.setAttribute("ordencompra", orden);
                request.setAttribute("usuario", dtcli);
                request.getRequestDispatcher("/WEB-INF/DetalleOrden.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Orden no encontrada.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el número de orden.");
        }
    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String accion = request.getParameter("accion");
        String ordenParam = request.getParameter("numeroOrden");

        // Obtener el cliente de la sesión
        HttpSession sess = request.getSession(false);
        if (sess == null || sess.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("formlogin");
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
            
            
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
            EntityManager em = emf.createEntityManager();
            
            if ("confirmar".equals(accion)) {
            	DTOrdenDeCompra orden = cliente.mostrarCompras(numeroOrden);
                if (orden != null) {
                	TypedQuery<DTEstado> query11 = em.createQuery(
             	            "SELECT e FROM DTEstado e WHERE e.estado = :estado", DTEstado.class);
             	        query11.setParameter("estado", "Entregada");

             	     DTEstado estadoComprada11 = query11.getSingleResult();
             	     sist.cambiarEstadoOrdenconDT(estadoComprada11, numeroOrden, cliente.getNick());
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Orden no encontrada.");
                    return;
                }
            }

            request.setAttribute("ordencompra", cliente.mostrarCompras(numeroOrden));
            response.sendRedirect("perfilOrden?nickname=" + cliente.getNick() + "&orden=" + numeroOrden);

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el número de orden.");
        }
    }
    
}
