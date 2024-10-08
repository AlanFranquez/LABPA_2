package com.controllers;

import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import com.exceptions.CategoriaException;
import com.exceptions.UsuarioRepetidoException;
import com.model.Cat_Padre;
import com.model.Categoria;
import com.model.Cliente;
import com.model.Comentario;
import com.model.DTFecha;
import com.model.DTProveedor;
import com.model.DtProducto;
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

             s.agregarImagenUsuario("Juan123", "/images/p1.jpg");
             s.agregarImagenUsuario("albert1341", "/images/p2.jpg");
             s.agregarImagenUsuario("agusmari", "/images/p3.jpg");
             
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
             
             s.agregarImagenUsuario("Perez", "/images/p1.jpg");
             
             Producto p1 = new Producto("Pelota", "Pelota inflable ideal", 120, 1,"Lalala", prov, 2);
             s.agregarProducto("Pelota", 1, "Pelota inflable ideal", "Increible", 120, "Perez", 2);
             s.agregarProducto("Cargador", 2, "Cargador tipo c", "Muy bueno", 220, "Perez", 20);
             s.agregarProducto("Sillon Comodo", 3, "Sillon comodo para todos los hogares", "Muy bueno", 330, "Jorge", 25);
             
             s.agregarProductoCategoria("Tecno", 1);
             s.agregarProductoCategoria("Otros", 1);
             s.agregarProductoCategoria("Tecno", 2);
             s.agregarProductoCategoria("Bazar", 3);
             
             s.agregarProductoCategoria("Tecno", 2);
             
             // DOS FORMAS DE AGREGAR IMAGENES
             s.agregarImagenesDesdeProveedor("Perez", 1, "/images/pelota1.jpg");
             s.getProdByCateogria("Tecno", 1).agregarImagen("/images/pelota2.jpg");
             
             s.agregarImagenesDesdeProveedor("Perez", 2, "/images/cargador1.jpg");
             s.agregarImagenesDesdeProveedor("Perez", 2, "/images/cargador2.jpg");
             
             
             List<String> lista = s.getProdByCateogria("Tecno", 2).crearDT().getImagenes();
             
             for(String dt : lista) {
            	 System.out.print(dt);
             }
             
             if(p1.getComentarios() == null || p1.getComentarios().isEmpty()){
            	 System.out.print("no Hya comentarios");
             } else {
            	 List<Comentario> listacc = p1.getComentarios();
            	 for(Comentario c : listacc) {
            		 System.out.print(c.getTexto());
            	 }
             }
             //
             OrdenDeCompra o1 = new OrdenDeCompra(1);
             o1.addItem(p1, 3);
             //
             
             s.addOrdenes(o1, "Juan123");

             s.agregarImagenUsuario("Juan123", "/images/p1.jpg");
             s.agregarImagenUsuario("albert1341", "/images/p2.jpg");
             s.agregarImagenUsuario("agusmari", "/images/p3.jpg");
             
             
             
             //System.out.print(s.getUsuario("Juan123").getImagen());


         } catch (Exception e) {
             e.printStackTrace();
         }
    }
}
