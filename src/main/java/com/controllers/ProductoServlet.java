package com.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import com.model.Cat_Producto;
import com.model.Categoria;
import com.model.Factory;
import com.model.ISistema;
import com.model.Proveedor;
import com.model.Sistema;

@WebServlet("/registrarproducto")
public class ProductoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


    private ISistema sist;

    @Override
    public void init() throws ServletException {
        try {
            sist = Factory.getSistema();  // Aquí puede estar fallando
        } catch (Exception e) {
            throw new ServletException("No se pudo inicializar ISistema", e);  // Manejar la excepción
        }
    }
   

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {     
        HttpSession objSession = request.getSession();
        Proveedor prov = (Proveedor) objSession.getAttribute("usuarioLogueado");
        
        // Obtener la lista de categorías
        Map<String, Categoria> listacats = sist.getCategoriasLista();
        List<String> listaString = new ArrayList<>();

        for (Map.Entry<String, Categoria> entry : listacats.entrySet()) {
        	if(entry.getValue() instanceof Cat_Producto) {
        		String stringUnico = entry.getValue().getNombre();
                listaString.add(stringUnico);
        	}
        }

        // Establecer el atributo de categorías antes de redirigir al JSP
        if (!listaString.isEmpty()) {
            request.setAttribute("categories", listaString); 
        } else {
            request.setAttribute("error", "No hay categorías disponibles.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("inicio.jsp");
            dispatcher.forward(request, response);
            return; // Asegúrate de salir después de redirigir
        }

        // Ahora que el atributo está configurado, redirige al JSP
        request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {     
        // Obtener el nombre del proveedor (usuario logueado) del atributo de la sesión
        String proveedor = (String) request.getAttribute("nickname");

        // Validar que el proveedor no sea nulo
        /*if (proveedor == null) {
            request.setAttribute("error", "No se ha podido determinar el proveedor.");
            request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
            return;
        }*/

        // Recibiendo los datos del formulario
        String titulo = request.getParameter("titulo");
        String referenciaStr = request.getParameter("referencia");
        String descripcion = request.getParameter("descripcion");
        String especificaciones = request.getParameter("especificaciones");
        String precioStr = request.getParameter("precio");
        String stockStr = request.getParameter("stock");

        // Manejo de referencia
        int referencia = 0;
        if (referenciaStr != null && !referenciaStr.trim().isEmpty()) {
            try {
                referencia = Integer.parseInt(referenciaStr);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "El número de referencia debe ser un número válido.");
                request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
                return;
            }
        }

        // Manejo del precio
        float precio = 0;
        if (precioStr != null && !precioStr.trim().isEmpty()) {
            try {
                precio = Float.parseFloat(precioStr);
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

        // Manejo del stock
        int stock = 0;
        if (stockStr != null && !stockStr.trim().isEmpty()) {
            try {
                stock = Integer.parseInt(stockStr);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "El stock debe ser un número válido.");
                request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
                return;
            }
        }

        // Manejo de las imágenes (limitado a 3)
        Collection<Part> imagenes = request.getParts();
        int cantidadImagenes = 0;
        List<File> archivosImagenes = new ArrayList<>();

        for (Part part : imagenes) {
            if (part.getSubmittedFileName() != null && !part.getSubmittedFileName().isEmpty()) {
                if (cantidadImagenes >= 3) {
                    request.setAttribute("error", "Puedes cargar un máximo de 3 imágenes.");
                    request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
                    return;
                }

                File uploads = new File("/media/images");
                if (!uploads.exists()) {
                    uploads.mkdirs();
                }
                File archivo = new File(uploads, part.getSubmittedFileName());
                part.write(archivo.getAbsolutePath());
                archivosImagenes.add(archivo);
                cantidadImagenes++;
            }
        }

        // Convertir la lista a un arreglo
        File[] archivosArray = archivosImagenes.toArray(new File[0]);

        // Obtener categorías seleccionadas
        String[] categoriasSeleccionadas = request.getParameterValues("categoria");
        Categoria[] cats = null;
        if (categoriasSeleccionadas != null) {
            cats = new Categoria[categoriasSeleccionadas.length];
            for (int i = 0; i < categoriasSeleccionadas.length; i++) {
                cats[i] = sist.getCat(categoriasSeleccionadas[i]);
            }
        }

        // Obtener el proveedor
        Proveedor prov = (Proveedor) sist.getUsuario(proveedor);
     // Registrar producto
        if (precio > 0) { // Solo registrar si el precio es mayor a cero
            prov.registrarProducto(titulo, descripcion, precio, referencia, especificaciones, prov, stock, cats, archivosArray);
            
            // Redirigir a la página de "Producto Agregado" después del registro exitoso
            response.sendRedirect("ProductoAgregado.jsp");
        } else {
            // Manejo del error si el precio es menor o igual a cero
            request.setAttribute("error", "El precio debe ser mayor a cero.");
            request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
            return;
        }

    }
}


//prueba committt
