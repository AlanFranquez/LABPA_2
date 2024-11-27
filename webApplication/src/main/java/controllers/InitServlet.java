package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import webservices.CategoriaException_Exception;
import webservices.DtEstado;
import webservices.DtFecha;
import webservices.Publicador;
import webservices.PublicadorService;
import webservices.UsuarioRepetidoException_Exception;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.security.KeyStore.Entry;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

@WebServlet(urlPatterns = {"/initServlet"}, loadOnStartup = 1)
public class InitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    


	@Override
    public void init() throws ServletException {
    	System.out.print("El SISTEMA INICIO VAMO ARRIBA");
    
    		PublicadorService p = new PublicadorService();
    		Publicador port = p.getPublicadorPort();
         
        	 
             
             
             
             String fecha = "12-12-1990";
             
             try {
				port.agregarCliente("Juan", "Juan123", "Perez", "Juan@gmail.com", 12, 12, 1990, "123", "123");
			} catch (UsuarioRepetidoException_Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             
             
             byte[] imageBytes = imagenPrecargada("https://priorityonepayroll.com/wp-content/uploads/2018/06/pop-client-meeting.jpg");
             port.agregarImagenUsuario("Juan123", imageBytes);
         
             port.agregarProveedor("Perez", "Perez@gmail.com", "Perez", "Alberto", 12,12,1990, "Nueva Helvecia", "nuevahelvecia.com", "123", "123");
             
             
             byte[] imageBytes2 = imagenPrecargada("https://www.getcanopy.com/hubfs/Legacy/Imported_Blog_Media/ways-to-manage-clients-effectively-2.jpg");
             port.agregarImagenUsuario("Perez", imageBytes2);
             
          
             
             
            try {
				port.agregarCATPadre("Living");
				port.agregarCATPadre("Tecnologia");
				port.agregarCATPadre("Estanterias");
			} catch (CategoriaException_Exception e) {
				System.out.println(e.getMessage());
			}
             
            try {
				port.agregarCATProducto("Tecno");
				port.asignarlePadreCategoria("Living", "Tecno");
				port.agregarCATProducto("Otros");
				port.agregarCATProducto("Bazar");
			} catch (CategoriaException_Exception e) {
				System.out.println(e.getMessage());
			}
             
             
             try {
            	 DtEstado estado3 = port.crearEstado("En preparación", "El proveedor está preparando el pedido.");
            	 DtEstado estado4 = port.crearEstado("En camino", "El pedido ha sido enviado y está en camino.");
            	 DtEstado estado5 = port.crearEstado("Entregada", "El cliente ha recibido el pedido.");            	 
            	 System.out.print("SE CREAN LOS ESTADOS");
             }catch (Exception e) {
            	 e.printStackTrace();
             }


         try {
        	 port.agregarProducto("Pelota", 1, "Pelota inflable ideal", "Increible", 120, "Perez", 100);
        	 port.agregarProducto("Cargador", 2, "Cargador tipo c", "Muy bueno", 220, "Perez", 20);
        	 port.agregarProducto("Television", 3, "Televisión en Excelente estado", "Muy bueno", 330, "Perez", 120);
        	 System.out.println(port.obtenerDTProducto(1).toString());
         } catch (Exception e) {
        	 e.printStackTrace();
         }
             
             
             port.agregarImagenProducto(1, imagenPrecargada("https://f.fcdn.app/imgs/98c842/www.lacancha.uy/lcanuy/6ac3/original/catalogo/8905_blanco_1/800x800/pelota-neo-swerve-umbro-blanco.jpg"));
             port.agregarImagenProducto(2, imagenPrecargada("https://digitalworld.com.uy/wp-content/uploads/2021/03/Cargador-g-power-5v-a-20v-3.25a-65w-usb-c-g6m.jpg"));
             port.agregarImagenProducto(3, imagenPrecargada("https://cdn.kemik.gt/2024/07/TELEVISIONES-CAT-1200x1200.jpg"));
             //port.agregarImagenesDesdeProveedor("Perez", 1, "/images/pelota1.jpg");
             
             
             
             //sistema.getProducto(1).agregarImagen("/images/pelota2.jpg");
             
             //sistema.agregarImagenesDesdeProveedor("Perez", 2, "/images/cargador1.jpg");
             //sistema.agregarImagenesDesdeProveedor("Perez", 2, "/images/cargador2.jpg");
             
             
    
             try {
				port.agregarProductoCategoria("Tecno", 1);
				port.agregarProductoCategoria("Tecno", 2);
				port.agregarProductoCategoria("Bazar", 3);
			} catch (CategoriaException_Exception e) {
				System.out.println(e.getMessage());
			}
         

         
    }
	
	private byte[] imagenPrecargada(String imagenUrl) {
    	byte[] imageBytes = null;
    	if (imagenUrl != null && !imagenUrl.isEmpty()) {
            try {
                URL url = new URL(imagenUrl);
                InputStream in = url.openStream();

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                }
                imageBytes = byteArrayOutputStream.toByteArray();
                in.close();

                String uploadDir = getServletContext().getRealPath("") + File.separator + "media";
                File uploads = new File(uploadDir);
                if (!uploads.exists()) {
                    uploads.mkdirs();
                }

                String fileName = imagenUrl.substring(imagenUrl.lastIndexOf("/") + 1);
                File file = new File(uploads, fileName);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(imageBytes);
                fileOutputStream.close();

                System.out.println("Imagen descargada y guardada en el servidor: " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                this.getServletContext().log("Error al descargar la imagen: " + e.getMessage());
            }
        }
    	return imageBytes;
    }
}
