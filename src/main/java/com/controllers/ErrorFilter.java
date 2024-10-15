package com.controllers;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*") // Intercepta todas las solicitudes
public class ErrorFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialización del filtro, si es necesario
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        System.out.println("Interceptando solicitud: " + httpRequest.getRequestURI());

        // Procesa la solicitud
        chain.doFilter(request, response);

        // Verifica si la respuesta es 404
        if (httpResponse.getStatus() == HttpServletResponse.SC_NOT_FOUND) {
            System.out.println("Error 404 detectado. Redirigiendo a /error404");
            httpResponse.sendRedirect("Error404");
        }
    }

    @Override
    public void destroy() {
        // Limpiar recursos, si es necesario
    }
}
