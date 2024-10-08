package com.controllers;

import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.io.File;

import javax.swing.ImageIcon;

import com.exceptions.CategoriaException;
import com.exceptions.UsuarioRepetidoException;
import com.model.Cliente;
import com.model.DTFecha;
import com.model.Factory;
import com.model.ISistema;
import com.model.OrdenDeCompra;
import com.model.Producto;
import com.model.Proveedor;

@WebServlet(urlPatterns = {"/initServlet"}, loadOnStartup = 1)
public class InitServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
    	System.out.print("El SISTEMA INCIIO VAMO ARRIBA");
    	ISistema s = Factory.getSistema();
    	
         try {
        	 
        	 DTFecha fecha1 = new DTFecha(1, 1, 1990);
             DTFecha fecha2 = new DTFecha(15, 6, 1985);
             DTFecha fecha3 = new DTFecha(5, 6, 1990);

             s.agregarCliente("Juan", "Juan123", "Perez", "Juan@gmail.com", fecha1, "123", "123");
             s.agregarCliente("Alberto", "albert1341", "Hernandez", "Ahernandez@gmail.com", fecha2, "123", "123");
             s.agregarCliente("Maria", "agusmari", "Agustina", "mariaagustina@gmail.com", fecha1, "123", "123");

             
          // Ruta accesible públicamente (sin getRealPath, directamente la URL relativa)
             String imagePath1 = "/images/p1.jpg";
             s.agregarImagenUsuario("Juan123", imagePath1);

             String imagePath2 = "/images/p2.jpg";
             s.agregarImagenUsuario("albert1341", imagePath2);

             String imagePath3 = "/images/p3.jpg";
             s.agregarImagenUsuario("agusmari", imagePath3);
             
             Cliente e = (Cliente) s.getUsuario("Juan123");
             System.out.print(e.getImagen());
             
             s.agregarCategoria("Living");
             s.agregarCategoria("Tecnología");
             s.agregarCategoria("Estanterias");
             
             s.agregarCategoriaConProductos("Tecno");
             s.asignarlePadreACategoriaProds("Living", "Tecno");
             s.agregarCategoriaConProductos("Otros");
             s.agregarCategoriaConProductos("Bazar");

             
             
            
             Proveedor prov = new Proveedor("Perez", "AndresPerez@gmail.com", "Andres", "Perez", fecha3 ,"Bamboo.inc" , "www.bamboo.com", "123");
             s.agregarProveedor("Perez", "AndresPerez@gmail.com", "Andres", "Perez", fecha3 ,"Bamboo.inc" , "www.bamboo.com", "123", "123");
             s.agregarProveedor("Jorge", "Jorge@gmail.com", "Jorge", "Urrutia", fecha3 ,"Google.inc" , "www.google.com", "123", "123");
             
             s.agregarImagenUsuario("Perez", "./imagenes/p1.jpg");
             
             Producto p1 = new Producto("Pelota", "Pelota inflable ideal", 120, 1,"Lalala", prov, 2);
             s.agregarProducto("Pelota", 1, "Pelota inflable ideal", "Increible", 120, "Perez", 2);
             s.agregarProducto("Cargador", 2, "Cargador tipo c", "Muy bueno", 220, "Perez", 20);
             s.agregarProducto("Sillon Comodo", 3, "Sillon comodo para todos los hogares", "Muy bueno", 330, "Jorge", 25);
             
             s.agregarProductoCategoria("Tecno", 1);
             s.agregarProductoCategoria("Otros", 1);
             s.agregarProductoCategoria("Tecno", 2);
             s.agregarProductoCategoria("Bazar", 3);
             s.agregarImagenesProducto("Tecno", 2, new File("./imagenes/cargador1.jpg"));
             s.agregarImagenesProducto("Tecno", 2, new File("./imagenes/cargador2.jpg"));
             
             s.agregarImagenesProducto("Tecno", 1, new File("./imagenes/pelota1.jpg"));
             s.agregarImagenesProducto("Tecno", 1, new File("./imagenes/pelota2.jpg"));
             
             s.agregarImagenesProducto("Bazar", 3, new File("./imagenes/s1.jpg"));
             s.agregarImagenesProducto("Bazar", 3, new File("./imagenes/s2.jpg"));
             
             s.agregarProductoCategoria("Tecno", 2);
             OrdenDeCompra o1 = new OrdenDeCompra(1);
             o1.addItem(p1, 3);
             
             
             s.addOrdenes(o1, "Juan123");


         } catch (Exception e) {
             e.printStackTrace();
         }
    }
}
