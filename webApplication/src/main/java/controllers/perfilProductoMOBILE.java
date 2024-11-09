package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.beans.PersistenceDelegate;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.market.svcentral.DtProducto;
import com.market.svcentral.Factory;
import com.market.svcentral.ISistema;
import com.market.svcentral.Producto;
import com.market.svcentral.Usuario;

/**
 * Servlet implementation class PerfilProducto
 */
@WebServlet("/perfilProductoMOBILE")
public class perfilProductoMOBILE extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public perfilProductoMOBILE() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    private ISistema sistema;

    @Override
    public void init() throws ServletException {
        try {
            sistema = Factory.getSistema();  
        } catch (Exception e) {
            throw new ServletException("No se pudo inicializar ISistema", e);  
        }
    }

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Cambiado a false para no crear una nueva sesión
        
     // Detectar si el acceso proviene de un dispositivo móvil
        String userAgent = request.getHeader("User-Agent");
        boolean isMobile = isMobileDevice(userAgent);

        if (!isMobile) {
            response.sendRedirect("home");
            return;
        }
       
        if (session == null) {
            response.sendRedirect("formloginMOBILE");
            return;
        }
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();

        Object usuarioLogueado = session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
        	response.sendRedirect("formlogin");
        }
        Usuario user = (Usuario) usuarioLogueado;
        request.setAttribute("usuario", user);

        try {
            String parametro = request.getParameter("producto");
            if (parametro == null) {
                response.sendRedirect("perfilClienteMOBILE");
                return;
            }
            int paramNumero = Integer.parseInt(parametro);
            DtProducto dtprod = (em.find(Producto.class, paramNumero)).crearDT();

            if (dtprod == null) {
                response.sendRedirect("perfilClienteMOBILE");
                return;
            }
            
            request.setAttribute("dtprod", dtprod);
            request.getRequestDispatcher("/WEB-INF/PerfilProductoMOBILE.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect("perfilClienteMOBILE");
        } catch (Exception e) {
            // Manejar otras excepciones si es necesario
            System.out.print(e); // O una página de error adecuada
            return;
        }
        
        em.getTransaction().commit();
        emf.close();
        em.close();
        
        
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
}
