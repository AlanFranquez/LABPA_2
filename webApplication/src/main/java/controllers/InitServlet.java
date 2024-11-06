package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.market.svcentral.Cliente;
import com.market.svcentral.Comentario;
import com.market.svcentral.DTFecha;
import com.market.svcentral.Factory;
import com.market.svcentral.ISistema;
import com.market.svcentral.Item;
import com.market.svcentral.OrdenDeCompra;
import com.market.svcentral.Producto;
import com.market.svcentral.usuarioRandom;
import com.market.svcentral.Proveedor;

@WebServlet(urlPatterns = {"/initServlet"}, loadOnStartup = 1)
public class InitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
  


	@Override
    public void init() throws ServletException {
    	System.out.print("El SISTEMA INICIO VAMO ARRIBA");
    	ISistema sistema = Factory.getSistema();
    
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
    	
    	EntityManager em = emf.createEntityManager();
    	
         try {
        	 
        	 DTFecha fecha1 = new DTFecha(1, 1, 1990);
             DTFecha fecha2 = new DTFecha(15, 6, 1985);
             DTFecha fecha3 = new DTFecha(5, 6, 1990);
             
             
             em.getTransaction().begin();
             
             sistema.agregarCliente("Juan", "Juan123", "Perez", "Juan@gmail.com", fecha1, "123", "123");

             sistema.agregarCliente("Juan", "Juan123", "Perez", "milagrosvairo.dev@gmail.com", fecha1, "123", "123");
             sistema.agregarCliente("Mili", "Mili123", "Vairo", "maria.vairo@estudiantes.utec.edu.uy", fecha1, "123", "123");
             sistema.agregarCliente("Alberto", "albert1341", "Hernandez", "Ahernandez@gmail.com", fecha2, "123", "123");
             sistema.agregarCliente("Maria", "agusmari", "Agustina", "mariaagustina@gmail.com", fecha1, "123", "123");
             
             
             sistema.agregarImagenUsuario("Juan123", "/images/p1.jpg");
             sistema.agregarImagenUsuario("agusmari", "/images/p3.jpg");
             sistema.agregarImagenUsuario("albert1341", "/images/p2.jpg");
             
             sistema.agregarProveedor("Perez", "AndresPerez@gmail.com", "Andres", "Perez", fecha3 , "Bamboo.inc" , "www.bamboo.com", "123", "123");
             sistema.agregarProveedor("Jorge", "Jorge@gmail.com", "Jorge", "Urrutia", fecha3 , "Google.inc" , "www.google.com", "123", "123");
             
             sistema.agregarImagenUsuario("Perez", "/images/p1.jpg");
             
             em.persist(sistema.getUsuario("Juan123"));
             em.persist(sistema.getUsuario("albert1341"));
             em.persist(sistema.getUsuario("agusmari"));
             em.persist(sistema.getUsuario("Perez"));
             em.persist(sistema.getUsuario("Jorge"));

             
             sistema.agregarCategoria("Living");
             sistema.agregarCategoria("Tecnologia");
             sistema.agregarCategoria("Estanterias");
             
             sistema.agregarCategoriaConProductos("Tecno");
             sistema.asignarlePadreCategoria("Living", "Tecno");
             sistema.agregarCategoriaConProductos("Otros");
             sistema.agregarCategoriaConProductos("Bazar");
             
            
             sistema.agregarProveedor("Perez", "milivairo2303@gmail.com", "Andres", "Perez", fecha3 , "Bamboo.inc" , "www.bamboo.com", "123", "123");
             sistema.agregarProveedor("Jorge", "Jorge@gmail.com", "Jorge", "Urrutia", fecha3 , "Google.inc" , "www.google.com", "123", "123");
             
             sistema.agregarProducto("Pelota", 1, "Pelota inflable ideal", "Increible", 120, "Perez", 100);
             sistema.agregarProducto("Cargador", 2, "Cargador tipo c", "Muy bueno", 220, "Perez", 20);
             sistema.agregarProducto("Television", 3, "Televisión en Excelente estado", "Muy bueno", 330, "Jorge", 120);
             sistema.agregarImagenesDesdeProveedor("Perez", 1, "/images/pelota1.jpg");
             sistema.getProducto(1).agregarImagen("/images/pelota2.jpg");
             
             sistema.agregarImagenesDesdeProveedor("Perez", 2, "/images/cargador1.jpg");
             sistema.agregarImagenesDesdeProveedor("Perez", 2, "/images/cargador2.jpg");
             
             
             sistema.agregarProductoCategoria("Tecno", 1);
             sistema.agregarProductoCategoria("Tecno", 2);
             sistema.agregarProductoCategoria("Bazar", 3);
             
             em.persist(sistema.getProducto(1));
             em.persist(sistema.getProducto(2));
             em.persist(sistema.getProducto(3));
             
             em.persist(sistema.getCat("Living"));
             em.persist(sistema.getCat("Tecnologia"));
             em.persist(sistema.getCat("Estanterias"));
             em.persist(sistema.getCat("Tecno"));
             em.persist(sistema.getCat("Otros"));
             em.persist(sistema.getCat("Bazar"));
             
             
             //Map<Integer, Item> items = new HashMap<>();
             //Item nuevoItem = new Item(5, sistema.getProducto(1));
             //items.put(1, nuevoItem);
             
             
             
          // Crear un nuevo Item y obtener su Proveedor
             Map<Integer, Item> items = new HashMap<>();
             Producto producto = sistema.getProducto(1); // Obtener el producto
             Proveedor proveedor = producto.getProveedor(); // Obtener el proveedor del producto
             Item nuevoItem = new Item(5, producto);
             items.put(1, nuevoItem);

             // Crear la OrdenDeCompra con el proveedor
             OrdenDeCompra orden = new OrdenDeCompra(items, nuevoItem.getSubTotal(), proveedor);
             
             
             Cliente cliente = (Cliente) sistema.getUsuario("Juan123");
             orden.setEstado("Enviado", "LISTO PARA RECOGER");
             sistema.realizarCompra(orden, cliente.getNick());
             cliente.agregarCompra(orden);
             sistema.cambiarEstadoOrden("Enviado", "se está enviando, confíe", orden.getNumero(), cliente.getNick());
             
             Producto p1 = sistema.getProducto(1);
             Comentario c = new Comentario(22, "lalala", cliente, LocalDateTime.now());
             p1.agregarComentario(c);
             
             em.persist(orden);
             em.persist(c);
             
             em.getTransaction().commit();
            
             
             
         	 

             
            
             
           

          // Electrónica
             //sistema.agregarCategoria("Electrónica");
             //sistema.agregarCategoriaConProductos("Celulares y Accesorios");
             //sistema.asignarlePadreCategoria("Electrónica", "Celulares y Accesorios");
             //sistema.agregarCategoriaConProductos("Computadoras y Laptops");
             //sistema.asignarlePadreCategoria("Electrónica", "Computadoras y Laptops");
             //sistema.agregarCategoriaConProductos("Cámaras");      

         } catch (Exception exeption) {
             exeption.printStackTrace();
         }
    }
}
