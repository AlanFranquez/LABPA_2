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
import java.io.IOException;
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
         
        	 
        	 DtFecha fecha1 = port.nuevaFecha(12, 12, 1990);
             DtFecha fecha2 = port.nuevaFecha(12, 12, 1990);
             DtFecha fecha3 = port.nuevaFecha(12, 12, 1990);
             
             
             
             
             
             try {
				port.agregarCliente("Juan", "Juan123", "Perez", "Juan@gmail.com", fecha1, "123", "123");
				port.agregarCliente("Mili", "Mili123", "Vairo", "maria.vairo@estudiantes.utec.edu.uy", fecha1, "123", "123");
				port.agregarCliente("Alberto", "albert1341", "Hernandez", "Ahernandez@gmail.com", fecha2, "123", "123");
				port.agregarCliente("Maria", "agusmari", "Agustina", "mariaagustina@gmail.com", fecha1, "123", "123");
			} catch (UsuarioRepetidoException_Exception e) {
				System.out.println(e.getMessage());
			}
             
             
             //sistema.agregarImagenUsuario("Juan123", "/images/p1.jpg");
             //sistema.agregarImagenUsuario("agusmari", "/images/p3.jpg");
             //sistema.agregarImagenUsuario("albert1341", "/images/p2.jpg");
             
         
             try {
            	
            	port.agregarProveedor("Jorge", "Jorge@gmail.com", "Jorge", "Alberto", fecha1, "Nueva Helvecia", "nuevahelvecia.com", "123", "123");
			} catch (UsuarioRepetidoException_Exception e) {
				System.out.println(e.getMessage());
			}
             
             try {
             	
             	//port.agregarProveedor("Jorge", "Jorge@gmail.com", "Jorge", "Alberto", fecha1, "Nueva Helvecia", "nuevahelvecia.com", "123", "123");
             	port.agregarProveedor("Perez", "milivairo2303@gmail.com", "Perez", "Alberto", fecha1, "otramperesa", "otramperesa.com", "123", "123");
 			} catch (UsuarioRepetidoException_Exception e) {
 				System.out.println(e.getMessage());
 			}
             
          
             
             
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
        	 port.agregarProducto("Television", 3, "Televisión en Excelente estado", "Muy bueno", 330, "Jorge", 120);
        	 System.out.println(port.obtenerDTProducto(1).toString());
         } catch (Exception e) {
        	 e.printStackTrace();
         }
             
             
             
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
             
             try {
            	 webservices.Proveedor nick = port.obtenerProvDeProducto(1);
            	 
            	 List<webservices.Item> items = new ArrayList<webservices.Item>();
            	 items.add(port.prodsAItem(5, 1));
            	 
            	 port.realizarCompraPRUEBA(items, 500, nick.getNick(), "Juan123");
            	 
            	 
            	 List<webservices.OrdenDeCompra> ordenes= port.getOrdenesCliente("Juan123");
            	 
            	 for(webservices.OrdenDeCompra ed: ordenes) {
            		 System.out.print("ORDEN DE JUAN123 -->" + ed.getNumero());
            	 }
            	 
             } catch (Exception e) {
            	 e.printStackTrace();
             }
             
             port.setEstadoPrueba("Juan123", "En Camino", "El pedido ha sido enviado y está en camino.");
             
             
             //port.agregarComentario(123, "nuevoComentario", "Juan123", 1);
             
             
             List<webservices.Comentario> comentarios = port.listarComentarios(1);
             
             for(webservices.Comentario c : comentarios) {
            	 System.out.print("COMENTARIO --> "+ c.getTexto());
             }
             
             port.agregarRespuesta(5874, "lalalala", 2, "Juan123");
             System.out.print("SE AGREGO RESPUESTA");
             /*

             // Crear la OrdenDeCompra con el proveedor
             
             
             Cliente cliente = (Cliente) sistema.getUsuario("Juan123");
            
     	     orden.setEstado(estado3);
     	     orden.setEstado(estado4);
             sistema.realizarCompra(orden, cliente.getNick());
             cliente.agregarCompra(orden);
             
             
     	        
             
             
             Producto p1 = sistema.getProducto(1);
             Comentario c = new Comentario(22, "lalala", cliente, LocalDateTime.now());
             p1.agregarComentario(c);
             
             List<Producto> prodDestacados = sistema.obtenerProductosDestacados();
             
             for(Producto p : prodDestacados) {
            	 System.out.print(p.getNombre());
             }
             
              */
             
             
             


             
            
               

         
    }
}
