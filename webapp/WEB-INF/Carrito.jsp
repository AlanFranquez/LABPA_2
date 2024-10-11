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
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
	<link href="media/styles/RegistrarUsuario.css" rel="stylesheet">
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
	<link href="media/styles/Carrito.css" rel="stylesheet">
	<title>Carrito de Compras</title>
</head>
<body>
	<div id="barra-nav"></div>
	
    <section class="mt-3 h-100">
        <div class="container h-100 py-5">
          <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-10">
      
              <div class="d-flex justify-content-between align-items-center mb-4">
                <h3 class="fw-normal mb-0">Carrito de Compras</h3>
              </div>
      
              <div class="card rounded-3 mb-4 contenedorProductos">
                <div class="card-body p-4">
                  <div class="row d-flex justify-content-between align-items-center">
                    <div class="col-md-2 col-lg-2 col-xl-2">
                      <img
                        src="https://http2.mlstatic.com/D_NQ_NP_674984-MLU73424458627_122023-O.webp"
                        class="img-fluid rounded-3" alt="Cotton T-shirt">
                    </div>
                    <div class="col-md-3 col-lg-3 col-xl-3">
                      <p class="lead fw-normal mb-2 text-white">Basic T-shirt</p>
                      <p class="text-white font-weight-bold"><span >Size: </span class="text-white">M <span class="text-white font-weight-bold">Color: </span class="text-white"> Grey</p>
                    </div>
                    <div class="col-md-3 col-lg-3 col-xl-2 d-flex">
                     
      
                      <input id="form1" min="0" name="quantity" value="2" type="number"
                        class="form-control form-control-sm" />
      
                    </div>
                    <div class="col-md-3 col-lg-2 col-xl-2 offset-lg-1">
                      <h5 class="mb-0 text-white">$499.00</h5>
                    </div>
                    <div class="col-md-1 col-lg-1 col-xl-1 text-end">
                        <button class="btn botonProducto">x</button>
                      </div>
                  </div>
                </div>
              </div>

              <div class="card rounded-3 mb-4 contenedorProductos">
                <div class="card-body p-4">
                  <div class="row d-flex justify-content-between align-items-center">
                    <div class="col-md-2 col-lg-2 col-xl-2">
                      <img
                        src="https://http2.mlstatic.com/D_NQ_NP_727890-MLU74071986708_012024-O.webp"
                        class="img-fluid rounded-3" alt="Cotton T-shirt">
                    </div>
                    <div class="col-md-3 col-lg-3 col-xl-3">
                      <p class="lead fw-normal mb-2 text-white">Basic T-shirt</p>
                      <p class="text-white font-weight-bold"><span >Size: </span class="text-white">M <span class="text-white font-weight-bold">Color: </span class="text-white"> Grey</p>
                    </div>
                    <div class="col-md-3 col-lg-3 col-xl-2 d-flex">
                      
      
                      <input id="form1" min="0" name="quantity" value="2" type="number"
                        class="form-control form-control-sm" />
      
                     
                    </div>
                    <div class="col-md-3 col-lg-2 col-xl-2 offset-lg-1">
                      <h5 class="mb-0 text-white">$499.00</h5>
                    </div>
                    <div class="col-md-1 col-lg-1 col-xl-1 text-end">
                        <button class="btn botonProducto">x</button>
                      </div>
                  </div>
                </div>
              </div>

              <div class="card rounded-3 mb-4 contenedorProductos">
                <div class="card-body p-4">
                  <div class="row d-flex justify-content-between align-items-center">
                    <div class="col-md-2 col-lg-2 col-xl-2">
                      <img
                        src="https://media.istockphoto.com/id/1395191574/photo/black-led-tv-television-screen-blank-isolated.jpg?s=612x612&w=0&k=20&c=ps14JZJh0ebkINcbQyHFsR1J5EC7ozkj_WO7Fh_9IOI="
                        class="img-fluid rounded-3" alt="Cotton T-shirt">
                    </div>
                    <div class="col-md-3 col-lg-3 col-xl-3">
                      <p class="lead fw-normal mb-2 text-white">Basic T-shirt</p>
                      <p class="text-white font-weight-bold"><span >Size: </span class="text-white">M <span class="text-white font-weight-bold">Color: </span class="text-white"> Grey</p>
                    </div>
                    <div class="col-md-3 col-lg-3 col-xl-2 d-flex">
                      
      
                      <input id="form1" min="0" name="quantity" value="2" type="number"
                        class="form-control form-control-sm" />
      
                      
                    </div>
                    <div class="col-md-3 col-lg-2 col-xl-2 offset-lg-1">
                      <h5 class="mb-0 text-white">$499.00</h5>
                    </div>
                    <div class="col-md-1 col-lg-1 col-xl-1 text-end">
                        <button class="btn botonProducto">x</button>
                      </div>
                  </div>
                </div>
              </div>
			  
			  <form action="crearOrden" method="post">
			      <button type="submit" class="btn hacerOrden">Realizar Orden 1</button>
			  </form>
      
      
                  <button  type="button" data-mdb-button-init data-mdb-ripple-init class="btn hacerOrden">Realizar Orden 2</button>
      
            </div>
          </div>
        </div>
      </section>


      <div class="part-final d-flex justify-content-center align-items-center">

        <p class="text-center">Todos los derechos reservados, 2024. <br> Laboratorio PA.</p>
    
    </div>
	
    <script>
      // Cargar el contenido de BarraNavSinReg.html usando fetch
      fetch('/WEB-INF/template/BarraNavSinReg.html')
          .then(response => response.text())  // Convertir la respuesta a texto
          .then(data => {
              // Insertar el contenido en el div con el id 'barra-nav'
              document.getElementById('/WEB-INF/template/barra-nav').innerHTML = data;
          })
          .catch(error => console.error('Error al cargar BarraNav:', error));
    </script>
	
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>