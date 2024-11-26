package controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import webservices.CatProducto;
import webservices.CategoriaException_Exception;
import webservices.Proveedor;
import webservices.Publicador;
import webservices.PublicadorService;

@WebServlet("/registrarproducto")
@MultipartConfig
public class ProductoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	PublicadorService p = new PublicadorService();
        Publicador port = p.getPublicadorPort();
        
        
        List<webservices.Categoria> categorias = port.listaCatPRODS();
      
       
        request.setAttribute("categories", categorias);
        request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	HttpSession objSession = request.getSession();
        Proveedor prov = (Proveedor) objSession.getAttribute("usuarioLogueado");
        PublicadorService p = new PublicadorService();
        Publicador port = p.getPublicadorPort();

        // Verificar si el proveedor es nulo
        if (prov == null) {
            request.setAttribute("error", "No se encontró el proveedor logueado.");
            request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
            return;
        }
        
        objSession.setAttribute("usuarioLogueado", prov);

        // Recibir los datos del formulario
        String titulo = request.getParameter("titulo");
        String referenciaStr = request.getParameter("referencia");
        String descripcion = request.getParameter("descripcion");
        String especificaciones = request.getParameter("especificaciones");
        String precioStr = request.getParameter("precio");
        String stockStr = request.getParameter("stock");

        // Manejo de referencia
        int referencia = 0;
        try {
            referencia = Integer.parseInt(referenciaStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "El número de referencia debe ser un número válido.");
            request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
            return;
        }

        // Manejo del precio
        float precio = 0;
        try {
            precio = Float.parseFloat(precioStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "El precio debe ser un número válido.");
            request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
            return;
        }

        // Manejo del stock
        int stock = 0;
        try {
            stock = Integer.parseInt(stockStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "El stock debe ser un número válido.");
            request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
            return;
        }

        // Obtener la categoría seleccionada
        String categoriaSeleccionada = request.getParameter("categoria");
        //Categoria cat = sist.getCat(categoriaSeleccionada);

        // Manejo de múltiples imágenes
        List<byte[]> imagenesBytesList = new ArrayList<>();
        for (Part part : request.getParts()) {
        	byte[] imageBytes = null;
            if (part.getName().equals("imagenes") && part.getSize() > 0) {
                String uploadDir = getServletContext().getRealPath("") + File.separator + "media";
                File uploads = new File(uploadDir);
                if (!uploads.exists()) { 
                	uploads.mkdirs();
                }

                // Guardar la imagen
                String fileName = part.getSubmittedFileName();
                File file = new File(uploads, fileName);
                part.write(file.getAbsolutePath());
                
                
                imageBytes = Files.readAllBytes(file.toPath());
                
                // Ruta para almacenar la URL de la imagen
                imagenesBytesList.add(imageBytes);
            }
        }

        
        if (precio > 0) {

            port.agregarProducto(titulo, referencia, descripcion, especificaciones, precio, prov.getNick(), stock);
           
            if (!imagenesBytesList.isEmpty()) {
            	for (byte[] img : imagenesBytesList) {
                    port.agregarImagenProducto(referencia, img);
                }
            }
            
            try {
				port.agregarProductoCategoria(categoriaSeleccionada, referencia);
			} catch (CategoriaException_Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            
            
            //sist.notificarClientesNuevoProducto(sist.getProducto(referencia), prov);

            // Redirigir al perfil del proveedor
            response.sendRedirect("perfilProveedor?nickname=" + prov.getNick());
        } else {
            request.setAttribute("error", "El precio debe ser mayor a cero.");
            request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
        }
        
    }
}
