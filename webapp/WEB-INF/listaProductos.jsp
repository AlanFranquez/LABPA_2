<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="com.model.DtProducto" %>
    <%@page import="com.model.Producto" %>
    <%@page import="java.util.Collections" %>
    <%@page import="com.model.ISistema" %>
 <%@page import="java.util.List" %>
 <%@page import="com.model.Usuario" %>
 <%@page import="java.util.HashMap"%>
 <%@page import="java.util.Collection"%>
 <%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
    
<!DOCTYPE html>
<html>
<head>


<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
<link href="media/styles/listarProducto.css" rel="stylesheet">

<title>Lista de Productos</title>

</head>

<div id="barra-nav"></div>

<div class="container">
<div class="row">
  <!-- BEGIN SEARCH RESULT -->
  <div class="col-md-12">
    <div class="grid search">
      <div class="grid-body">
        <div class="row">
          <!-- BEGIN FILTERS -->
          <div class="col-md-3">
            <h2 class="grid-title"><i class="fa fa-filter"></i> Filters</h2>
            <hr>
            
            <!-- BEGIN FILTER BY CATEGORY -->
            <h4>Por Categoria:</h4>
            <div class="checkbox">
              <label><input type="checkbox" class="icheck"> Tecnologia</label>
            </div>
            <div class="checkbox">
              <label><input type="checkbox" class="icheck"> Living</label>
            </div>
            <div class="checkbox">
              <label><input type="checkbox" class="icheck"> Bazar</label>
            </div>
            <div class="checkbox">
              <label><input type="checkbox" class="icheck"> Deporte</label>
            </div>
            <!-- END FILTER BY CATEGORY -->
            
            <div class="padding"></div>
            
            <!-- BEGIN FILTER BY PRICE -->
            <h4>Por Precio:</h4>
            Entre <div id="price1">$300</div> y <div id="price2">$800</div>
            <div class="slider-primary">
              <div class="slider slider-horizontal" style="width: 152px;"><div class="slider-track"><div class="slider-selection" style="left: 30%; width: 50%;"></div><div class="slider-handle round" style="left: 30%;"></div><div class="slider-handle round" style="left: 80%;"></div></div><div class="tooltip top hide" style="top: -30px; left: 50.1px;"><div class="tooltip-arrow"></div><div class="tooltip-inner">300 : 800</div></div><input type="text" class="slider" value="" data-slider-min="0" data-slider-max="1000" data-slider-step="1" data-slider-value="[300,800]" data-slider-tooltip="hide"></div>
            </div>
            <!-- END FILTER BY PRICE -->
          </div>
          <!-- END FILTERS -->
          <!-- BEGIN RESULT -->
          <div class="col-md-9">
            <h2><i class="fa fa-file-o"></i> Resultado</h2>
            <hr>
            <!-- BEGIN SEARCH INPUT -->
            <div class="input-group">
              <input type="text" class="form-control" value="web development">
              <span class="input-group-btn">
                <button class="btn btn-primary" type="button"><i class="fa fa-search"></i></button>
              </span>
            </div>
            <!-- END SEARCH INPUT -->
            <p>Showing all results matching "web development"</p>
            
            <div class="padding"></div>
            
			
			<section>
	    		<h2 class="comprasTitle text-center mt-5">Todos los Productos:</h2>
	    
	    		<% 
	    		ISistema sistema = (ISistema) session.getAttribute("sistema");
	    		List<Producto> listaProductos = sistema.getAllProductos();
		        List<DtProducto> listaDTProductos = new ArrayList<>();
		        
			 	 if (listaProductos == null || listaProductos.isEmpty()) { %>
		        <p class="text-center mt-4">No Existen Productos :/</p>
		    <% } else { 
		        for (Producto p : listaProductos) {
		            DtProducto dtp = p.crearDT();
		            listaDTProductos.add(dtp);
		        }
		    %>
			   <% 
			    if (listaDTProductos.isEmpty()) { %>
			        <p class="text-center mt-4">No hay productos disponibles.</p>
			    <% } %>
			    
			    <div class="row justify-content-center">
			        <% for (DtProducto dt : listaDTProductos) { %>
			            <div class="col-md-4 col-sm-6 mb-4">
			                <div class="card h-100 text-center">
			                    <div class="card-body">
			                        <% if (dt != null && dt.getImagenes() != null && !dt.getImagenes().isEmpty() && dt.getImagenes().getFirst() != null) { %>
			                            <img class="card-img-top" src="media/<%= dt.getImagenes().getFirst() %>" alt="<%= dt.getNombre() %>" style="max-height: 200px; object-fit: cover;">
			                        <% } else { %>
			                            <img class="card-img-top" src="https://upload.wikimedia.org/wikipedia/commons/a/a3/Image-not-found.png" alt="<%= dt.getNombre() %>" style="max-height: 200px; object-fit: cover;">
			                        <% } %>
			                        <h5 class="card-title mt-2"><%= dt != null ? dt.getNombre() : "Producto no encontrado" %></h5>
			                        <p class="card-text"><strong>Precio:</strong> $<%= dt != null ? dt.getPrecio() : "N/A" %></p>
			                        <p class="card-text"><strong>Número de Referencia:</strong> <%= dt != null ? dt.getNumRef() : "N/A" %></p>
			                        <a href="perfilProducto?producto=<%= dt != null ? dt.getNumRef() : "" %>" class="btn btn-primary">Ver Detalles</a>
			                        <form action="agregarAlCarrito" method="post" style="display: inline-block;">
                        				<input type="hidden" name="numRef" value="<%= dt != null ? dt.getNumRef() : "" %>">
                        				<input type="number" name="cantidad" min="1" max="10" value="1" style="width: 60px;">
                        				<button type="submit" class="btn btn-primary">Agregar al Carrito</button>
                    				</form>
                    				<% } %>
			                    </div>
			                </div>
			            </div>
			        <% } %>
			    </div>
			    
			   
			</section>
    
          </div>
          <!-- END RESULT -->
        </div>
      </div>
    </div>
  </div>
  <!-- END SEARCH RESULT -->
</div>
</div>


<!-- 
	parte de abajo
 -->

 <div class="part-final d-flex justify-content-center align-items-center">

	<p class="text-center">Todos los derechos reservados, 2024. <br> Laboratorio PA.</p>

</div>

	
	
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
	<script src="RegistrarUsuario1.js"></script>

  <script>
    // Cargar el contenido de BarraNav.html usando fetch
    fetch('/WEB-INF/template/BarraNavSinReg.html')
        .then(response => response.text())  // Convertir la respuesta a texto
        .then(data => {
            // Insertar el contenido en el div con el id 'barra-nav'
            document.getElementById('/WEB-INF/template/barra-nav').innerHTML = data;
        })
        .catch(error => console.error('Error al cargar BarraNav:', error));
</script>
</body>
</html>