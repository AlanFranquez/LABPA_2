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
    	ISistema sistema = Factory.getSistema();
    	
         try {
        	 
        	 DTFecha fecha1 = new DTFecha(1, 1, 1990);
             DTFecha fecha2 = new DTFecha(15, 6, 1985);
             DTFecha fecha3 = new DTFecha(5, 6, 1990);

             sistema.agregarCliente("Juan", "Juan123", "Perez", "Juan@gmail.com", fecha1, "123", "123");
             sistema.agregarCliente("Alberto", "albert1341", "Hernandez", "Ahernandez@gmail.com", fecha2, "123", "123");
             sistema.agregarCliente("Maria", "agusmari", "Agustina", "mariaagustina@gmail.com", fecha1, "123", "123");

             sistema.agregarImagenUsuario("Juan123", "/images/p1.jpg");
             sistema.agregarImagenUsuario("albert1341", "/images/p2.jpg");
             sistema.agregarImagenUsuario("agusmari", "/images/p3.jpg");
             
             sistema.agregarCategoria("Living");
             sistema.agregarCategoria("Tecnología");
             sistema.agregarCategoria("Estanterias");
             
             sistema.agregarCategoriaConProductos("Tecno");
             sistema.asignarlePadreCategoria("Living", "Tecno");
             sistema.agregarCategoriaConProductos("Otros");
             sistema.agregarCategoriaConProductos("Bazar");

          // Electrónica
             sistema.agregarCategoria("Electrónica");
             sistema.agregarCategoriaConProductos("Celulares y Accesorios");
             sistema.asignarlePadreCategoria("Electrónica", "Celulares y Accesorios");
             sistema.agregarCategoriaConProductos("Computadoras y Laptops");
             sistema.asignarlePadreCategoria("Electrónica", "Computadoras y Laptops");
             sistema.agregarCategoriaConProductos("Cámaras");
             sistema.asignarlePadreCategoria("Electrónica", "Cámaras");
             sistema.agregarCategoriaConProductos("Audio y Video");
             sistema.asignarlePadreCategoria("Electrónica", "Audio y Video");
             sistema.agregarCategoriaConProductos("Consolas de Videojuegos");
             sistema.asignarlePadreCategoria("Electrónica", "Consolas de Videojuegos");
             sistema.agregarCategoriaConProductos("Smart Home");
             sistema.asignarlePadreCategoria("Electrónica", "Smart Home");

             // Hogar y Muebles
             sistema.agregarCategoria("Hogar y Muebles");
             sistema.agregarCategoriaConProductos("Muebles de Sala");
             sistema.asignarlePadreCategoria("Hogar y Muebles", "Muebles de Sala");
             sistema.agregarCategoriaConProductos("Muebles de Dormitorio");
             sistema.asignarlePadreCategoria("Hogar y Muebles", "Muebles de Dormitorio");
             sistema.agregarCategoriaConProductos("Cocina y Comedor");
             sistema.asignarlePadreCategoria("Hogar y Muebles", "Cocina y Comedor");
             sistema.agregarCategoriaConProductos("Decoración del Hogar");
             sistema.asignarlePadreCategoria("Hogar y Muebles", "Decoración del Hogar");
             sistema.agregarCategoriaConProductos("Iluminación");
             sistema.asignarlePadreCategoria("Hogar y Muebles", "Iluminación");
             sistema.agregarCategoriaConProductos("Herramientas");
             sistema.asignarlePadreCategoria("Hogar y Muebles", "Herramientas");

             // Moda y Accesorios
             sistema.agregarCategoria("Moda y Accesorios");
             sistema.agregarCategoriaConProductos("Ropa de Hombre");
             sistema.asignarlePadreCategoria("Moda y Accesorios", "Ropa de Hombre");
             sistema.agregarCategoriaConProductos("Ropa de Mujer");
             sistema.asignarlePadreCategoria("Moda y Accesorios", "Ropa de Mujer");
             sistema.agregarCategoriaConProductos("Calzado");
             sistema.asignarlePadreCategoria("Moda y Accesorios", "Calzado");
             sistema.agregarCategoriaConProductos("Accesorios de Moda");
             sistema.asignarlePadreCategoria("Moda y Accesorios", "Accesorios de Moda");
             sistema.agregarCategoriaConProductos("Joyería");
             sistema.asignarlePadreCategoria("Moda y Accesorios", "Joyería");
             sistema.agregarCategoriaConProductos("Bolsos y Mochilas");
             sistema.asignarlePadreCategoria("Moda y Accesorios", "Bolsos y Mochilas");

             // Deportes y Aire Libre
             sistema.agregarCategoria("Deportes y Aire Libre");
             sistema.agregarCategoriaConProductos("Bicicletas");
             sistema.asignarlePadreCategoria("Deportes y Aire Libre", "Bicicletas");
             sistema.agregarCategoriaConProductos("Equipamiento de Gimnasio");
             sistema.asignarlePadreCategoria("Deportes y Aire Libre", "Equipamiento de Gimnasio");
             sistema.agregarCategoriaConProductos("Ropa Deportiva");
             sistema.asignarlePadreCategoria("Deportes y Aire Libre", "Ropa Deportiva");
             sistema.agregarCategoriaConProductos("Camping y Senderismo");
             sistema.asignarlePadreCategoria("Deportes y Aire Libre", "Camping y Senderismo");
             sistema.agregarCategoriaConProductos("Equipamiento de Deportes");
             sistema.asignarlePadreCategoria("Deportes y Aire Libre", "Equipamiento de Deportes");

             // Salud y Belleza
             sistema.agregarCategoria("Salud y Belleza");
             sistema.agregarCategoriaConProductos("Maquillaje");
             sistema.asignarlePadreCategoria("Salud y Belleza", "Maquillaje");
             sistema.agregarCategoriaConProductos("Cuidado de la Piel");
             sistema.asignarlePadreCategoria("Salud y Belleza", "Cuidado de la Piel");
             sistema.agregarCategoriaConProductos("Fragancias");
             sistema.asignarlePadreCategoria("Salud y Belleza", "Fragancias");
             sistema.agregarCategoriaConProductos("Salud y Bienestar");
             sistema.asignarlePadreCategoria("Salud y Belleza", "Salud y Bienestar");
             sistema.agregarCategoriaConProductos("Suplementos");
             sistema.asignarlePadreCategoria("Salud y Belleza", "Suplementos");

             // Niños y Bebésistema
             sistema.agregarCategoria("Niños y Bebésistema");
             sistema.agregarCategoriaConProductos("Juguetes");
             sistema.asignarlePadreCategoria("Niños y Bebésistema", "Juguetes");
             sistema.agregarCategoriaConProductos("Ropa de Bebé");
             sistema.asignarlePadreCategoria("Niños y Bebésistema", "Ropa de Bebé");
             sistema.agregarCategoriaConProductos("Pañales y Higiene");
             sistema.asignarlePadreCategoria("Niños y Bebésistema", "Pañales y Higiene");
             sistema.agregarCategoriaConProductos("Cunas y Mobiliario");
             sistema.asignarlePadreCategoria("Niños y Bebésistema", "Cunas y Mobiliario");

             // Bebidas y Alimentos
             sistema.agregarCategoria("Bebidas y Alimentos");
             sistema.agregarCategoriaConProductos("Bebidas Alcohólicas");
             sistema.asignarlePadreCategoria("Bebidas y Alimentos", "Bebidas Alcohólicas");
             sistema.agregarCategoriaConProductos("Comestibles");
             sistema.asignarlePadreCategoria("Bebidas y Alimentos", "Comestibles");
             sistema.agregarCategoriaConProductos("Alimentos Gourmet");
             sistema.asignarlePadreCategoria("Bebidas y Alimentos", "Alimentos Gourmet");
             sistema.agregarCategoriaConProductos("Suplementos Nutricionales");
             sistema.asignarlePadreCategoria("Bebidas y Alimentos", "Suplementos Nutricionales");

             // Libros y Música
             sistema.agregarCategoria("Libros y Música");
             sistema.agregarCategoriaConProductos("Libros Físicos");
             sistema.asignarlePadreCategoria("Libros y Música", "Libros Físicos");
             sistema.agregarCategoriaConProductos("E-books");
             sistema.asignarlePadreCategoria("Libros y Música", "E-books");
             sistema.agregarCategoriaConProductos("Instrumentos Musicales");
             sistema.asignarlePadreCategoria("Libros y Música", "Instrumentos Musicales");
             sistema.agregarCategoriaConProductos("Música y Películas");
             sistema.asignarlePadreCategoria("Libros y Música", "Música y Películas");

             // Automotriz
             sistema.agregarCategoria("Automotriz");
             sistema.agregarCategoriaConProductos("Accesorios para Vehículos");
             sistema.asignarlePadreCategoria("Automotriz", "Accesorios para Vehículos");
             sistema.agregarCategoriaConProductos("Mantenimiento y Repuestos");
             sistema.asignarlePadreCategoria("Automotriz", "Mantenimiento y Repuestos");
             sistema.agregarCategoriaConProductos("Herramientas para el Automóvil");
             sistema.asignarlePadreCategoria("Automotriz", "Herramientas para el Automóvil");

             // Jardín y Exterior
             sistema.agregarCategoria("Jardín y Exterior");
             sistema.agregarCategoriaConProductos("Mobiliario de Jardín");
             sistema.asignarlePadreCategoria("Jardín y Exterior", "Mobiliario de Jardín");
             sistema.agregarCategoriaConProductos("Decoración para el Jardín");
             sistema.asignarlePadreCategoria("Jardín y Exterior", "Decoración para el Jardín");
             sistema.agregarCategoriaConProductos("Herramientas de Jardinería");
             sistema.asignarlePadreCategoria("Jardín y Exterior", "Herramientas de Jardinería");
             sistema.agregarCategoriaConProductos("Equipos para Piscinas");
             sistema.asignarlePadreCategoria("Jardín y Exterior", "Equipos para Piscinas");

             // Oficina y Papelería
             sistema.agregarCategoria("Oficina y Papelería");
             sistema.agregarCategoriaConProductos("Mobiliario de Oficina");
             sistema.asignarlePadreCategoria("Oficina y Papelería", "Mobiliario de Oficina");
             sistema.agregarCategoriaConProductos("Computadoras y Equipos");
             sistema.asignarlePadreCategoria("Oficina y Papelería", "Computadoras y Equipos");
             sistema.agregarCategoriaConProductos("Papelería");
             sistema.asignarlePadreCategoria("Oficina y Papelería", "Papelería");
             sistema.agregarCategoriaConProductos("Impresoras y Tintas");
             sistema.asignarlePadreCategoria("Oficina y Papelería", "Impresoras y Tintas");

             // Mascotas
             sistema.agregarCategoria("Mascotas");
             sistema.agregarCategoriaConProductos("Alimentos para Mascotas");
             sistema.asignarlePadreCategoria("Mascotas", "Alimentos para Mascotas");
             sistema.agregarCategoriaConProductos("Accesorios para Mascotas");
             sistema.asignarlePadreCategoria("Mascotas", "Accesorios para Mascotas");
             sistema.agregarCategoriaConProductos("Juguetes para Mascotas");
             sistema.asignarlePadreCategoria("Mascotas", "Juguetes para Mascotas");
             sistema.agregarCategoriaConProductos("Cuidado y Salud");
             sistema.asignarlePadreCategoria("Mascotas", "Cuidado y Salud");

             
            
             ;
             sistema.agregarProveedor("Perez", "AndresPerez@gmail.com", "Andres", "Perez", fecha3 , "Bamboo.inc" , "www.bamboo.com", "123", "123");
             sistema.agregarProveedor("Jorge", "Jorge@gmail.com", "Jorge", "Urrutia", fecha3 , "Google.inc" , "www.google.com", "123", "123");
             
             sistema.agregarImagenUsuario("Perez", "/images/p1.jpg");
             

             sistema.agregarProducto("Pelota", 1, "Pelota inflable ideal", "Increible", 120, "Perez", 100);
             sistema.agregarProducto("Cargador", 2, "Cargador tipo c", "Muy bueno", 220, "Perez", 20);
          
             sistema.agregarProducto("Television", 3, "Televisión en Excelente estado", "Muy bueno", 330, "Jorge", 120);
             
             sistema.agregarProductoCategoria("Tecno", 1);
             sistema.agregarProductoCategoria("Tecno", 2);
             sistema.agregarProductoCategoria("Bazar", 3);
             
             // DOS FORMAS DE AGREGAR IMAGENES
             sistema.agregarImagenesDesdeProveedor("Perez", 1, "/images/pelota1.jpg");
             sistema.getProducto(1).agregarImagen("/images/pelota2.jpg");
             
             sistema.agregarImagenesDesdeProveedor("Perez", 2, "/images/cargador1.jpg");
             sistema.agregarImagenesDesdeProveedor("Perez", 2, "/images/cargador2.jpg");
             
             
             List<String> lista = sistema.getProdByCateogria("Tecno", 2).crearDT().getImagenes();
             
             for (String data : lista) {
            	 System.out.print(data);
             }
             
             
             Map<Integer, Item> items = new HashMap<>();
             Item nuevoItem = new Item(5, sistema.getProducto(1));
             items.put(1, nuevoItem);
             
             
             
             OrdenDeCompra orden = new OrdenDeCompra(items, nuevoItem.getSubTotal());
             
             Cliente cliente = (Cliente) sistema.getUsuario("Juan123");
             sistema.realizarCompra(orden, cliente.getNick());
             cliente.agregarCompra(orden);
             sistema.cambiarEstadoOrden("Enviado", orden.getNumero(), cliente.getNick());

             sistema.agregarImagenUsuario("Juan123", "/images/p1.jpg");
             sistema.agregarImagenUsuario("albert1341", "/images/p2.jpg");
             sistema.agregarImagenUsuario("agusmari", "/images/p3.jpg");
             
             List<Producto> prodlist = sistema.getAllProductos();
             
             for (Producto producto : prodlist) {
            	 System.out.print(producto.getNombre());
             }
             
             

         } catch (Exception exeption) {
             exeption.printStackTrace();
         }
    }
}
