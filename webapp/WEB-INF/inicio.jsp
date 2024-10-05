<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <link rel="stylesheet" href="media/styles/index.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
    
    <jsp:include page="/WEB-INF/template/barraNav.jsp" />
    
    <main class="container d-flex justify-content-center align-items-center vh-90">
        <div class="row w-100">
            <div class="col-md-6 d-flex justify-content-center align-items-center">
                <img src="media/images/icono.svg" alt="Icono.png" style="width: 200px; height: 200px">
            </div>

            <div class="col-md-6 d-flex flex-column justify-content-center align-items-start">
                <h2>Compra lo que quieras</h2>
                <p class="sub">Estamos para ayudarte!</p>
                <p class="textoPrincipal">Lorem ipsum dolor sit amet consectetur adipisicing elit. Nam consectetur sequi enim atque quaerat sed laudantium repudiandae id, asperiores doloribus molestias totam necessitatibus! Quam perspiciatis officia alias hic repellendus dolorem.</p>
                <button class="botonIndex">
                    <a href="./listarProductos.html">
                        Empezar
                    </a>
                </button>
            </div>
        </div>
    </main>

    <section style="background-color: #eee;" class="mt-5">
        <div class="container py-5">
          <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6 col-xl-4">
              <div class="card" style="border-radius: 15px;">
                <div class="bg-image hover-overlay ripple ripple-surface ripple-surface-light"
                  data-mdb-ripple-color="light">
                  <img src="https://mdbcdn.b-cdn.net/img/Photos/Horizontal/E-commerce/Products/12.webp"
                    style="border-top-left-radius: 15px; border-top-right-radius: 15px;" class="img-fluid"
                    alt="Laptop" />
                  <a href="#!">
                    <div class="mask"></div>
                  </a>
                </div>
                <div class="card-body pb-0">
                  <div class="d-flex justify-content-between">
                    <div>
                      <p><a href="#!" class="text-dark">Dell Xtreme 270</a></p>
                      <p class="small text-muted">Laptops</p>
                    </div>
                    <div>
                      <div class="d-flex flex-row justify-content-end mt-1 mb-4 text-danger">
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                        <i class="fas fa-star"></i>
                      </div>
                    </div>
                  </div>
                </div>
                <hr class="my-0" />
                <div class="card-body pb-0">
                  <div class="d-flex justify-content-between">
                    <p><a href="#!" class="text-dark">$3,999</a></p>
                    <p class="text-dark">#### 8787</p>
                  </div>
                  <p class="small text-muted">VISA Platinum</p>
                </div>
                <hr class="my-0" />
                <div class="card-body">
                  <a href="InfoProducto.html" class="d-flex justify-content-between align-items-center pb-2 mb-1">
                    <button type="button" class="btn btn-primary">Añadir al Carrito</button>
                  </a>
                </div>
              </div>
            </div>

            <div class="col-md-8 col-lg-6 col-xl-4">
                <div class="card" style="border-radius: 15px;">
                  <div class="bg-image hover-overlay ripple ripple-surface ripple-surface-light"
                    data-mdb-ripple-color="light">
                    <img src="https://mdbcdn.b-cdn.net/img/Photos/Horizontal/E-commerce/Products/12.webp"
                      style="border-top-left-radius: 15px; border-top-right-radius: 15px;" class="img-fluid"
                      alt="Laptop" />
                    <a href="#!">
                      <div class="mask"></div>
                    </a>
                  </div>
                  <div class="card-body pb-0">
                    <div class="d-flex justify-content-between">
                      <div>
                        <p><a href="#!" class="text-dark">Dell Xtreme 270</a></p>
                        <p class="small text-muted">Laptops</p>
                      </div>
                      <div>
                        <div class="d-flex flex-row justify-content-end mt-1 mb-4 text-danger">
                          <i class="fas fa-star"></i>
                          <i class="fas fa-star"></i>
                          <i class="fas fa-star"></i>
                          <i class="fas fa-star"></i>
                        </div>
                      </div>
                    </div>
                  </div>
                  <hr class="my-0" />
                  <div class="card-body pb-0">
                    <div class="d-flex justify-content-between">
                      <p><a href="#!" class="text-dark">$3,999</a></p>
                      <p class="text-dark">#### 8787</p>
                    </div>
                    <p class="small text-muted">VISA Platinum</p>
                  </div>
                  <hr class="my-0" />
                  <div class="card-body">
                    <a href="InfoProducto.html" class="d-flex justify-content-between align-items-center pb-2 mb-1">
                      <button type="button" class="btn btn-primary">Añadir al Carrito</button>
                    </a>
                  </div>
                </div>
              </div>

              <div class="col-md-8 col-lg-6 col-xl-4">
                <div class="card" style="border-radius: 15px;">
                  <div class="bg-image hover-overlay ripple ripple-surface ripple-surface-light"
                    data-mdb-ripple-color="light">
                    <img src="https://mdbcdn.b-cdn.net/img/Photos/Horizontal/E-commerce/Products/12.webp"
                      style="border-top-left-radius: 15px; border-top-right-radius: 15px;" class="img-fluid"
                      alt="Laptop" />
                    <a href="#!">
                      <div class="mask"></div>
                    </a>
                  </div>
                  <div class="card-body pb-0">
                    <div class="d-flex justify-content-between">
                      <div>
                        <p><a href="#!" class="text-dark">Dell Xtreme 270</a></p>
                        <p class="small text-muted">Laptops</p>
                      </div>
                      <div>
                        <div class="d-flex flex-row justify-content-end mt-1 mb-4 text-danger">
                          <i class="fas fa-star"></i>
                          <i class="fas fa-star"></i>
                          <i class="fas fa-star"></i>
                          <i class="fas fa-star"></i>
                        </div>
                      </div>
                    </div>
                  </div>
                  <hr class="my-0" />
                  <div class="card-body pb-0">
                    <div class="d-flex justify-content-between">
                      <p><a href="#!" class="text-dark">$3,999</a></p>
                      <p class="text-dark">#### 8787</p>
                    </div>
                    <p class="small text-muted">VISA Platinum</p>
                  </div>
                  <hr class="my-0" />
                  <div class="card-body">
                    <a href="InfoProducto.html" class="d-flex justify-content-between align-items-center pb-2 mb-1">
                      <button type="button" class="btn btn-primary">Añadir al Carrito</button>
                    </a>
                  </div>
                </div>
              </div>
          </div>
        </div>
      </section>


    <div class="part-final d-flex justify-content-center align-items-center">

		<p class="text-center">Todos los derechos reservados, 2024. <br> Laboratorio PA.</p>

	</div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
<script src="../media/js/iniciarSesion.js"></script>
</html>