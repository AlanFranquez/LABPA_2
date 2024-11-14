package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import com.market.svcentral.exceptions.CategoriaException;
import com.market.svcentral.Cat_Producto;
import com.market.svcentral.Categoria;
import com.market.svcentral.Factory;
import com.market.svcentral.ISistema;
import com.market.svcentral.Proveedor;

@WebServlet("/registrarproducto")
@MultipartConfig
public class ProductoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ISistema sist;

    @Override
    public void init() throws ServletException {
        try {
            sist = Factory.getSistema();  // Inicializa el sistema usando Factory
        } catch (Exception e) {
            throw new ServletException("No se pudo inicializar ISistema", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //HttpSession objSession = request.getSession();
        //Proveedor prov = (Proveedor) objSession.getAttribute("usuarioLogueado");

        // Obtener la lista de categorías
        Map<String, Categoria> listacats = sist.getCategoriasLista();
        List<String> listaString = new ArrayList<>();

        for (Map.Entry<String, Categoria> entry : listacats.entrySet()) {
            if (entry.getValue() instanceof Cat_Producto) {
                String stringUnico = entry.getValue().getNombre();
                listaString.add(stringUnico);
            }
        }

        // Establecer el atributo de categorías antes de redirigir al JSP
        if (!listaString.isEmpty()) {
            request.setAttribute("categories", listaString); 
        } else {
            request.setAttribute("error", "No hay categorías disponibles.");
            request.getRequestDispatcher("inicio.jsp").forward(request, response);
            return;
        }

        // Redirige al JSP de registro de productos
        request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession objSession = request.getSession();
        Proveedor prov = (Proveedor) objSession.getAttribute("usuarioLogueado");

        // Verificar si el proveedor es nulo
        if (prov == null) {
            request.setAttribute("error", "No se encontró el proveedor logueado.");
            request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
            return;
        }
        
        objSession.setAttribute("usuarioLogueado", prov);

        // Recibir los datos del formulario
        String titulo = request.getParameter("titulo");
        String referenciaStr = request.getParameter("referencia");
        String descripcion = request.getParameter("descripcion");
        String especificaciones = request.getParameter("especificaciones");
        String precioStr = request.getParameter("precio");
        String stockStr = request.getParameter("stock");

        // Manejo de referencia
        int referencia = 0;
        try {
            referencia = Integer.parseInt(referenciaStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "El número de referencia debe ser un número válido.");
            request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
            return;
        }

        // Manejo del precio
        float precio = 0;
        try {
            precio = Float.parseFloat(precioStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "El precio debe ser un número válido.");
            request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
            return;
        }

        // Manejo del stock
        int stock = 0;
        try {
            stock = Integer.parseInt(stockStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "El stock debe ser un número válido.");
            request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
            return;
        }

        // Obtener la categoría seleccionada
        String categoriaSeleccionada = request.getParameter("categoria");
        //Categoria cat = sist.getCat(categoriaSeleccionada);

        // Manejo de múltiples imágenes
        List<String> imagenesUrls = new ArrayList<>();
        for (Part part : request.getParts()) {
            if (part.getName().equals("imagenes") && part.getSize() > 0) {
                String uploadDir = getServletContext().getRealPath("") + File.separator + "media";
                File uploads = new File(uploadDir);
                if (!uploads.exists()) { 
                	uploads.mkdirs();
                }

                // Guardar la imagen
                String fileName = part.getSubmittedFileName();
                File file = new File(uploads, fileName);
                part.write(file.getAbsolutePath());

                // Ruta para almacenar la URL de la imagen
                imagenesUrls.add(fileName);
            }
        }

        
        if (precio > 0) {

            sist.agregarProducto(titulo, referencia, descripcion, especificaciones, precio, prov.getNick(), stock);
           
            if (!imagenesUrls.isEmpty()) {
            	for (String img : imagenesUrls) {
                    System.out.print(img);
                    sist.getProducto(referencia).agregarImagen(img);
                }
            }
            

            Cat_Producto catp1 = new Cat_Producto(categoriaSeleccionada);
            catp1.agregarProducto(sist.getProducto(referencia));
            
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
            EntityManager em = emf.createEntityManager();
            
            em.getTransaction().begin();
            
            
            try {
				sist.agregarProductoCategoria(catp1.getNombre(), referencia);
			} catch (CategoriaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            prov.agregarProd(sist.getProducto(referencia));
            em.persist(sist.getProducto(referencia));
            response.sendRedirect("perfilProveedor?nickname=" + prov.getNick());
            em.getTransaction().commit();
            em.close();
            emf.close();
            

            // Notificar a los clientes sobre el nuevo producto
            sist.notificarClientesNuevoProducto(sist.getProducto(referencia), prov);

            // Redirigir al perfil del proveedor
            response.sendRedirect("perfilProveedor?nickname=" + prov.getNick());
        } else {
            request.setAttribute("error", "El precio debe ser mayor a cero.");
            request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
        }
    }
}
