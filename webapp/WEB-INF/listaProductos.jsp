<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="com.model.DtProducto" %>
    <%@page import="com.model.Producto" %>
    <%@page import="java.util.Collections" %>
 <%@page import="java.util.List" %>
 <%@page import="com.model.Usuario" %>
    
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
            
            
            <!-- BEGIN TABLE RESULT -->
            <div class="table-responsive">
              <table class="table table-hover">
                <a href="InfoProducto.html">
                <button>
                  <tbody><tr>
                  <td class="number text-center">1</td>
                  <td class="image"><img src="celular.jfif" alt=""></td>
                  <td class="product"><strong>Celular</strong><br>Telefono ultimo modelo, exelente calidad.</td>
                  <td class="rate text-right"><span><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star-half-o"></i></span></td>
                  <td class="price text-right">$750</td>
                   <td class="text-right">
                    <form action="agregarAlCarrito" method="post">
                        <input type="hidden" name="numRef" value="1">
                        <input type="number" name="cantidad" min="1" max="10" value="1" style="width: 60px;">
                        <button type="submit" class="btn btn-primary">Agregar</button>
                    </form>
                </td>
                </tr>
              </button>
              </a>
                <tr>
                  <td class="number text-center">2</td>
                  <td class="image"><img src="MaquinaDeEscribir.jpg" alt=""></td>
                  <td class="product"><strong>Maquina de Escribir</strong><br>Poratil Mac, ideal para programar.</td>
                  <td class="rate text-right"><span><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star-o"></i><i class="fa fa-star-o"></i></span></td>
                  <td class="price text-right">$1,050</td>
                   <td class="text-right">
                    <form action="agregarAlCarrito" method="post">
                        <input type="hidden" name="numRef" value="2">
                        <input type="number" name="cantidad" min="1" max="10" value="1" style="width: 60px;">
                        <button type="submit" class="btn btn-primary">Agregar</button>
                    </form>
                </td>
                </tr>
                <tr>
                  <td class="number text-center">3</td>
                  <td class="image"><img src="MonaLisa.jpg" alt=""></td>
                  <td class="product"><strong>Replica Monalisa</strong><br>Cuadro infantil para colorear.</td>
                  <td class="rate text-right"><span><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star-half-o"></i><i class="fa fa-star-o"></i></span></td>
                  <td class="price text-right">$990</td>
                   <td class="text-right">
                    <form action="agregarAlCarrito" method="post">
                        <input type="hidden" name="numRef" value="3">
                        <input type="number" name="cantidad" min="1" max="10" value="1" style="width: 60px;">
                        <button type="submit" class="btn btn-primary">Agregar</button>
                    </form>
                </td>
                </tr>
                <tr>
                  <td class="number text-center">4</td>
                  <td class="image"><img src="celular.jfif" alt=""></td>
                  <td class="product"><strong>Celular</strong><br>Telefono ultimo modelo, exelente calidad.</td>
                  <td class="rate text-right"><span><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star-o"></i></span></td>
                  <td class="price text-right">$5,600</td>
                   <td class="text-right">
                    <form action="agregarAlCarrito" method="post">
                        <input type="hidden" name="numRef" value="4">
                        <input type="number" name="cantidad" min="1" max="10" value="1" style="width: 60px;">
                        <button type="submit" class="btn btn-primary">Agregar</button>
                    </form>
                </td>
                </tr>
                <tr>
                  <td class="number text-center">5</td>
                  <td class="image"><img src="t1.jpg" alt=""></td>
                  <td class="product"><strong>Juego de Muebles</strong><br>Muebles comodos de living.</td>
                  <td class="rate text-right"><span><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i></span></td>
                  <td class="price text-right">$6,000</td>
                   <td class="text-right">
                    <form action="agregarAlCarrito" method="post">
                        <input type="hidden" name="numRef" value="5">
                        <input type="number" name="cantidad" min="1" max="10" value="1" style="width: 60px;">
                        <button type="submit" class="btn btn-primary">Agregar</button>
                    </form>
                </td>
                </tr>
                <tr>
                  <td class="number text-center">6</td>
                  <td class="image"><img src="t2.jpg" alt=""></td>
                  <td class="product"><strong>Harry Potter colecccion</strong><br>Libros de Harry Potter del 1 al 7.</td>
                  <td class="rate text-right"><span><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star-half-o"></i></span></td>
                  <td class="price text-right">$870</td>
                   <td class="text-right">
                    <form action="agregarAlCarrito" method="post">
                        <input type="hidden" name="numRef" value="6">
                        <input type="number" name="cantidad" min="1" max="10" value="1" style="width: 60px;">
                        <button type="submit" class="btn btn-primary">Agregar</button>
                    </form>
                </td>
                </tr>
                <tr>
                  <td class="number text-center">7</td>
                  <td class="image"><img src="Ferrari.jfif" alt=""></td>
                  <td class="product"><strong>Ferrari</strong><br>Auto clasico de coleccion.</td>
                  <td class="rate text-right"><span><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star-o"></i><i class="fa fa-star-o"></i><i class="fa fa-star-o"></i></span></td>
                  <td class="price text-right">$100,000</td>
                   <td class="text-right">
                    <form action="agregarAlCarrito" method="post">
                        <input type="hidden" name="numRef" value="7">
                        <input type="number" name="cantidad" min="1" max="10" value="1" style="width: 60px;">
                        <button type="submit" class="btn btn-primary">Agregar</button>
                    </form>
                </td>
                </tr>
                <tr>
                  <td class="number text-center">8</td>
                  <td class="image"><img src="t3.jpg" alt=""></td>
                  <td class="product"><strong>Libreria</strong><br>Libreria ideal para guardar libros de Harry Potter del 1 al 7.</td>
                  <td class="rate text-right"><span><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star-half-o"></i></span></td>
                  <td class="price text-right">$1,550</td>
                   <td class="text-right">
                    <form action="agregarAlCarrito" method="post">
                        <input type="hidden" name="numRef" value="8">
                        <input type="number" name="cantidad" min="1" max="10" value="1" style="width: 60px;">
                        <button type="submit" class="btn btn-primary">Agregar</button>
                    </form>
                </td>
                </tr>
              </tbody></table>
            </div>
            <!-- END TABLE RESULT -->
            
            <!-- BEGIN PAGINATION -->
           <!-- <ul class="pagination">
              <li class="disabled"><a href="#">«</a></li>
              <li class="active"><a href="#">1</a></li>
              <li><a href="#">2</a></li>
              <li><a href="#">3</a></li>
              <li><a href="#">4</a></li>
              <li><a href="#">5</a></li>
              <li><a href="#">»</a></li>
            </ul>                                           -->
            <!-- END PAGINATION -->
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