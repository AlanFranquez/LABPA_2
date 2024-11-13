package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;

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

        if (comentarioIdStr == null || comentarioIdStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El comentario no está disponible.");
            return;
        }

        int paramNum = Integer.parseInt(parametro);
        int comentarioId = Integer.parseInt(comentarioIdStr);

        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("formlogin");
            return;
        }

        Cliente cliente = (Cliente) session.getAttribute("usuarioLogueado");
        Producto producto1 = sist.getProducto(paramNum);
        Comentario comentarioRespondido = producto1.getComentario(comentarioId);

        if (comentarioRespondido == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Comentario no encontrado.");
            return;
        }

        // Incrementar el contador de comentarios utilizando un método centralizado
        int respuestaId = sist.incrementarContadorComentarios();

        // Crear la respuesta al comentario
        Comentario respuesta = new Comentario(respuestaId, respuestaTexto, cliente, LocalDateTime.now());
        comentarioRespondido.agregarRespuesta(respuesta);

        // Notificar al autor del comentario respondido
        sist.notificarComentario(producto1, respuesta, comentarioRespondido);

        // Redirigir a la página del producto
        response.sendRedirect("perfilProducto?producto=" + paramNum);
    }
}
