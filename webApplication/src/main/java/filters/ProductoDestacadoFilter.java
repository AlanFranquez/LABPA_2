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

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.market.svcentral.Cliente;
import com.market.svcentral.Item;
import com.market.svcentral.Producto;
import com.market.svcentral.Sistema;

@WebFilter("/PaginaExito")
public class ProductoDestacadoFilter extends HttpFilter implements Filter {

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

    
          EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
          EntityManager em = emf.createEntityManager();

          em.getTransaction().begin();

          System.out.println("Entrando al filtro de la página de éxito");

          HttpSession session = httpRequest.getSession();
          
          if (session.getAttribute("usuarioLogueado") == null) {
              httpResponse.sendRedirect("home");  
              return;
          }


          List<Producto> productosComprados = (List<Producto>) session.getAttribute("productosComprados");

          if (productosComprados != null && !productosComprados.isEmpty()) {
              for (Producto producto : productosComprados) {
                  
                  Producto productoBD = em.find(Producto.class, producto.getNumRef());
                  if (productoBD != null) {
                     
                      productoBD.setComprasUnicas();  
                      
                      em.merge(productoBD);
                      System.out.println("Producto actualizado: " + productoBD.getNombre() + ", Compras únicas: " + productoBD.getComprasUnicas());
                  }
              }
          } else {
              System.out.println("No hay productos en la sesión para actualizar.");
          }

          // Confirmar la transacción y cerrar el EntityManager
          em.getTransaction().commit();
          em.close();
          emf.close();

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
