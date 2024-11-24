package controllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import webservices.UsuarioRepetidoException_Exception;

@WebServlet("/registrarusuario2")
@MultipartConfig
public class RegistrarUsuarios2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistrarUsuarios2() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws ServletException {

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession objSession = request.getSession();
		String nick = (String) objSession.getAttribute("nick");
		String correo = (String) objSession.getAttribute("correo");

		String nombre = request.getParameter("nombre");
		String apellido = request.getParameter("apellido");
		String fechaNacimiento = request.getParameter("nacimiento");
		String contraseña = request.getParameter("password");
		String contraseña2 = request.getParameter("confirmPassword");
		String tipoUsuario = request.getParameter("tipoUsuario");

		Part img = request.getPart("imagen");
		
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
		
		for(Byte b : imageBytes) {
			System.out.print(b + "");
		}
		

		PublicadorService p = new PublicadorService();
		Publicador port = p.getPublicadorPort();

		String nombreCompania = request.getParameter("nombreCompania");
		String sitioWeb = request.getParameter("sitioWeb");

		// Validar campos requeridos
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

		// Separar la fecha
		String[] partesFecha = fechaNacimiento.split("-");
		int anio = Integer.parseInt(partesFecha[0]);
		int mes = Integer.parseInt(partesFecha[1]);
		int dia = Integer.parseInt(partesFecha[2]);
		webservices.DtFecha fechaNueva = port.nuevaFecha(dia, mes, anio);

		// Validar contraseñas
		if (!contraseña.equals(contraseña2)) {
			request.setAttribute("errorMsg", "Las contraseñas no coinciden");
			System.out.println("Contraseñas diferentes");
			request.getRequestDispatcher("/WEB-INF/RegistrarUsuario2.jsp").forward(request, response);
			return;
		}

		String nuevoEstado = "nologueado";
		webservices.Usuario usr = null;

		try {
			if (tipoUsuario.equals("proveedor")) {
				port.agregarProveedor(nick, correo, nombre, apellido, fechaNueva, nombreCompania, sitioWeb, contraseña,
						contraseña2);
				System.out.println("Registrado Proveedor");
				usr = port.obtenerProveedor(nick);
			} else {
				port.agregarCliente(nombre, nick, apellido, correo, fechaNueva, contraseña, contraseña2);
				
				System.out.print("Registrado Cliente");
				usr = port.obtenerCliente(nick);
			}
			if (fileName != null) {

				port.agregarImagenUsuario(usr.getNick(), imageBytes);
				//usr.setImagen(fileName);
			}

			

			nuevoEstado = "logueado";
			objSession.setAttribute("usuarioLogueado", port.obtenerUsuario(nick));
			objSession.setAttribute("estado", nuevoEstado);

			response.sendRedirect("home");

		} catch (UsuarioRepetidoException_Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", "El usuario ya está registrado. Intenta con otro nombre.");
			System.out.println("Usuario ya registrado");
			request.getRequestDispatcher("/WEB-INF/RegistrarUsuario2.jsp").forward(request, response);
			return;
		}
	}

}
