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
import java.util.Random;

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
		Random numRandom = new Random();
        contadorComentarios++;
        int comentarioId = contadorComentarios;
		
		emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
		em = emf.createEntityManager();
		
		em.getTransaction().begin();

        String mensaje = request.getParameter("comentario");
        String parametro = request.getParameter("dtprod");

		int paramNum = Integer.parseInt(parametro);
		
		if (session == null || session.getAttribute("usuarioLogueado") == null) {
			response.sendRedirect("formlogin");
			return;
		}
		
		Cliente cliente = (Cliente) session.getAttribute("usuarioLogueado");
		Producto producto1 = em.find(Producto.class, paramNum);

		// Crear el comentario
		Comentario comentario = new Comentario(comentarioId, mensaje, cliente, LocalDateTime.now());
		producto1.agregarComentario(comentario);
		em.persist(comentario);
		System.out.print("Comentario guardado en la base de datos");
	
		
		
		em.getTransaction().commit();
		em.close();
		emf.close();
		

        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("formlogin");
            return;
        }

        // Crear el comentario principal
        Comentario nuevoComentario = new Comentario(comentarioId, mensaje, cliente, LocalDateTime.now());
        producto1.agregarComentario(nuevoComentario);

        // Notificar a los interesados
        sist.notificarComentario(producto1, nuevoComentario, null);

        // Redirigir a la página del producto
        response.sendRedirect("perfilProducto?producto=" + paramNum);
    }
}
