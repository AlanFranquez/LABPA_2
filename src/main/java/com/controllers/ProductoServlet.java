package com.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.model.Cat_Producto;
import com.model.Categoria;
import com.model.Factory;
import com.model.ISistema;
import com.model.Producto;
import com.model.Proveedor;

@WebServlet("/registrarproducto")
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
        HttpSession objSession = request.getSession();
        Proveedor prov = (Proveedor) objSession.getAttribute("usuarioLogueado");

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
            RequestDispatcher dispatcher = request.getRequestDispatcher("inicio.jsp");
            dispatcher.forward(request, response);
            return; // Asegúrate de salir después de redirigir
        }

        // Redirige al JSP de registro de productos
        request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el proveedor (usuario logueado) de la sesión
        HttpSession objSession = request.getSession();
        Proveedor prov = (Proveedor) objSession.getAttribute("usuarioLogueado");
        
        // Verificar si el proveedor es nulo
        if (prov == null) {
            System.out.println("Error: No se encontró el proveedor en la sesión.");
            request.setAttribute("error", "No se encontró el proveedor logueado.");
            request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
            return;
        }
        
        System.out.println("Proveedor logueado: " + prov.getNickname());

        // Recibiendo los datos del formulario
        String titulo = request.getParameter("titulo");
        String referenciaStr = request.getParameter("referencia");
        String descripcion = request.getParameter("descripcion");
        String especificaciones = request.getParameter("especificaciones");
        String precioStr = request.getParameter("precio");
        String stockStr = request.getParameter("stock");
        String imagenUrl = request.getParameter("imagen");
        
        System.out.println("Título del producto: " + titulo);
        System.out.println("Referencia: " + referenciaStr);

        

        // Manejo de referencia
        int referencia = 0;
        try {
            referencia = Integer.parseInt(referenciaStr);
            System.out.println("Número de referencia: " + referencia);
        } catch (NumberFormatException e) {
            System.out.println("Error: Referencia inválida");
            request.setAttribute("error", "El número de referencia debe ser un número válido.");
            request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
            return;
        }

        // Manejo del precio
        float precio = 0;
        try {
            precio = Float.parseFloat(precioStr);
            System.out.println("Precio del producto: " + precio);
        } catch (NumberFormatException e) {
            System.out.println("Error: Precio inválido");
            request.setAttribute("error", "El precio debe ser un número válido.");
            request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
            return;
        }

        // Manejo del stock
        int stock = 0;
        try {
            stock = Integer.parseInt(stockStr);
            System.out.println("Stock del producto: " + stock);
        } catch (NumberFormatException e) {
            System.out.println("Error: Stock inválido");
            request.setAttribute("error", "El stock debe ser un número válido.");
            request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
            return;
        }

        // Obtener la categoría seleccionada
        String categoriaSeleccionada = request.getParameter("categoria");
        Categoria cat = null;
        if (categoriaSeleccionada != null) {
            cat = sist.getCat(categoriaSeleccionada);
            System.out.println("Categoría seleccionada: " + categoriaSeleccionada);
        } else {
            System.out.println("Error: No se seleccionó una categoría");
            request.setAttribute("error", "Debe seleccionar una categoría.");
            request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
            return;
        }

        // Registrar producto si el precio es mayor a cero
        if (precio > 0) { 
            Producto p1 = new Producto(titulo, descripcion, precio, referencia, especificaciones, prov, stock);
            Cat_Producto catp1 = new Cat_Producto(categoriaSeleccionada);
            catp1.agregarProducto(p1);
            prov.agregarProd(p1);
            System.out.println("Producto registrado correctamente.");

            // Redirigir a la página de "Producto Agregado" después del registro exitoso
            response.sendRedirect("perfilProveedor?nickname=" + prov.getNickname());
        } else {
            // Manejo del error si el precio es menor o igual a cero
            System.out.println("Error: El precio debe ser mayor a cero.");
            request.setAttribute("error", "El precio debe ser mayor a cero.");
            request.getRequestDispatcher("/WEB-INF/RegistrarProducto.jsp").forward(request, response);
        }
    }



}
