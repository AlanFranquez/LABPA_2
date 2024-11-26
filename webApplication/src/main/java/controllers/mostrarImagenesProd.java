package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import webservices.Publicador;
import webservices.PublicadorService;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@WebServlet("/mostrarImagenesProd")
public class mostrarImagenesProd extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtén el ID del producto desde el parámetro de la solicitud
        String productoId = request.getParameter("productoId");
        String imagenIndexParam = request.getParameter("imagenIndex");

        if (productoId == null || productoId.isEmpty() || imagenIndexParam == null || imagenIndexParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan parámetros.");
            return;
        }

        try {
            int productoIdInt = Integer.parseInt(productoId);
            int imagenIndex = Integer.parseInt(imagenIndexParam);

            // Conectar al servicio
            PublicadorService service = new PublicadorService();
            Publicador port = service.getPublicadorPort();

            // Obtener lista de imágenes del producto
            List<String> imagenesBase64 = port.obtenerImagenesProducto(productoIdInt);

            if (imagenesBase64 == null || imagenesBase64.isEmpty() || imagenIndex < 0 || imagenIndex >= imagenesBase64.size()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Imagen no encontrada.");
                return;
            }

            for(int i = 0; i < imagenesBase64.size(); i++) {
            	
            	String imagenBase64 = imagenesBase64.get(imagenIndex);
            	byte[] imageBytes = Base64.getDecoder().decode(imagenBase64);
            	response.setContentType("image/jpg");
            	response.getOutputStream().write(imageBytes);
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos.");
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al procesar la solicitud.");
            e.printStackTrace();
        }
    }
}
