package com.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.model.Cliente;
import com.model.DTCliente;
import com.model.DTOrdenDeCompra;

/**
 * Servlet implementation class PerfilOrden
 */
@WebServlet("/perfilOrden")
public class PerfilOrden extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PerfilOrden() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nickname = request.getParameter("nickname");
        String ordenParam = request.getParameter("orden");

        // Obtener el cliente de la sesión
        HttpSession sess = request.getSession(false); // No crear una nueva sesión
        if (sess == null || sess.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("formlogin");
            return;
        }

        Cliente cliente = (Cliente) sess.getAttribute("usuarioLogueado");

        // Comprobar que el parámetro de la orden no sea nulo y convertirlo a entero
        if (ordenParam != null && !ordenParam.isEmpty()) {
            int numeroOrden;
            try {
                numeroOrden = Integer.parseInt(ordenParam);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Número de orden inválido.");
                return;
            }

            // Obtener la orden específica
            DTOrdenDeCompra orden = cliente.mostrarCompras(numeroOrden);
            if (orden != null) {
                DTCliente dtcli = (DTCliente) cliente.crearDt();
                request.setAttribute("ordencompra", orden);
                request.setAttribute("usuario", dtcli);
                request.getRequestDispatcher("/WEB-INF/DetalleOrden.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Orden no encontrada.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el número de orden.");
        }
    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Delegar la solicitud POST a doGet
        doGet(request, response);
    }
}
