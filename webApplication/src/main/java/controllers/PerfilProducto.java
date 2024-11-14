package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.market.svcentral.Comentario;
import com.market.svcentral.DtProducto;
import com.market.svcentral.Factory;
import com.market.svcentral.ISistema;
import com.market.svcentral.Producto;
import com.market.svcentral.Usuario;

/**
 * Servlet implementation class PerfilProducto
 */
@WebServlet("/perfilProducto")
public class PerfilProducto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PerfilProducto() {
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
        
        if (session == null) {
            response.sendRedirect("formlogin");
            return;
        }

        Object usuarioLogueado = session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            response.sendRedirect("formlogin");
            return;
        }
        Usuario user = (Usuario) usuarioLogueado;
        request.setAttribute("usuario", user);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            String parametro = request.getParameter("producto");
            if (parametro == null) {
                response.sendRedirect("perfilCliente");
                return;
            }

            int paramNumero = Integer.parseInt(parametro);

            Producto producto = em.find(Producto.class, paramNumero);

            for(Comentario c: producto.getComentarios()) {
            	
            	System.out.println();
            	System.out.println(c.getTexto());
            }
            
            if (producto == null) {
                response.sendRedirect("perfilCliente");
                return;
            }

            DtProducto dtprod = producto.crearDT();
            
            List<Comentario> comentarios = producto.getComentarios();

            request.setAttribute("dtprod", dtprod);
            request.setAttribute("comentarios", comentarios);
            request.getRequestDispatcher("/WEB-INF/PerfilProducto.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            // Si el parámetro no es un número válido, redirigir al perfil del cliente
            response.sendRedirect("perfilCliente");
        } catch (Exception e) {
            // Manejar otras excepciones si es necesario
            response.sendRedirect("errorPage"); // O una página de error adecuada
        } finally {
            em.getTransaction().commit();
            em.close();
            emf.close();
        }
    }


	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
