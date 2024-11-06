package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.market.svcentral.Cliente;
import com.market.svcentral.Comentario;
import com.market.svcentral.Factory;
import com.market.svcentral.ISistema;
import com.market.svcentral.Producto;

@WebServlet("/enviarRespuesta")
public class enviarRespuesta extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ISistema sist;

    @Override
    public void init() throws ServletException {
        try {
            sist = Factory.getSistema();
        } catch (Exception exeption) {
            throw new ServletException("No se pudo inicializar ISistema", exeption);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        String respuestaTexto = request.getParameter("respuesta");
        String comentarioIdStr = request.getParameter("comentarioId");
        
        String parametro = request.getParameter("dtprod");

		if (parametro == null || parametro.isEmpty()) {
		    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El producto no está disponible.");
		    return;
		}
		
int paramNum = Integer.parseInt(parametro);
		
		if (session == null || session.getAttribute("usuarioLogueado") == null) {
			response.sendRedirect("formlogin");
			return;
		}
		

        if (comentarioIdStr == null || comentarioIdStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El comentario no está disponible.");
            return;
        }

        int comentarioId = Integer.parseInt(comentarioIdStr);
       
        
        Cliente cli = (Cliente) session.getAttribute("usuarioLogueado");
        Producto producto1 = sist.getProducto(paramNum);
        Comentario coment = producto1.getComentario(comentarioId);
        
        if (coment == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Comentario no encontrado.");
            return;
        }
        
        Random rand = new Random();
        int numeroRandom = rand.nextInt(20000);

        Comentario respuesta = new Comentario(numeroRandom, respuestaTexto, cli, LocalDateTime.now());
        coment.agregarRespuesta(respuesta);
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		em.persist(respuesta);
		
		em.getTransaction().commit();
		
		emf.close();

        // Redirigir a la página del producto
        response.sendRedirect("perfilProducto?producto=" + paramNum);
    }
}
