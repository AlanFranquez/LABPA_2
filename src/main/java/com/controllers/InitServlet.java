package com.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.Cliente;
import com.model.DTFecha;
import com.model.Factory;
import com.model.ISistema;
import com.model.Item;
import com.model.OrdenDeCompra;
import com.model.Producto;

@WebServlet(urlPatterns = {"/initServlet"}, loadOnStartup = 1)
public class InitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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
             s.asignarlePadreCategoria("Living", "Tecno");
             s.agregarCategoriaConProductos("Otros");
             s.agregarCategoriaConProductos("Bazar");

          // Electrónica
             s.agregarCategoria("Electrónica");
             s.agregarCategoriaConProductos("Celulares y Accesorios");
             s.asignarlePadreCategoria("Electrónica", "Celulares y Accesorios");
             s.agregarCategoriaConProductos("Computadoras y Laptops");
             s.asignarlePadreCategoria("Electrónica", "Computadoras y Laptops");
             s.agregarCategoriaConProductos("Cámaras");
             s.asignarlePadreCategoria("Electrónica", "Cámaras");
             s.agregarCategoriaConProductos("Audio y Video");
             s.asignarlePadreCategoria("Electrónica", "Audio y Video");
             s.agregarCategoriaConProductos("Consolas de Videojuegos");
             s.asignarlePadreCategoria("Electrónica", "Consolas de Videojuegos");
             s.agregarCategoriaConProductos("Smart Home");
             s.asignarlePadreCategoria("Electrónica", "Smart Home");

             // Hogar y Muebles
             s.agregarCategoria("Hogar y Muebles");
             s.agregarCategoriaConProductos("Muebles de Sala");
             s.asignarlePadreCategoria("Hogar y Muebles", "Muebles de Sala");
             s.agregarCategoriaConProductos("Muebles de Dormitorio");
             s.asignarlePadreCategoria("Hogar y Muebles", "Muebles de Dormitorio");
             s.agregarCategoriaConProductos("Cocina y Comedor");
             s.asignarlePadreCategoria("Hogar y Muebles", "Cocina y Comedor");
             s.agregarCategoriaConProductos("Decoración del Hogar");
             s.asignarlePadreCategoria("Hogar y Muebles", "Decoración del Hogar");
             s.agregarCategoriaConProductos("Iluminación");
             s.asignarlePadreCategoria("Hogar y Muebles", "Iluminación");
             s.agregarCategoriaConProductos("Herramientas");
             s.asignarlePadreCategoria("Hogar y Muebles", "Herramientas");

             // Moda y Accesorios
             s.agregarCategoria("Moda y Accesorios");
             s.agregarCategoriaConProductos("Ropa de Hombre");
             s.asignarlePadreCategoria("Moda y Accesorios", "Ropa de Hombre");
             s.agregarCategoriaConProductos("Ropa de Mujer");
             s.asignarlePadreCategoria("Moda y Accesorios", "Ropa de Mujer");
             s.agregarCategoriaConProductos("Calzado");
             s.asignarlePadreCategoria("Moda y Accesorios", "Calzado");
             s.agregarCategoriaConProductos("Accesorios de Moda");
             s.asignarlePadreCategoria("Moda y Accesorios", "Accesorios de Moda");
             s.agregarCategoriaConProductos("Joyería");
             s.asignarlePadreCategoria("Moda y Accesorios", "Joyería");
             s.agregarCategoriaConProductos("Bolsos y Mochilas");
             s.asignarlePadreCategoria("Moda y Accesorios", "Bolsos y Mochilas");

             // Deportes y Aire Libre
             s.agregarCategoria("Deportes y Aire Libre");
             s.agregarCategoriaConProductos("Bicicletas");
             s.asignarlePadreCategoria("Deportes y Aire Libre", "Bicicletas");
             s.agregarCategoriaConProductos("Equipamiento de Gimnasio");
             s.asignarlePadreCategoria("Deportes y Aire Libre", "Equipamiento de Gimnasio");
             s.agregarCategoriaConProductos("Ropa Deportiva");
             s.asignarlePadreCategoria("Deportes y Aire Libre", "Ropa Deportiva");
             s.agregarCategoriaConProductos("Camping y Senderismo");
             s.asignarlePadreCategoria("Deportes y Aire Libre", "Camping y Senderismo");
             s.agregarCategoriaConProductos("Equipamiento de Deportes");
             s.asignarlePadreCategoria("Deportes y Aire Libre", "Equipamiento de Deportes");

             // Salud y Belleza
             s.agregarCategoria("Salud y Belleza");
             s.agregarCategoriaConProductos("Maquillaje");
             s.asignarlePadreCategoria("Salud y Belleza", "Maquillaje");
             s.agregarCategoriaConProductos("Cuidado de la Piel");
             s.asignarlePadreCategoria("Salud y Belleza", "Cuidado de la Piel");
             s.agregarCategoriaConProductos("Fragancias");
             s.asignarlePadreCategoria("Salud y Belleza", "Fragancias");
             s.agregarCategoriaConProductos("Salud y Bienestar");
             s.asignarlePadreCategoria("Salud y Belleza", "Salud y Bienestar");
             s.agregarCategoriaConProductos("Suplementos");
             s.asignarlePadreCategoria("Salud y Belleza", "Suplementos");

             // Niños y Bebés
             s.agregarCategoria("Niños y Bebés");
             s.agregarCategoriaConProductos("Juguetes");
             s.asignarlePadreCategoria("Niños y Bebés", "Juguetes");
             s.agregarCategoriaConProductos("Ropa de Bebé");
             s.asignarlePadreCategoria("Niños y Bebés", "Ropa de Bebé");
             s.agregarCategoriaConProductos("Pañales y Higiene");
             s.asignarlePadreCategoria("Niños y Bebés", "Pañales y Higiene");
             s.agregarCategoriaConProductos("Cunas y Mobiliario");
             s.asignarlePadreCategoria("Niños y Bebés", "Cunas y Mobiliario");

             // Bebidas y Alimentos
             s.agregarCategoria("Bebidas y Alimentos");
             s.agregarCategoriaConProductos("Bebidas Alcohólicas");
             s.asignarlePadreCategoria("Bebidas y Alimentos", "Bebidas Alcohólicas");
             s.agregarCategoriaConProductos("Comestibles");
             s.asignarlePadreCategoria("Bebidas y Alimentos", "Comestibles");
             s.agregarCategoriaConProductos("Alimentos Gourmet");
             s.asignarlePadreCategoria("Bebidas y Alimentos", "Alimentos Gourmet");
             s.agregarCategoriaConProductos("Suplementos Nutricionales");
             s.asignarlePadreCategoria("Bebidas y Alimentos", "Suplementos Nutricionales");

             // Libros y Música
             s.agregarCategoria("Libros y Música");
             s.agregarCategoriaConProductos("Libros Físicos");
             s.asignarlePadreCategoria("Libros y Música", "Libros Físicos");
             s.agregarCategoriaConProductos("E-books");
             s.asignarlePadreCategoria("Libros y Música", "E-books");
             s.agregarCategoriaConProductos("Instrumentos Musicales");
             s.asignarlePadreCategoria("Libros y Música", "Instrumentos Musicales");
             s.agregarCategoriaConProductos("Música y Películas");
             s.asignarlePadreCategoria("Libros y Música", "Música y Películas");

             // Automotriz
             s.agregarCategoria("Automotriz");
             s.agregarCategoriaConProductos("Accesorios para Vehículos");
             s.asignarlePadreCategoria("Automotriz", "Accesorios para Vehículos");
             s.agregarCategoriaConProductos("Mantenimiento y Repuestos");
             s.asignarlePadreCategoria("Automotriz", "Mantenimiento y Repuestos");
             s.agregarCategoriaConProductos("Herramientas para el Automóvil");
             s.asignarlePadreCategoria("Automotriz", "Herramientas para el Automóvil");

             // Jardín y Exterior
             s.agregarCategoria("Jardín y Exterior");
             s.agregarCategoriaConProductos("Mobiliario de Jardín");
             s.asignarlePadreCategoria("Jardín y Exterior", "Mobiliario de Jardín");
             s.agregarCategoriaConProductos("Decoración para el Jardín");
             s.asignarlePadreCategoria("Jardín y Exterior", "Decoración para el Jardín");
             s.agregarCategoriaConProductos("Herramientas de Jardinería");
             s.asignarlePadreCategoria("Jardín y Exterior", "Herramientas de Jardinería");
             s.agregarCategoriaConProductos("Equipos para Piscinas");
             s.asignarlePadreCategoria("Jardín y Exterior", "Equipos para Piscinas");

             // Oficina y Papelería
             s.agregarCategoria("Oficina y Papelería");
             s.agregarCategoriaConProductos("Mobiliario de Oficina");
             s.asignarlePadreCategoria("Oficina y Papelería", "Mobiliario de Oficina");
             s.agregarCategoriaConProductos("Computadoras y Equipos");
             s.asignarlePadreCategoria("Oficina y Papelería", "Computadoras y Equipos");
             s.agregarCategoriaConProductos("Papelería");
             s.asignarlePadreCategoria("Oficina y Papelería", "Papelería");
             s.agregarCategoriaConProductos("Impresoras y Tintas");
             s.asignarlePadreCategoria("Oficina y Papelería", "Impresoras y Tintas");

             // Mascotas
             s.agregarCategoria("Mascotas");
             s.agregarCategoriaConProductos("Alimentos para Mascotas");
             s.asignarlePadreCategoria("Mascotas", "Alimentos para Mascotas");
             s.agregarCategoriaConProductos("Accesorios para Mascotas");
             s.asignarlePadreCategoria("Mascotas", "Accesorios para Mascotas");
             s.agregarCategoriaConProductos("Juguetes para Mascotas");
             s.asignarlePadreCategoria("Mascotas", "Juguetes para Mascotas");
             s.agregarCategoriaConProductos("Cuidado y Salud");
             s.asignarlePadreCategoria("Mascotas", "Cuidado y Salud");

             
            
             ;
             s.agregarProveedor("Perez", "AndresPerez@gmail.com", "Andres", "Perez", fecha3 ,"Bamboo.inc" , "www.bamboo.com", "123", "123");
             s.agregarProveedor("Jorge", "Jorge@gmail.com", "Jorge", "Urrutia", fecha3 ,"Google.inc" , "www.google.com", "123", "123");
             
             s.agregarImagenUsuario("Perez", "/images/p1.jpg");
             

             s.agregarProducto("Pelota", 1, "Pelota inflable ideal", "Increible", 120, "Perez", 100);
             s.agregarProducto("Cargador", 2, "Cargador tipo c", "Muy bueno", 220, "Perez", 20);
          
             s.agregarProducto("Television", 3, "Televisión en Excelente estado", "Muy bueno", 330, "Jorge", 120);
             
             s.agregarProductoCategoria("Tecno", 1);
             s.agregarProductoCategoria("Tecno", 2);
             s.agregarProductoCategoria("Bazar", 3);
             
             // DOS FORMAS DE AGREGAR IMAGENES
             s.agregarImagenesDesdeProveedor("Perez", 1, "/images/pelota1.jpg");
             s.getProducto(1).agregarImagen("/images/pelota2.jpg");
             
             s.agregarImagenesDesdeProveedor("Perez", 2, "/images/cargador1.jpg");
             s.agregarImagenesDesdeProveedor("Perez", 2, "/images/cargador2.jpg");
             
             
             List<String> lista = s.getProdByCateogria("Tecno", 2).crearDT().getImagenes();
             
             for(String dt : lista) {
            	 System.out.print(dt);
             }
             
             
             Map<Integer, Item> items = new HashMap<>();
             Item nuevoItem = new Item(5, s.getProducto(1));
             items.put(1, nuevoItem);
             
             
             
             OrdenDeCompra o = new OrdenDeCompra(items, nuevoItem.getSubTotal());
             
             Cliente cl = (Cliente) s.getUsuario("Juan123");
             s.realizarCompra(o, cl.getNick());
             cl.agregarCompra(o);
             s.cambiarEstadoOrden("Enviado", o.getNumero(), cl.getNick());

             s.agregarImagenUsuario("Juan123", "/images/p1.jpg");
             s.agregarImagenUsuario("albert1341", "/images/p2.jpg");
             s.agregarImagenUsuario("agusmari", "/images/p3.jpg");
             
             List<Producto> prodlist = s.getAllProductos();
             
             for(Producto p : prodlist) {
            	 System.out.print(p.getNombre());
             }
             
             

         } catch (Exception e) {
             e.printStackTrace();
         }
    }
}
