package com.controllers;

import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import com.exceptions.UsuarioRepetidoException;
import com.model.DTFecha;
import com.model.Factory;
import com.model.ISistema;

@WebServlet(urlPatterns = {"/initServlet"}, loadOnStartup = 1)
public class InitServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
    	System.out.print("El SISTEMA INCIIO VAMO ARRIBA");
    	ISistema s = Factory.getSistema();
    	 DTFecha fecha1 = new DTFecha(1, 1, 1990);
         DTFecha fecha2 = new DTFecha(15, 6, 1985);
         DTFecha fecha3 = new DTFecha(5, 6, 1990);
         try {
        	 s.agregarCliente("Juan", "Juan123", "Perez", "Juan@gmail.com", fecha1, "123", "123");
			s.agregarCliente("Alberto", "albert1341", "Hernandez", "Ahernandez@gmail.com", fecha2, "123", "123");
			s.agregarCliente("Maria", "agusmari", "Agustina", "mariaagustina@gmail.com", fecha3, "123", "123");
		} catch (UsuarioRepetidoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

