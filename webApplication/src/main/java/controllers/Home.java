package controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.market.svcentral.EmailService;
import com.market.svcentral.Factory;
import com.market.svcentral.ISistema;
import com.market.svcentral.Producto;
import com.market.svcentral.Usuario;

@WebServlet("/home")
public class Home extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Home() {
        super();
    }
    
    private final EmailService emailService = new EmailService();
    
    private ISistema sist;

    @Override
    public void init() throws ServletException {
        try {
            sist = Factory.getSistema();
        } catch (Exception e) {
            throw new ServletException("No se pudo inicializar ISistema", e);
        }
    }

  

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        // Obtener el correo del destinatario desde el parámetro
        String recipientEmail = "maria.vairo@estudiantes.utec.edu.uy";

        try {
            if (recipientEmail != null && !recipientEmail.isEmpty()) {
                // Enviar el correo de bienvenida
                emailService.sendWelcomeEmail(recipientEmail);
                System.out.println("Correo de bienvenida enviado a " + recipientEmail);
            } else {
                System.out.println("Error: No se proporcionó una dirección de correo válida.");
            }
        } catch (Exception e) {
            System.out.println("Error al intentar enviar el correo de bienvenida: " + e.getMessage());
//            e.printStackTrace();
        }
        
        List<Producto> productos = sist.getAllProductos();

        
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
        	request.setAttribute("prods", productos);
            request.getRequestDispatcher("/WEB-INF/inicioNoLogueado.jsp").forward(request, response);
            return;
        }

        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        
        if (productos != null) {
        	request.setAttribute("prods", productos);
        }
        
        

        if (usuarioLogueado != null) {
            request.setAttribute("usuario", usuarioLogueado); 
            request.setAttribute("estado", "logueado");
            request.getRequestDispatcher("/WEB-INF/inicio.jsp").forward(request, response);
        } else {
            response.sendRedirect("formlogin");
            request.setAttribute("estado", "noLogueado");
        }
    }
}
