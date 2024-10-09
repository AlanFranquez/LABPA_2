package com.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import com.model.Proveedor;
import com.model.Categoria;
import com.model.Factory;
import com.model.ISistema;
import com.model.Sistema;

@WebServlet ("/registrarproducto")
public class ProductoServlet extends HttpServlet {

    private static final long serialVersionUID = 2L;
    private ISistema sist;

    public ProductoServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
        try {
            sist = Factory.getSistema();  // Inicialización del sistema
        } catch (Exception e) {
            throw new ServletException("No se pudo inicializar ISistema", e);  // Manejar la excepción
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession objSession = request.getSession();
    	Proveedor prov = (Proveedor) objSession.getAttribute("usuarioLogueado");
    	
    	//if(prov == null) {
    	//	response.sendRedirect("home");
    	//}
    	//else {
    		
    		
    		Categoria categorias[] = sist.getCategorias();

    		if (categorias != null) {
    			String nombres[] = new String[categorias.length];

    			for (int i = 0; i < categorias.length; i++) {
    				if (categorias[i] != null) {
    					nombres[i] = categorias[i].getNombre();
    				}
    			}

    			request.setAttribute("categorias", nombres);
    			request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
    		} 
    		else {
    			request.setAttribute("error", "No hay categorías disponibles.");
    			RequestDispatcher dispatcher = request.getRequestDispatcher("inicio.jsp");
    			dispatcher.forward(request, response);
    		}
    	}
    //}


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el nombre del proveedor (usuario logueado) del atributo de la sesión
        String proveedor = (String) request.getAttribute("nickname");

        // Recibiendo los datos del formulario
        String titulo = request.getParameter("titulo");
        String referenciaStr = request.getParameter("referencia");
        Integer referencia = 0;  // Valor por defecto
        String descripcion = request.getParameter("descripcion");
        String especificaciones = request.getParameter("especificaciones");
        String precioStr = request.getParameter("precio");

        if (precioStr != null && !precioStr.trim().isEmpty()) {
            try {
                float precio = Float.parseFloat(precioStr);  // La variable precio está dentro de este bloque
            } catch (NumberFormatException e) {
                request.setAttribute("error", "El precio debe ser un número válido.");
                request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
                return;
            }
        } else {
            request.setAttribute("error", "El campo de precio no puede estar vacío.");
            request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
            return;
        }

        // Aquí la variable precio no es accesible porque fue declarada dentro del bloque if/try


        int stock = (int) Float.parseFloat(request.getParameter("stock"));

     // Obtener la imagen del formulario
        Collection<Part> imagenes = request.getParts();
        int cantidadImagenes = 0;
        List<File> archivosImagenes = new ArrayList<>();
        
        for (Part part : imagenes) {
            // Asegurarse de que la parte no esté vacía y que tenga un nombre de archivo
            if (part.getSubmittedFileName() != null && !part.getSubmittedFileName().isEmpty()) {
                // Verificar si ya se han subido 3 imágenes
                if (cantidadImagenes >= 3) {
                    request.setAttribute("error", "Puedes cargar un máximo de 3 imágenes.");
                    request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
                    return;
                }
                
                // Obtener el nombre del archivo
                String nombreArchivo = part.getSubmittedFileName();
                // Especificar la ruta donde deseas guardar el archivo
                File uploads = new File("/media/images"); // Cambia esta ruta según sea necesario
                if (!uploads.exists()) {
                    uploads.mkdirs(); // Crea el directorio si no existe
                }
                File archivo = new File(uploads, nombreArchivo);
                // Guardar el archivo
                part.write(archivo.getAbsolutePath());
                // Añadir el archivo a la lista
                archivosImagenes.add(archivo);
                cantidadImagenes++;
            }
        }
        
        
        String[] categoriasSeleccionadas = request.getParameterValues("categoria");
        Categoria[] cats = null;

        if (categoriasSeleccionadas != null) {
            cats = new Categoria[categoriasSeleccionadas.length];
            for (int i = 0; i < categoriasSeleccionadas.length; i++) {
                cats[i] = sist.getCat(categoriasSeleccionadas[i]);
            }
        }

        // Procesar los datos recibidos
        

        // Buscar el proveedor por su nombre de usuario
        Proveedor p = (Proveedor) sist.getUsuario(proveedor);
        Proveedor pcopia = p;


        // Registrar el producto con los datos recibidos
        p.registrarProducto(titulo, referencia, descripcion, especificaciones, precio, pcopia, stock, cats, archivosImagenes);

        
        response.sendRedirect("home");
    }
}
