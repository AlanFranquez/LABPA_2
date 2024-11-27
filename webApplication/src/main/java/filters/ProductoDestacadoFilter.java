package filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webservices.Producto;
import webservices.Publicador;
import webservices.PublicadorService;

import java.io.IOException;
import java.util.List;

import javax.sound.sampled.Port;

@WebFilter("/PaginaExito")
public class ProductoDestacadoFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;
	PublicadorService p = new PublicadorService();
    Publicador port = p.getPublicadorPort();
	public ProductoDestacadoFilter() {
        super();
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        // Cleanup (if any) when the filter is destroyed.
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	  HttpServletRequest httpRequest = (HttpServletRequest) request;
          HttpServletResponse httpResponse = (HttpServletResponse) response;

          System.out.println("Entrando al filtro de la página de éxito");

          HttpSession session = httpRequest.getSession();
          
          if (session.getAttribute("usuarioLogueado") == null) {
              httpResponse.sendRedirect("home");  
              return;
          }

          List<Producto> productosComprados = (List<Producto>) session.getAttribute("productosComprados");

          if (productosComprados != null && !productosComprados.isEmpty()) {
              for (Producto producto : productosComprados) {
            	  port.comprasUnicasProducto(producto.getNumRef());
                      
            	  System.out.println("Producto actualizado: " + producto.getNombre() + ", Compras únicas: " + port.getComprasUnicasProducto(producto.getNumRef()));
              }
          } else {
              System.out.println("No hay productos en la sesión para actualizar.");
          }

          // Continuar con el flujo de la aplicación
          chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        // Inicialización del filtro
        System.out.println("Filtro 'ProductoDestacadoFilter' inicializado.");
    }
}
