package controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import webservices.Publicador;
import webservices.PublicadorService;
import webservices.Usuario;

@WebServlet("/registrarusuario2")
@MultipartConfig
public class RegistrarUsuarios2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PublicadorService p = new PublicadorService();
    Publicador port = p.getPublicadorPort();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrarUsuarios2() {
        super();
    }

    @Override
    public void init() throws ServletException {
    }
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 HttpSession session = request.getSession();
         String nick = (String) session.getAttribute("nick");
         
         System.out.print(nick);
         if (nick == null) {
        	 response.sendRedirect("registrarusuario1");
        	 return;
         }
        
    	request.getRequestDispatcher("/WEB-INF/RegistrarUsuario2.jsp").forward(request, response);
    	System.out.println("Redirigiendo reg2 inicio servlet");
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession objSession = request.getSession();
		String nick = (String) objSession.getAttribute("nick");
		String correo = (String) objSession.getAttribute("correo");

		String nombre = request.getParameter("nombre");
		String apellido = request.getParameter("apellido");
		String fechaNacimiento = request.getParameter("nacimiento");
		String contraseña = request.getParameter("password");
		String contraseña2 = request.getParameter("confirmPassword");
		String tipoUsuario = request.getParameter("tipoUsuario");
		String nombreCompania = request.getParameter("nombreCompania");
		String sitioWeb = request.getParameter("sitioWeb");

		Part img = request.getPart("imagen");
		System.out.print("CONTENIDO DE LA IMAGEN " + img.getContentType());
		String fileName = null;
		byte[] imageBytes = null;
		if (img != null && img.getSize() > 0) {
			String uploadDir = getServletContext().getRealPath("") + File.separator + "media";
			File uploads = new File(uploadDir);
			if (!uploads.exists()) {
				uploads.mkdirs();
			}
			fileName = img.getSubmittedFileName();
			File file = new File(uploads, fileName);
			img.write(file.getAbsolutePath());
			
			imageBytes = Files.readAllBytes(file.toPath());
		}
		
		System.out.print("FECHA NACIMIENTO --> " + fechaNacimiento);
		//Validar campos requeridos
		if (nombre == null || nombre.isEmpty() || apellido == null || apellido.isEmpty() || fechaNacimiento == null
				|| fechaNacimiento.isEmpty() || contraseña == null || contraseña.isEmpty() || contraseña2 == null
				|| contraseña2.isEmpty() || tipoUsuario == null || tipoUsuario.isEmpty()
				|| (tipoUsuario.equals("proveedor") && (nombreCompania == null || nombreCompania.isEmpty()
						|| sitioWeb == null || sitioWeb.isEmpty()))) {

			request.setAttribute("errorMsg", "Todos los campos marcados con * son obligatorios");
			System.out.println("No se completaron todos los campos");
			request.getRequestDispatcher("/WEB-INF/RegistrarUsuario2.jsp").forward(request, response);
			return;
		}
        
        // Validar contraseñas
        if (!contraseña.equals(contraseña2)) {
            request.setAttribute("errorMsg", "Las contraseñas no coinciden");
            System.out.println("Contraseñas diferentes");
            request.getRequestDispatcher("/WEB-INF/RegistrarUsuario2.jsp").forward(request, response);
            return;
        }

        objSession.setAttribute("estado", "nologueado"); 
        Usuario usr = null;

        try {
            if (tipoUsuario.equals("proveedor")) {
            	usr = port.agregarProveedor2(nick, correo, nombre, apellido, fechaNacimiento, nombreCompania, sitioWeb, contraseña, contraseña2);
                System.out.println("Registrado Proveedor");
            } else {
            	usr = port.agregarCliente2(nombre, nick, apellido, correo, fechaNacimiento, contraseña, contraseña2);
            	System.out.println("Registrado Cliente");
            }
            if (fileName != null) {
            	port.agregarImagenUsuario(nick, imageBytes);
            }
            
            objSession.setAttribute("usuarioLogueado", usr);
            objSession.setAttribute("estado", "logueado"); 

            response.sendRedirect("home");
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "El usuario ya está registrado. Intenta con otro nombre.");
            System.out.println("Usuario ya registrado");
            request.getRequestDispatcher("/WEB-INF/RegistrarUsuario2.jsp").forward(request, response);
            return;
        }
    }
}