package com.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.exceptions.UsuarioException;
import com.exceptions.UsuarioRepetidoException;
import com.model.Cliente;
import com.model.DTFecha;
import com.model.EstadoSesion;
import com.model.Factory;
import com.model.ISistema;
import com.model.Proveedor;
import com.model.Usuario;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;





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
    	 HttpSession session = request.getSession();
         String nick = (String) session.getAttribute("nick");
         String correo = (String) session.getAttribute("correo");
         
         System.out.print(nick);
         if(nick == null) {
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
        
        Part img = request.getPart("imagen");
        String fileName = null;
        if (img != null && img.getSize() > 0) { 
            String uploadDir = getServletContext().getRealPath("") + File.separator + "media";
            File uploads = new File(uploadDir);
            if (!uploads.exists()) uploads.mkdirs();

            fileName = img.getSubmittedFileName();
            File file = new File(uploads, fileName);
            img.write(file.getAbsolutePath());
        }

      


        String nombreCompania = request.getParameter("nombreCompania");
        String sitioWeb = request.getParameter("sitioWeb");

        // Validar campos requeridos
        if (nombre == null || nombre.isEmpty() || 
            apellido == null || apellido.isEmpty() || 
            fechaNacimiento == null || fechaNacimiento.isEmpty() || 
            contraseña == null || contraseña.isEmpty() || 
            contraseña2 == null || contraseña2.isEmpty() || 
            tipoUsuario == null || tipoUsuario.isEmpty() || 
            (tipoUsuario.equals("proveedor") && (nombreCompania == null || nombreCompania.isEmpty() || 
            sitioWeb == null || sitioWeb.isEmpty()))) {

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
        DTFecha fechaNueva = new DTFecha(dia, mes, anio);

        // Validar contraseñas
        if (!contraseña.equals(contraseña2)) {
            request.setAttribute("errorMsg", "Las contraseñas no coinciden");
            System.out.println("Contraseñas diferentes");
            request.getRequestDispatcher("/WEB-INF/RegistrarUsuario2.jsp").forward(request, response);
            return;
        }

        EstadoSesion nuevoEstado = EstadoSesion.LOGIN_INCORRECTO;
        Usuario usr = null;

        try {
            if (tipoUsuario.equals("proveedor")) {
                Proveedor prov = new Proveedor(nombre, nick, apellido, correo, fechaNueva, nombreCompania, sitioWeb, contraseña);
                sist.agregarProveedor(nick, correo, nombre, apellido, fechaNueva, nombreCompania, sitioWeb, contraseña, contraseña2);
                usr = prov;
                System.out.println("Registrado Proveedor");
            } else {
                Cliente cliente = new Cliente(nombre, nick, apellido, correo, fechaNueva, contraseña);
                sist.agregarCliente(nombre, nick, apellido, correo, fechaNueva, contraseña, contraseña2);
                usr = cliente;
            }
            if(fileName != null) {
            	
            	sist.agregarImagenUsuario(usr.getNick(), fileName);
            	usr.setImagen(fileName);
            }
            
            System.out.print(sist.getUsuario(nick).getImagen());
            
            nuevoEstado = EstadoSesion.LOGIN_CORRECTO;
            objSession.setAttribute("usuarioLogueado", sist.getUsuario(nick));
            objSession.setAttribute("estado", nuevoEstado); 

            response.sendRedirect("home");
            
        } catch (UsuarioRepetidoException e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "El usuario ya está registrado. Intenta con otro nombre.");
            System.out.println("Usuario ya registrado");
            request.getRequestDispatcher("/WEB-INF/RegistrarUsuario2.jsp").forward(request, response);
            return;
        }
    }



}

