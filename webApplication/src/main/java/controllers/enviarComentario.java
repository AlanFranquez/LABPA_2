package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.beans.PersistenceDelegate;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.market.svcentral.Cliente;
import com.market.svcentral.Comentario;
import com.market.svcentral.Factory;
import com.market.svcentral.ISistema;
import com.market.svcentral.Producto;

@WebServlet("/enviarComentario")
public class enviarComentario extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EntityManagerFactory emf;
	private EntityManager em;
	
       
    private ISistema sist;
 // Contador de comentarios
    private static int contadorComentarios = 0;

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
		contadorComentarios++;
        int comentarioId = contadorComentarios;
		String mensaje = request.getParameter("comentario");
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
		
		Cliente cliente = (Cliente) session.getAttribute("usuarioLogueado");
		Producto producto1 = sist.getProducto(paramNum);

		// Crear el comentario
		Comentario comentario = new Comentario(comentarioId, mensaje, cliente, LocalDateTime.now());
	
		producto1.agregarComentario(comentario);
		emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
		em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		em.persist(comentario);
		System.out.print("Datos guardados en la base de datos");
		
		em.getTransaction().commit();
		emf.close();
		

		// Redirigir a la página del producto
		response.sendRedirect("perfilProducto?producto=" + paramNum);
	}
}
