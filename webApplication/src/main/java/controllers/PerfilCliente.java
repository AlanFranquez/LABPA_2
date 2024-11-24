package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import webservices.PublicadorService;
import webservices.DtCliente;
import webservices.OrdenDeCompra;
import webservices.Publicador;

/**
 * Servlet implementation class Perfil
 */
@WebServlet("/perfilCliente")
public class PerfilCliente extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(PerfilCliente.class.getName());

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PerfilCliente() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);  // No crear una nueva sesión si no existe
        
        String userAgent = request.getHeader("User-Agent");
        
        // Verificar si es un dispositivo móvil
        if (userAgent != null && (
                userAgent.contains("Mobile") || 
                userAgent.contains("Android") || 
                userAgent.contains("iPhone") || 
                userAgent.contains("iPad") || 
                userAgent.contains("Windows Phone") || 
                userAgent.contains("BlackBerry"))) {
        	request.getRequestDispatcher("perfilClienteMOBILE").forward(request, response);
            return;
        }
        
        // Verificar si el usuario está logueado
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("formlogin");
            return;
        }
        
        
       
        PublicadorService p = new PublicadorService();
        Publicador port = p.getPublicadorPort();

        webservices.Usuario user = (webservices.Usuario) session.getAttribute("usuarioLogueado");
        webservices.Cliente cli = port.obtenerCliente(user.getNick());
        
        if (cli == null) {
            logger.warning("No se encontró al usuario en la base de datos");
            response.sendRedirect("home");
            return;
        }
        /*
        // Ruta de la imagen
     // Ruta relativa proporcionada por el servicio
        String imagePath = port.getImagenesDTCliente(user.getNick()); // '/images/p3.jpg'

        // Concatenar la carpeta 'media' a la ruta
        String absoluteImagePath = getServletContext().getRealPath("/media" + imagePath);

        

        File imageFile = new File(absoluteImagePath); // Cambia a la ruta de tu imagen
        BufferedImage image = ImageIO.read(imageFile);

        // Convertir la imagen a un arreglo de bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        byte[] imageBytes = baos.toByteArray();

        // Codificar la imagen en Base64
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        
        */
        
        // Obtener las órdenes de compra del cliente
        List<OrdenDeCompra> ordenes = port.getOrdenesCliente(port.getNickCliente(cli));
        
        for(webservices.OrdenDeCompra orden : ordenes) {
        	System.out.print("=========  " + orden.getNumero());
        	System.out.print("=========  $" + port.imprimirPrecioTotal(cli.getNick(), orden.getNumero()));
        }
        
        // Verificar si el cliente es válido
        if (cli != null) {

            // Obtener el parámetro "nickname"
            String parametro = request.getParameter("nickname");

            if (parametro != null && port.getNickCliente(cli).equals(parametro)) {
            	//request.setAttribute("imagenEnBits", base64Image);
            	request.setAttribute("usuarioLogueado", user);
                request.setAttribute("usuario", cli);
                request.setAttribute("ordenes", ordenes);  // Pasar la lista de ordenes correctamente
                request.getRequestDispatcher("/WEB-INF/InfoPerfilCliente.jsp").forward(request, response);
                return;
            }
        }

        response.sendRedirect("home");
    }
    
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Delegar la solicitud al método doGet
        doGet(request, response);
    }
}