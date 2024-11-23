package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webservices.Publicador;
import webservices.PublicadorService;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;

import com.market.svcentral.Cat_Producto;

import webservices.PublicadorService;
import webservices.CatProducto;
import webservices.DtCliente;
import webservices.OrdenDeCompra;
import webservices.Publicador;

/**
 * Servlet implementation class PerfilProducto
 */
@WebServlet("/perfilProducto")
public class PerfilProducto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PublicadorService p = new PublicadorService();
    Publicador port = p.getPublicadorPort();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PerfilProducto() {
        super();
        // TODO Auto-generated constructor stub
    }

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Cambiado a false para no crear una nueva sesión

        String userAgent = request.getHeader("User-Agent");
        
        PublicadorService p = new PublicadorService();
        Publicador port = p.getPublicadorPort();
        
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

        webservices.Usuario usuarioLogueado = (webservices.Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            response.sendRedirect("formlogin");
            return;
        }
        webservices.Usuario user = (webservices.Usuario) usuarioLogueado;
        request.setAttribute("usuario", user);
        request.setAttribute("nickusuario", user.getNick());
        

            String parametro = request.getParameter("producto");
            if (parametro == null) {
                response.sendRedirect("perfilCliente");
                return;
            }
            
            System.out.print("VALOR A CONVERTIR -> " + parametro);
            int paramNumero = Integer.parseInt(parametro);
            
           
            
            webservices.Producto producto =  port.obtenerProducto(paramNumero);
            List<webservices.Comentario> coms = port.listarComentarios(paramNumero);
            
            
            for(webservices.Comentario c: coms) {
            	
            	System.out.println("COMENTARIOS");
            	System.out.println(c.getTexto());
            }
            
            List<Comentario> comentarios = port.comentariosProducto(paramNumero);
            
            if (dtprod == null) {
                response.sendRedirect("perfilCliente");
                return;
            }
            

            //webservices.DtProducto dtprod = port.crearDTProd(producto);
            
            List<CatProducto> cats = port.getCategoriasDTProd(producto);
            
            // Obtener la lista de rutas de imágenes
            List<String> imagenes = port.getImagenesDTProd(producto);

            // Lista para almacenar las imágenes codificadas en Base64
            List<String> imagenesBase64 = new ArrayList<>();

            // Procesar cada imagen
            for (String imagePath : imagenes) {
                try {
                    // Obtener la ruta absoluta de la imagen (concatenar con la carpeta 'media')
                    String absoluteImagePath = getServletContext().getRealPath("/media" + imagePath);
                    
                    // Crear un archivo a partir de la ruta
                    File imageFile = new File(absoluteImagePath);

                    // Leer la imagen
                    BufferedImage image = ImageIO.read(imageFile);

                    if (image != null) {
                        // Convertir la imagen a un arreglo de bytes
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(image, "jpg", baos);
                        byte[] imageBytes = baos.toByteArray();

                        // Codificar la imagen en Base64
                        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

                        // Agregar la imagen codificada a la lista
                        imagenesBase64.add(base64Image);
                    } else {
                        System.err.println("No se pudo leer la imagen: " + absoluteImagePath);
                    }
                } catch (Exception e) {
                    // Manejar cualquier error al procesar una imagen
                    System.err.println("Error al procesar la imagen: " + imagePath);
                    e.printStackTrace();
                }
            }
            
            // Guardar la lista completa de imágenes Base64 en el request
            request.setAttribute("imagenesBase64", imagenesBase64);
            request.setAttribute("categoriasp", cats);
            request.setAttribute("coms", coms);
            request.setAttribute("prod", producto);
            request.getRequestDispatcher("/WEB-INF/PerfilProducto.jsp").forward(request, response);
   


    }


	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
