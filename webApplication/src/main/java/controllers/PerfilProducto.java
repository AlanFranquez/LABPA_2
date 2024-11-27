package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webservices.Publicador;
import webservices.PublicadorService;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;


import webservices.PublicadorService;
import webservices.CatProducto;
import webservices.DtCliente;
import webservices.OrdenDeCompra;
import webservices.Publicador;

/**
 * Servlet implementation class PerfilProducto
 */
@WebServlet("/perfilProducto")
public class PerfilProducto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PublicadorService p = new PublicadorService();
    Publicador port = p.getPublicadorPort();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PerfilProducto() {
        super();
        // TODO Auto-generated constructor stub
    }

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Cambiado a false para no crear una nueva sesión

        String userAgent = request.getHeader("User-Agent");
        
        PublicadorService p = new PublicadorService();
        Publicador port = p.getPublicadorPort();
        
        if((userAgent != null) && (
                userAgent.contains("Mobile") || 
                userAgent.contains("Android") || 
                userAgent.contains("iPhone") || 
                userAgent.contains("iPad") || 
                userAgent.contains("Windows Phone") || 
                userAgent.contains("BlackBerry")
            )) {
        	request.getRequestDispatcher("perfilProductoMOBILE").forward(request, response);
        	return;
        } 
        
        if (session == null) {
            response.sendRedirect("formlogin");
            return;
        }

        webservices.Usuario usuarioLogueado = (webservices.Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            response.sendRedirect("formlogin");
            return;
        }
        webservices.Usuario user = (webservices.Usuario) usuarioLogueado;
        request.setAttribute("usuario", user);
        request.setAttribute("nickusuario", user.getNick());
        

            String parametro = request.getParameter("producto");
            if (parametro == null) {
                response.sendRedirect("perfilCliente");
                return;
            }
            
            System.out.print("VALOR A CONVERTIR -> " + parametro);
            int paramNumero = Integer.parseInt(parametro);
            
           
            
            webservices.Producto producto =  port.obtenerProducto(paramNumero);
            List<webservices.Comentario> coms = port.listarComentarios(paramNumero);
            
            
            for(webservices.Comentario c: coms) {
            	
            	System.out.println("COMENTARIOS");
            	System.out.println(c.getTexto());
            }
            
            
           
            
            request.setAttribute("coms", coms);
            request.setAttribute("prod", producto);
            request.getRequestDispatcher("/WEB-INF/PerfilProducto.jsp").forward(request, response);
   


    }


	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
