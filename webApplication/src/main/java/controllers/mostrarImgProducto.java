package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webservices.Publicador;
import webservices.PublicadorService;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@WebServlet("/mostrarImgProducto")
public class mostrarImgProducto extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtén el ID del producto desde el parámetro de la solicitud
        String productoId = request.getParameter("productoId");
        String indiceString = request.getParameter("indice");
        System.out.println("Índice recibido: " + indiceString);

        if (productoId == null || productoId.isEmpty()) {
            System.out.println("ID de producto no proporcionado.");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de producto requerido.");
            return;
        }
        
        int indice = 0;
        try {
			indice = Integer.parseInt(indiceString);
			} catch (Exception e) {
			// TODO: handle exception
		}

        // Conectar al servicio
        PublicadorService service = new PublicadorService();
        Publicador port = service.getPublicadorPort();


        List<String> imagenBase64 = port.obtenerImagenesProducto(Integer.parseInt(productoId));
        
        int cantidad = imagenBase64.size();
        if (imagenBase64 != null) {
            System.out.println("Imagen encontrada y enviada para el Producto: " + port.obtenerProducto(Integer.parseInt(productoId)));
            response.setContentType("image/jpg");
            byte[] imageBytes = Base64.getDecoder().decode(imagenBase64.get(indice));
            response.getOutputStream().write(imageBytes);
            request.setAttribute("cantidad", cantidad);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Imagen no encontrada");
        }
    }
}
